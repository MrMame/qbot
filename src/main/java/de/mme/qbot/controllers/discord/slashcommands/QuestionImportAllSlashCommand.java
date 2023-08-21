package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class QuestionImportAllSlashCommand extends AbstractSlashCommand{
    public final static String COMMAND_NAME ="question-import-all";
    public final static String COMMAND_DESCRIPTION ="Imports all questions from the provided textfile.";
    public final static String COMMAND_OPTION_IMPORTFILE_NAME ="importfile";
    public final static String COMMAND_OPTION_IMPORTFILE_DESCRIPTION ="File containing questions for import.";
    public final static String COMMAND_OPTION_APPENDDATA_NAME ="append-data";
    public final static String COMMAND_OPTION_APPENDDATA_DESCRIPTION ="True=all questions will be appended.False=Old questions will be overwritten.";


    public QuestionImportAllSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {
        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.ATTACHMENT,
                COMMAND_OPTION_IMPORTFILE_NAME,
                COMMAND_OPTION_IMPORTFILE_DESCRIPTION)
                .addOption(OptionType.BOOLEAN,
                        COMMAND_OPTION_APPENDDATA_NAME,
                        COMMAND_OPTION_APPENDDATA_DESCRIPTION);

        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);
    }
}
