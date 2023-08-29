package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class DareRemoveByIdSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="dare-remove-byid";
    public final static String COMMAND_DESCRIPTION ="Removes the dare with the provided id";

    public final static String COMMAND_OPTION_ID_NAME ="id";
    public final static String COMMAND_OPTION_ID_DESCRIPTION ="ID of the dare to remove";



    public DareRemoveByIdSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.INTEGER,
                        COMMAND_OPTION_ID_NAME,
                        COMMAND_OPTION_ID_DESCRIPTION);

        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);

    }



}
