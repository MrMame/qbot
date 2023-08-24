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
    public final static String COMMAND_OPTION_QUESTION_DESCRIPTION ="Question Text. Max 250 Characters";
    public final static String COMMAND_OPTION_ANSWER_A_NAME ="answer-a";
    public final static String COMMAND_OPTION_ANSWER_A_DESCRIPTION ="Text of answer A.Max 600 Characters";
    public final static String COMMAND_OPTION_ANSWER_B_NAME ="answer-b";
    public final static String COMMAND_OPTION_ANSWER_B_DESCRIPTION ="Text of answer B.Max 600 Characters";
    public final static String COMMAND_OPTION_ANSWER_C_NAME ="answer-c";
    public final static String COMMAND_OPTION_ANSWER_C_DESCRIPTION ="Text of answer C.Max 600 Characters";
    public final static String COMMAND_OPTION_ANSWER_D_NAME ="answer-d";
    public final static String COMMAND_OPTION_ANSWER_D_DESCRIPTION ="Text of answer D.Max 600 Characters";
    public final static String COMMAND_OPTION_ANSWER_E_NAME ="answer-e";
    public final static String COMMAND_OPTION_ANSWER_E_DESCRIPTION ="Text of answer E.Max 600 Characters";

    public QuestionAddSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_QUESTION_NAME,
                        COMMAND_OPTION_QUESTION_DESCRIPTION)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_ANSWER_A_NAME,
                        COMMAND_OPTION_ANSWER_A_DESCRIPTION,
                        false)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_ANSWER_B_NAME,
                        COMMAND_OPTION_ANSWER_B_DESCRIPTION,
                        false)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_ANSWER_C_NAME,
                        COMMAND_OPTION_ANSWER_C_DESCRIPTION,
                        false)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_ANSWER_D_NAME,
                        COMMAND_OPTION_ANSWER_D_DESCRIPTION,
                        false)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_ANSWER_E_NAME,
                        COMMAND_OPTION_ANSWER_E_DESCRIPTION,
                        false)
                ;


        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);

    }



}
