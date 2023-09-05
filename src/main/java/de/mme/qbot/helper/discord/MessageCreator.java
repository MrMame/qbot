package de.mme.qbot.helper.discord;

import de.mme.qbot.interaction.actionbuttons.*;
import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.IEntity;
import de.mme.qbot.model.domain.Question;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessageCreator {

    public static final String EMBED_ANSWER_A_TITLE = "A";
    public static final String EMBED_ANSWER_B_TITLE = "B";
    public static final String EMBED_ANSWER_C_TITLE = "C";
    public static final String EMBED_ANSWER_D_TITLE = "D";
    public static final String EMBED_ANSWER_E_TITLE = "E";
    private AnonymAnswerActionButton anonymAnswerButton;
    private GetQuestionActionButton getQuestionButton;
    private GetDareActionButton getDareButton;
    private VoteAnswerAActionButton voteAnswerAButton;
    private VoteAnswerBActionButton voteAnswerBButton;
    private VoteAnswerCActionButton voteAnswerCButton;
    private VoteAnswerDActionButton voteAnswerDButton;
    private VoteAnswerEActionButton voteAnswerEButton;


    public enum SystemMessageTypes{
        Info,Error
    }


    @Autowired
    public MessageCreator(AnonymAnswerActionButton anonymAnswerButton, GetQuestionActionButton getQuestionButton, GetDareActionButton getDareButton, VoteAnswerAActionButton voteAnswerAButton, VoteAnswerBActionButton voteAnswerBButton, VoteAnswerCActionButton voteAnswerCButton, VoteAnswerDActionButton voteAnswerDButton, VoteAnswerEActionButton voteAnswerEButton) {
        this.anonymAnswerButton = anonymAnswerButton;
        this.getQuestionButton = getQuestionButton;
        this.getDareButton = getDareButton;
        this.voteAnswerAButton = voteAnswerAButton;
        this.voteAnswerBButton = voteAnswerBButton;
        this.voteAnswerCButton = voteAnswerCButton;
        this.voteAnswerDButton = voteAnswerDButton;
        this.voteAnswerEButton = voteAnswerEButton;
    }




    public MessageCreateData createSystemMessage(SystemMessageTypes messageType, String messageText,@NotNull List<IEntity> entities){
        MessageCreateBuilder msgB = new MessageCreateBuilder();
        MessageEmbed ebInfoText = getSystemMessageInfotextEmbed(messageType,messageText);
        msgB.addEmbeds(ebInfoText);
        MessageEmbed ebListing =  getIDListEmbed(entities);
        msgB.addEmbeds(ebListing);
        return msgB.build();
    }
    public MessageCreateData createSystemMessage(SystemMessageTypes messageType, String messageText,@Nonnull FileUpload... files){
        MessageCreateBuilder msgB = new MessageCreateBuilder();
        MessageEmbed ebInfoText = getSystemMessageInfotextEmbed(messageType,messageText);
        msgB.addEmbeds(ebInfoText);
        msgB.addFiles(files);
        return msgB.build();
    }
    public MessageCreateData createSystemMessage(SystemMessageTypes messageType, String messageText){
        MessageCreateBuilder msgB = new MessageCreateBuilder();
        MessageEmbed ebInfoText = getSystemMessageInfotextEmbed(messageType,messageText);
        msgB.addEmbeds(ebInfoText);
        return msgB.build();
    }



    public MessageCreateData createQuestionMessage(Optional<Question> optQuestion){
        // If There is no Question to display, we return an error Message
        if(optQuestion.isEmpty()){
            return createMessage("No Question available. Please add at least one question to the bot.");
        }else {

            Question question = optQuestion.get();

            MessageCreateBuilder msgB = new MessageCreateBuilder();

            // ----------------- Question Embed (Question Title, Question Description)
            addMessagesHeader(msgB, question.getQuestionText());


            // ----------------- IF Answers - Answer Embed with ActionButtons
            List<Button> btnAnswers = new ArrayList<>();
            List<MessageEmbed> embeds = new ArrayList<>();

            if (question.isAnswerAvailableA()) {
                embeds.add(CreateAnswerEmbed(EMBED_ANSWER_A_TITLE,question));
                btnAnswers.add(voteAnswerAButton.getButton());
            }

            if (question.isAnswerAvailableB()) {
                embeds.add(CreateAnswerEmbed(EMBED_ANSWER_B_TITLE,question));
                btnAnswers.add(voteAnswerBButton.getButton());
            }
            if (question.isAnswerAvailableC()) {
                embeds.add(CreateAnswerEmbed(EMBED_ANSWER_C_TITLE,question));
                btnAnswers.add(voteAnswerCButton.getButton());
            }
            if (question.isAnswerAvailableD()) {
                embeds.add(CreateAnswerEmbed(EMBED_ANSWER_D_TITLE,question));
                btnAnswers.add(voteAnswerDButton.getButton());
            }
            if (question.isAnswerAvailableE()) {
                embeds.add(CreateAnswerEmbed(EMBED_ANSWER_E_TITLE,question));
                btnAnswers.add(voteAnswerEButton.getButton());
            }
            if (!embeds.isEmpty()) {
                msgB.addEmbeds(embeds);
                msgB.addActionRow(btnAnswers);
            }

            // ------------------ Create finished message
            return msgB.build();
        }   // if(optQuestion.isEmpty())
    }

    private static MessageEmbed CreateAnswerEmbed(String title ,Question question) {
        EmbedBuilder emb = new EmbedBuilder();
        emb.setTitle(title);
        emb.setDescription(question.getAnswerA());
        return emb.build();
    }

    public MessageCreateData createDareMessage(Optional<Dare> optDare){
        // If There is no Question to display, we return an error Message
        if(optDare.isEmpty()){
            return createMessage("No Dare available. Please add at least one dare to the bot.");
        }else {
            Dare dare = optDare.get();
            MessageCreateBuilder msgB = new MessageCreateBuilder();
            addMessagesHeader(msgB, dare.getText());
            return msgB.build();
        }
    }

    private MessageEmbed getIDListEmbed(List<IEntity> entities){
        EmbedBuilder ebQuestions = new EmbedBuilder();
        ebQuestions.setTitle("Question IDs");
        StringBuilder questionIds = new StringBuilder();
        for(IEntity e:entities){
            questionIds.append(e.getId() + " ");
        }
        ebQuestions.setDescription(questionIds.toString());
        return ebQuestions.build();
    }
    private MessageEmbed getSystemMessageInfotextEmbed(SystemMessageTypes messageType, String messageText){
        EmbedBuilder ebInfotext = new EmbedBuilder();
        switch(messageType){
            case Info -> ebInfotext.setColor(Color.YELLOW);
            case Error -> ebInfotext.setColor(Color.RED);
        }
        ebInfotext
                .setTitle(messageType.name())
                .setDescription(messageText);
        return ebInfotext.build();
    }


    @NotNull
    private void addMessagesHeader(MessageCreateBuilder msgB, String embedTitle) {
        EmbedBuilder ebQuestion = new EmbedBuilder();
        ebQuestion.setColor(Color.BLACK)
                .setTitle(embedTitle);
        MessageEmbed embQuestion = ebQuestion.build();

        msgB.addEmbeds(embQuestion);
        msgB.addActionRow(anonymAnswerButton.getButton(),getQuestionButton.getButton(),getDareButton.getButton());
    }

    private MessageCreateData createMessage(String title){
        MessageCreateBuilder msgB = new MessageCreateBuilder();

        // ----------------- Question Embed (Question Title, Question Description)
        addMessagesHeader(msgB,title);

        // ------------------ Create finished message
        return msgB.build();
    }

    private boolean isAnswerExisting(Question question) {
        return question.isAnswerAvailableA()
                || question.isAnswerAvailableB()
                || question.isAnswerAvailableC()
                || question.isAnswerAvailableD()
                || question.isAnswerAvailableE();
    }


}
