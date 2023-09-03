package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Component
public class QuestionRemoveByIdSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="question-remove-byid";
    public final static String COMMAND_DESCRIPTION ="Removes the question with the provided id";

    public final static String COMMAND_OPTION_ID_NAME ="id";
    public final static String COMMAND_OPTION_ID_DESCRIPTION ="ID of the question to remove";



    public QuestionRemoveByIdSlashCommand() {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.INTEGER,
                        COMMAND_OPTION_ID_NAME,
                        COMMAND_OPTION_ID_DESCRIPTION,
                        true);


        this.setCommandData(commandData);
    }



}
