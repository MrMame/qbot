package de.mme.qbot.interaction.slashcommands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class QuestionGetUniqueRandomSlashCommand extends AbstractSlashCommand{
    public final static String COMMAND_NAME ="question-get-unique-random";
    public final static String COMMAND_DESCRIPTION ="Returns a random question. No duplicates will be returned until all questions were returned";


    public QuestionGetUniqueRandomSlashCommand() {
        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION);

        this.setCommandData(commandData);
    }
}
