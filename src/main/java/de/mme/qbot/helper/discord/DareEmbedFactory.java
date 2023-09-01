package de.mme.qbot.helper.discord;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;


public class DareEmbedFactory {




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


    public static MessageEmbed createNormalEmbed(Dare dare) {
       // Build the embed
        EmbedBuilder eb = new EmbedBuilder();

        eb
                .setTitle(dare.getText())
                .setColor(Color.GREEN)
                .setFooter("id:" + dare.getId())
                .setThumbnail("https://img.freepik.com/vektoren-kostenlos/nette-pizza-cartoon-vektor-icon-illustration-fast-food-symbol-konzept-flacher-cartoon-stil_138676-2588.jpg");
                ;

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

    public static MessageEmbed createErrorEmbed(String s, List<Dare> errD ){

        // Get all question ids which cause trouble
        StringBuilder idText = new StringBuilder();
        for(Dare q:errD){
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
                .addField("Number of Questions with error",String.valueOf(errD.size()),false)
                .addField("Question IDs which cause trouble",idText.toString(),false);

        // send it into the channel
        return eb.build();


    }

}
