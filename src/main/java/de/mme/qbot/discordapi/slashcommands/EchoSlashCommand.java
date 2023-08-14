package de.mme.qbot.discordapi.slashcommands;

import de.mme.qbot.QbotApplication;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EchoSlashCommand extends ListenerAdapter {

    static Logger logger = LoggerFactory.getLogger(QbotApplication.class);

    private final static String COMMAND_NAME ="echo-channel";
    private final static String COMMAND_DESCRIPTION ="Echoes all input back to channel";

    private final static String COMMAND_OPTION_TEXT_NAME ="text";
    private final static String COMMAND_OPTION_TEXT_DESCRIPTION ="Text to echo back in channel";

    // Singleton
    private static EchoSlashCommand instance;


    private final CommandData commandData;
    public CommandData getCommandData() {
        return commandData;
    }



    // Singleton
    public static EchoSlashCommand getInstance(){
        if(EchoSlashCommand.instance == null){
            EchoSlashCommand.instance = new EchoSlashCommand();
        }
        return EchoSlashCommand.instance;
    }


    // Singleton
    private EchoSlashCommand() {

        CommandData theCommand = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_TEXT_NAME,
                        COMMAND_OPTION_TEXT_DESCRIPTION);

        this.commandData = theCommand;

        this.instance = this;

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {

        logger.info("Getting Slash command " + event.getName());

        //System.out.println("Getting Slash command " + event.getName());

        // make sure we handle the right command
        switch (event.getName()) {
            case COMMAND_NAME:
                event.reply("Echoing: \n\n" + event.getOption(COMMAND_OPTION_TEXT_NAME,OptionMapping::getAsString)).queue();
                break;
        }
    }

}
