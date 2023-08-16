package de.mme.qbot.controllers.discord.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class TripleEchoSlashCommand extends AbstractSlashCommand{


    private final static String COMMAND_NAME ="echo-channel";
    private final static String COMMAND_DESCRIPTION ="Echoes all input back to channel";

    private final static String COMMAND_OPTION_TEXT_A_NAME ="text";
    private final static String COMMAND_OPTION_TEXT_A_DESCRIPTION ="First text to echo back into channel";
    private final static String COMMAND_OPTION_TEXT_B_NAME ="textb";
    private final static String COMMAND_OPTION_TEXT_B_DESCRIPTION ="Second text to echo back into channel";
    private final static String COMMAND_OPTION_TEXT_C_NAME ="textc";
    private final static String COMMAND_OPTION_TEXT_C_DESCRIPTION ="third text to echo back into channel";



    public TripleEchoSlashCommand() {
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
        this.setCommandHandler((event)->{
            event.getChannel().sendMessage("First Echoing: \n\n" + event.getOption(COMMAND_OPTION_TEXT_A_NAME, OptionMapping::getAsString)).queue();
            event.getChannel().sendMessage("Second Echoing: \n\n" + event.getOption(COMMAND_OPTION_TEXT_B_NAME, OptionMapping::getAsString)).queue();
            event.reply("Third Echoing: \n\n" + event.getOption(COMMAND_OPTION_TEXT_C_NAME, OptionMapping::getAsString)).queue();
        });
    }
}
