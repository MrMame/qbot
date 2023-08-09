package de.mme.qbot.discordapi.slashcommands;

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

import java.util.concurrent.TimeUnit;

public class EchoSlashCommand extends ListenerAdapter {

    private final static String COMMAND_NAME ="echo-channel";
    private final static String COMMAND_DESCRIPTION ="Echoes all input back to channel";

    private final static String COMMAND_OPTION_TEXT_NAME ="text";
    private final static String COMMAND_OPTION_TEXT_DESCRIPTION ="Text to echo back in channel";



    private CommandData commandData;

    public CommandData getCommandData() {
        return commandData;
    }

    public EchoSlashCommand() {

        CommandData theCommand = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                                    .addOption(OptionType.STRING,
                                               COMMAND_OPTION_TEXT_NAME,
                                               COMMAND_OPTION_TEXT_DESCRIPTION);

        this.commandData = theCommand;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case COMMAND_NAME:
                event.reply("Echoing: \n\n" + event.getOption(COMMAND_OPTION_TEXT_NAME,OptionMapping::getAsString)).queue();
                break;
        }
    }

}
