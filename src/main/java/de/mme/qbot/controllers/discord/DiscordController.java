package de.mme.qbot.controllers.discord;


import de.mme.qbot.controllers.discord.slashcommands.SlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@PropertySource("classpath:discord.properties")
public class DiscordController {

    JDA jda;
    static Logger logger = LoggerFactory.getLogger(DiscordController.class);

    @Autowired
    Environment env;

    @Autowired
    public DiscordController(Environment env,
                             List<SlashCommand> slashCommandsList,
                             List<EventListener> eventListenerList) {
        this.env = env;
        this.jda = CreateJda(slashCommandsList, eventListenerList);
    }




    private JDA CreateJda(List<SlashCommand> slashCommandsList,
                          List<EventListener> eventListenerList)  {

        String discordToken = this.env.getProperty("settings.discord.token");

        this.jda = null;

        JDABuilder builder = JDABuilder.createDefault(discordToken);


        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("ExampleJDA running"));

        // Add Event Listeners ==============================================
        eventListenerList.forEach(builder::addEventListeners);
        // ==================================================================

        // Build the JDA Object
        this.jda =  builder.build();
        // Wait for the JDA Object to be ready for use
        try {
            this.jda.awaitReady();
            logger.info("...JDA Object was created and is ready to use.");
        } catch (InterruptedException e) {
            logger.warn("Abort waiting for JDA Object to get ready. There is no JDA Object available.");
            throw new RuntimeException(e);
        }

        // Now Add slash commands ===========================================
        ArrayList<CommandData> commandDatas = new ArrayList();
        slashCommandsList.forEach((slashCommand)->{
            commandDatas.add(slashCommand.getCommandData());
        });
        jda.updateCommands().addCommands(commandDatas).queue();


        // ==================================================================

        return this.jda;
    }





}
