package de.mme.qbot.helper.discord;

import de.mme.qbot.model.domain.Question;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionMessageFactory {


    public static final String BUTTON_ID_ANONYM = "anonym-button-id";
    public static final String BUTTON_TEXT_ANONYM = "Sags dem Bot...";
    public static final String BUTTON_ID_NEW_QUESTION = "new-question-button-id";
    public static final String BUTTON_TEXT_NEW_QUESTION = "Wahrheit!";
    public static final String BUTTON_ID_NEW_DARE = "new-dare-button-id";
    public static final String BUTTON_TEXT_NEW_DARE = "Pflicht!";

    public static final String BUTTON_ID_ANSWER_A ="answer-a-button-id";
    public static final String BUTTON_TEXT_ANSWER_A ="A";
    public static final String BUTTON_ID_ANSWER_B ="answer-b-button-id";
    public static final String BUTTON_TEXT_ANSWER_B ="B";
    public static final String BUTTON_ID_ANSWER_C ="answer-c-button-id";
    public static final String BUTTON_TEXT_ANSWER_C ="C";
    public static final String BUTTON_ID_ANSWER_D ="answer-d-button-id";
    public static final String BUTTON_TEXT_ANSWER_D ="D";
    public static final String BUTTON_ID_ANSWER_E ="answer-e-button-id";
    public static final String BUTTON_TEXT_ANSWER_E ="E";




    public static MessageCreateData createQuestionMessage(Question question){

        MessageCreateBuilder msgB = new MessageCreateBuilder();

        // ----------------- Question Embed (Question Title, Question Description)
        EmbedBuilder ebQuestion = new EmbedBuilder();
        ebQuestion.setColor(Color.BLACK)
                .setTitle(question.getQuestionText());
        MessageEmbed embQuestion = ebQuestion.build();

        msgB.addEmbeds(embQuestion);

        // ----------------- Action Row ( AnonymAnswer Button, GetNewQuestion Button, GetNewDare button)
        Button btnAnonym = Button.secondary(BUTTON_ID_ANONYM,BUTTON_TEXT_ANONYM);
        Button btnNewQuestion = Button.primary(BUTTON_ID_NEW_QUESTION,BUTTON_TEXT_NEW_QUESTION);
        Button btnNewDare = Button.primary(BUTTON_ID_NEW_DARE,BUTTON_TEXT_NEW_DARE);

        msgB.addActionRow(btnAnonym,btnNewQuestion,btnNewDare);

        // ----------------- IF Answers - Answer Embed with ActionButtons
        EmbedBuilder ebAnswers = new EmbedBuilder();
        List<Button> btnAnswers = new ArrayList<>();

        if(question.isAnswerAvailableA()){
            ebAnswers.addField("A",question.getAnswerA(),false);}
            btnAnswers.add(Button.secondary(BUTTON_ID_ANSWER_A,BUTTON_TEXT_ANSWER_A));
        if(question.isAnswerAvailableB()){
            ebAnswers.addField("B",question.getAnswerB(),false);
            btnAnswers.add(Button.secondary(BUTTON_ID_ANSWER_B,BUTTON_TEXT_ANSWER_B));}
        if(question.isAnswerAvailableC()){
            ebAnswers.addField("C",question.getAnswerC(),false);
            btnAnswers.add(Button.secondary(BUTTON_ID_ANSWER_C,BUTTON_TEXT_ANSWER_C));}
        if(question.isAnswerAvailableD()){
            ebAnswers.addField("D",question.getAnswerD(),false);
            btnAnswers.add(Button.secondary(BUTTON_ID_ANSWER_D,BUTTON_TEXT_ANSWER_D));}
        if(question.isAnswerAvailableE()){
            ebAnswers.addField("E",question.getAnswerE(),false);
            btnAnswers.add(Button.secondary(BUTTON_ID_ANSWER_E,BUTTON_TEXT_ANSWER_E));}
        if(!ebAnswers.isEmpty()){
            msgB.addEmbeds(ebAnswers.build());
            msgB.addActionRow(btnAnswers);}

        // ------------------ Create finished message
        return msgB.build();

    }

    private static boolean isAnswerExisting(Question question) {
        return question.isAnswerAvailableA()
                || question.isAnswerAvailableB()
                || question.isAnswerAvailableC()
                || question.isAnswerAvailableD()
                || question.isAnswerAvailableE();
    }

}
