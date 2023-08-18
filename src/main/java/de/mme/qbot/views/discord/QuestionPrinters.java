package de.mme.qbot.views.discord;

import de.mme.qbot.model.domain.Question;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;


public class QuestionPrinters {


    public static MessageEmbed createNormalEmbed(Question question) {
       // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb
                .setColor(Color.ORANGE)
                .setDescription(question.getQuestionText())
                .setFooter("QuestionId:" + question.getId())
                .setThumbnail("https://img.freepik.com/vektoren-kostenlos/nette-pizza-cartoon-vektor-icon-illustration-fast-food-symbol-konzept-flacher-cartoon-stil_138676-2588.jpg");
                ;
        // send it into the channel
        return eb.build();
    }

    public static MessageEmbed createErrorEmbed(String s) {
        // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor("qBot")
                .setColor(Color.ORANGE)
                .setTitle("ERROR - Something went wrong")
                .setDescription(s);

        // send it into the channel
        return eb.build();

    }

    public static MessageEmbed createNormalEmbed(Iterable<Question> allQuestions) {

        StringBuilder returnText = new StringBuilder();

        for(Question q : allQuestions){
            returnText.append(q.toString() + "\n");
        }

        if(returnText.isEmpty())returnText.append("No questions to show.");

        // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor("qBot")
                .setColor(Color.ORANGE)
                .setTitle("Returning all messages")
                .setDescription(returnText);

        return eb.build();

    }
}
