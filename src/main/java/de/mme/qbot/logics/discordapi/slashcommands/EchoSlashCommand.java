package de.mme.qbot.logics.discordapi.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class EchoSlashCommand extends AbstractSlashCommand {

    private final static String COMMAND_NAME ="echo-channel";
    private final static String COMMAND_DESCRIPTION ="Echoes all input back to channel";

    private final static String COMMAND_OPTION_TEXT_NAME ="text";
    private final static String COMMAND_OPTION_TEXT_DESCRIPTION ="Text to echo back in channel";


    public EchoSlashCommand() {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                            .addOption(OptionType.STRING,
                                        COMMAND_OPTION_TEXT_NAME,
                                        COMMAND_OPTION_TEXT_DESCRIPTION);


        this.setCommandData(commandData);
        this.setCommandHandler((event)->{
            event.reply("Echoing: \n\n" + event.getOption(COMMAND_OPTION_TEXT_NAME, OptionMapping::getAsString)).queue();
        });

    }


}
