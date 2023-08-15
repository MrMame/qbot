package de.mme.qbot.configs.discordapi;

import de.mme.qbot.logics.discordapi.eventlisteners.ChannelListener;
import de.mme.qbot.logics.discordapi.eventlisteners.ReadyListener;
import de.mme.qbot.logics.discordapi.eventlisteners.SlashCommandListener;
import de.mme.qbot.logics.discordapi.slashcommands.SlashCommand;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EventListenersConfig {

    @Bean
    @Autowired
    public List<EventListener> createEventListenersList(List<SlashCommand> slashCommandList){
        List<EventListener> retList = new ArrayList<>();
        retList.add(createSlashCommandListener(slashCommandList));
        retList.add(createChannelListener());
        retList.add(createReadyListener());
        return retList;
    }



    private SlashCommandListener createSlashCommandListener(List<SlashCommand> slashCommandList){
        SlashCommandListener scl = new SlashCommandListener();
        slashCommandList.forEach((slashCommand)->{scl.addSlashCommand(slashCommand);});
        return scl;
    }


    public ChannelListener createChannelListener(){
        return new ChannelListener();
    }


    public ReadyListener createReadyListener(){
        return new ReadyListener();
    }

}
