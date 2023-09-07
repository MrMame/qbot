package de.mme.qbot.interaction.events;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class QbotChannelDeleteEvent extends ListenerAdapter{ //implements EventListener {


    Consumer<ChannelDeleteEvent> eventHandler;

    public Consumer<ChannelDeleteEvent> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(Consumer<ChannelDeleteEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {
        super.onChannelDelete(event);
        this.eventHandler.accept(event);
    }

}
