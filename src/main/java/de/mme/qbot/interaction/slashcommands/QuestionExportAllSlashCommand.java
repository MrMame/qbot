package de.mme.qbot.interaction.slashcommands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class QuestionExportAllSlashCommand extends AbstractSlashCommand{
    public final static String COMMAND_NAME ="question-export-all";
    public final static String COMMAND_DESCRIPTION ="Export all questions as a file for download";


    public QuestionExportAllSlashCommand() {
        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION);

        this.setCommandData(commandData);
    }
}
