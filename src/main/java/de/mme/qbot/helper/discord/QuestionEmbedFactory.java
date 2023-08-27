package de.mme.qbot.helper.discord;

import de.mme.qbot.model.domain.Question;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;


public class QuestionEmbedFactory {

    public static final String FIELDNAME_EMBED_ANSWER_A = "A";
    public static final String FIELDNAME_EMBED_ANSWER_B = "B";
    public static final String FIELDNAME_EMBED_ANSWER_C = "C";
    public static final String FIELDNAME_EMBED_ANSWER_D = "D";
    public static final String FIELDNAME_EMBED_ANSWER_E = "E";


    public static MessageEmbed createSystemEmbed(String s){
        // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb
                .setColor(Color.YELLOW)
                .setTitle("qBot INFO")
                .setDescription(s);

        // send it into the channel
        return eb.build();

    }


    public static MessageEmbed createNormalEmbed(Question question) {
       // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb
                .setTitle(question.getQuestionText())
                .setColor(Color.GREEN)
                .setFooter("id:" + question.getId())
                .setThumbnail("https://img.freepik.com/vektoren-kostenlos/nette-pizza-cartoon-vektor-icon-illustration-fast-food-symbol-konzept-flacher-cartoon-stil_138676-2588.jpg");
                ;
                // If Answer is exsiting, add an additional Field for it
                if(question.isAnswerAvailableA())eb.addField(FIELDNAME_EMBED_ANSWER_A, question.getAnswerA(), false);
                if(question.isAnswerAvailableB())eb.addField(FIELDNAME_EMBED_ANSWER_B, question.getAnswerB(), false);
                if(question.isAnswerAvailableC())eb.addField(FIELDNAME_EMBED_ANSWER_C, question.getAnswerC(), false);
                if(question.isAnswerAvailableD())eb.addField(FIELDNAME_EMBED_ANSWER_D, question.getAnswerD(), false);
                if(question.isAnswerAvailableE())eb.addField(FIELDNAME_EMBED_ANSWER_E, question.getAnswerE(), false);

        // send it into the channel
        return eb.build();
    }

    public static MessageEmbed createErrorEmbed(String s) {
        // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb
                .setColor(Color.RED)
                .setTitle("qBot ERROR")
                .setDescription(s);

        // send it into the channel
        return eb.build();

    }

    public static MessageEmbed createErrorEmbed(String s, List<Question> errQ ){

        // Get all question ids which cause trouble
        StringBuilder idText = new StringBuilder();
        for(Question q:errQ){
            idText.append(q.getId() + " ");
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb
                .setColor(Color.RED)
                .setTitle("qBot ERROR")
                .setDescription(s)
                .appendDescription("\r\n\r\nIt seems also that some Questions could not be imported because they have some "
                                    + "issues like too many characters for their question/answer text. Please "
                                    + "doublecheck the following questions with their corresponding ids inside your "
                                    + "importfile.")
                .addField("Number of Questions with error",String.valueOf(errQ.size()),false)
                .addField("Question IDs which cause trouble",idText.toString(),false);

        // send it into the channel
        return eb.build();


    }

}
