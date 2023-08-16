package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class QuestionAddSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="question-add";
    public final static String COMMAND_DESCRIPTION ="Add a question to the Bot";

    public final static String COMMAND_OPTION_QUESTION_NAME ="question";
    public final static String COMMAND_OPTION_QUESTION_DESCRIPTION ="Question Text";


    public QuestionAddSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_QUESTION_NAME,
                        COMMAND_OPTION_QUESTION_DESCRIPTION);


        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);

    }



}
