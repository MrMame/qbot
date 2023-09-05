package de.mme.qbot.helper.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmbedsCreator {

    public static MessageEmbed appendUsernameToDescription(MessageEmbed oldEmbed, User user){
        EmbedBuilder neb = new EmbedBuilder();
        neb.setTitle(oldEmbed.getTitle());
        neb.appendDescription(oldEmbed.getDescription());
        neb.appendDescription("\r\n" + user.getAsMention());
        return neb.build();
    }

    public static MessageEmbed removeUsernameFromDescription(MessageEmbed oldEmbed, User user){
        EmbedBuilder neb = new EmbedBuilder();
        neb.setTitle(oldEmbed.getTitle());
        neb.setDescription(oldEmbed.getDescription().replace("\r\n" + user.getAsMention(),""));
        return neb.build();
    }


    public static List<MessageEmbed> toggleUsernameInDescription(String targetEmbedTitle,List<MessageEmbed> oldEmbeds, User user){
        List<MessageEmbed> newEmbeds = oldEmbeds.stream()
                .map(emb->{
                    if(emb.getTitle().equals(targetEmbedTitle)){
                        if(emb.getDescription().contains(user.getAsMention())){
                            return removeUsernameFromDescription(emb,user);
                        }else{
                            return appendUsernameToDescription(emb,user);
                        }
                    }else{
                        return emb;
                    }})
                .collect(Collectors.toList());
        return newEmbeds;
    }

}
