package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class TripleEchoSlashCommand extends AbstractSlashCommand{


    public final static String COMMAND_NAME ="echo-channel";
    public final static String COMMAND_DESCRIPTION ="Echoes all input back to channel";

    public final static String COMMAND_OPTION_TEXT_A_NAME ="text";
    public final static String COMMAND_OPTION_TEXT_A_DESCRIPTION ="First text to echo back into channel";
    public final static String COMMAND_OPTION_TEXT_B_NAME ="textb";
    public final static String COMMAND_OPTION_TEXT_B_DESCRIPTION ="Second text to echo back into channel";
    public final static String COMMAND_OPTION_TEXT_C_NAME ="textc";
    public final static String COMMAND_OPTION_TEXT_C_DESCRIPTION ="third text to echo back into channel";



    public TripleEchoSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {
        CommandData commandData = Commands.slash("triple-echo-channel","Echos three textblocks")
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_TEXT_A_NAME,
                        COMMAND_OPTION_TEXT_A_DESCRIPTION).addOption(OptionType.STRING,
                        COMMAND_OPTION_TEXT_B_NAME,
                        COMMAND_OPTION_TEXT_B_DESCRIPTION).addOption(OptionType.STRING,
                        COMMAND_OPTION_TEXT_C_NAME,
                        COMMAND_OPTION_TEXT_C_DESCRIPTION)

                ;

        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);

    }
}
