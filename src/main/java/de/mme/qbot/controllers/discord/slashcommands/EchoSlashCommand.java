package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class EchoSlashCommand extends AbstractSlashCommand {

    public final static String COMMAND_NAME ="echo-channel";
    public final static String COMMAND_DESCRIPTION ="Echoes all input back to channel";

    public final static String COMMAND_OPTION_TEXT_NAME ="text";
    public final static String COMMAND_OPTION_TEXT_DESCRIPTION ="Text to echo back in channel";


    public EchoSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                            .addOption(OptionType.STRING,
                                        COMMAND_OPTION_TEXT_NAME,
                                        COMMAND_OPTION_TEXT_DESCRIPTION);


        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);


    }


}
