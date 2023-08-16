package de.mme.qbot.controllers.discord;


import de.mme.qbot.controllers.discord.slashcommands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Controller
@PropertySource("classpath:discord.properties")
public class DiscordController implements EventListener{

    JDA jda;
    List<SlashCommand> slashCommandsList;

    Map<Object, Consumer<GenericEvent>> listenerHandlersMap = new HashMap<>();

    static Logger logger = LoggerFactory.getLogger(DiscordController.class);

    @Autowired
    public DiscordController(Environment env) {
        // First create the Discord API Object
        this.jda = createDiscordApiObject(env);

        // Register all used SlashCommands and its EventHandlers, used by the DiscordController
        this.slashCommandsList = new ArrayList<>();
        this.slashCommandsList.add(new EchoSlashCommand(this::onEchoSlashCommand));
        this.slashCommandsList.add(new TripleEchoSlashCommand(this::onTripleEchoSlashCommand));
        this.slashCommandsList.add(new QuestionAddSlashCommand(this::onQuestionAddSlashCommand));

        // Register all JDA Events and its EventHandlers, used by the DiscordController
        this.listenerHandlersMap.put(ChannelDeleteEvent.class,this::onChannelDeleteEvent);
        this.listenerHandlersMap.put(ChannelCreateEvent.class,this::onChannelCreateEvent);
        this.listenerHandlersMap.put(ReadyEvent.class,this::onReadyEvent);
        this.listenerHandlersMap.put(SlashCommandInteractionEvent.class,this::onSlashCommandReceivedEvent);

        // Send all finished SlashCommands to Discord, so they will be showing up the users
        sendSlashCommandsToDiscord(this.jda ,this.slashCommandsList);
    }



    // --------------------------- Normal Events (Add if necessary) --------------------------------------------------
    private void onChannelDeleteEvent(GenericEvent genericEvent){
        ChannelDeleteEvent event = (ChannelDeleteEvent) genericEvent;
        logger.info("FIRED ChannelDeleteEvent - Channel '"
                + event.getChannel().getName()
                + "' was deleted");
    }
    private void onChannelCreateEvent(GenericEvent genericEvent){
        ChannelCreateEvent event = (ChannelCreateEvent) genericEvent;
        logger.info("FIRED ChannelCreateEvent - Channel '"
                + event.getChannel().getName()
                + "' was created");
    }
    private void onReadyEvent(GenericEvent genericEvent) {
        ReadyEvent event = (ReadyEvent) genericEvent;
        this.logger.info("FIRED ReadyEvent - API is ready");
    }

    // --------------------------- SlashCommands Events (Add if necessary) ------------------------------------------
    private void onEchoSlashCommand(SlashCommandFiredEvent event){

        EchoSlashCommand echoSlashCommand = ((EchoSlashCommand)event.getFiredSlashCommand());

        event.reply("onEchoSlashCommand: \n\n"
                + event.getOption(echoSlashCommand.COMMAND_OPTION_TEXT_NAME, OptionMapping::getAsString))
                .queue();
    }
    private void onQuestionAddSlashCommand(SlashCommandFiredEvent event){
        QuestionAddSlashCommand questionAddSlashCommand =  ((QuestionAddSlashCommand) (event.getFiredSlashCommand()));

        event.reply("onQuestionAddSlashCommand: \n\n"
                + event.getOption(questionAddSlashCommand.COMMAND_OPTION_QUESTION_NAME, OptionMapping::getAsString))
                .queue();
    }
    private void onTripleEchoSlashCommand(SlashCommandFiredEvent event){

            TripleEchoSlashCommand tripleEchoSlashCommand = ((TripleEchoSlashCommand) (event.getFiredSlashCommand()));

            event.getChannel().sendMessage("First Echoing: \n\n"
                    + event.getOption(TripleEchoSlashCommand.COMMAND_OPTION_TEXT_A_NAME, OptionMapping::getAsString))
                    .queue();
            event.getChannel().sendMessage("Second Echoing: \n\n"
                    + event.getOption(TripleEchoSlashCommand.COMMAND_OPTION_TEXT_B_NAME, OptionMapping::getAsString))
                    .queue();
            event.reply("Third Echoing: \n\n"
                    + event.getOption(TripleEchoSlashCommand.COMMAND_OPTION_TEXT_C_NAME, OptionMapping::getAsString))
                    .queue();

        }

    // =========================== Internal Privates ===============================================================
    private void sendSlashCommandsToDiscord(JDA jda,List<SlashCommand> slashCommandsList){
        // Now Add slash commands ===========================================
        ArrayList<CommandData> commandDatas = new ArrayList();
        slashCommandsList.forEach((slashCommand)->{
            commandDatas.add(slashCommand.getCommandData());
        });
        jda.updateCommands().addCommands(commandDatas).queue();
    }
    private void onSlashCommandReceivedEvent(GenericEvent genericEvent){
        SlashCommandInteractionEvent event = (SlashCommandInteractionEvent) genericEvent;
        slashCommandsList.stream()
                .filter(slashCommand -> {return slashCommand.getCommandData().getName().equals(event.getName());})
                .forEach(slashCommand -> {slashCommand.getCommandHandler().accept(new SlashCommandFiredEvent(slashCommand,event));});
    }
    @Override
    public void onEvent(GenericEvent genericEvent) {
        Consumer<GenericEvent> registeredEventHandler =this.listenerHandlersMap.get(genericEvent.getClass());
        if(registeredEventHandler!=null)registeredEventHandler.accept(genericEvent);
    }
    private JDA createDiscordApiObject(Environment env){


        String discordToken = env.getProperty("settings.discord.token");

        JDA retJda = null;

        JDABuilder builder = JDABuilder.createDefault(discordToken);


        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("ExampleJDA running"));

        // Add Event Listeners ==============================================
        builder.addEventListeners(this);
        // ==================================================================

        // Build the JDA Object
        retJda =  builder.build();
        // Wait for the JDA Object to be ready for use
        try {
            retJda.awaitReady();
            logger.info("...JDA Object was created and is ready to use.");
        } catch (InterruptedException e) {
            logger.warn("Abort waiting for JDA Object to get ready. There is no JDA Object available.");
            throw new RuntimeException(e);
        }


        return retJda;
    }


}
