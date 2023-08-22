package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class QuestionExportAllSlashCommand extends AbstractSlashCommand{
    public final static String COMMAND_NAME ="question-export-all";
    public final static String COMMAND_DESCRIPTION ="Export all questions as a file for download";


    public QuestionExportAllSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {
        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION);

        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);
    }
}
