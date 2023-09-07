package de.mme.qbot.interaction.events;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;


import java.util.function.Consumer;


@Component
public class QbotChannelCreateEvent extends ListenerAdapter{ //implements EventListener {


    Consumer<ChannelCreateEvent> eventHandler;

    public Consumer<ChannelCreateEvent> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(Consumer<ChannelCreateEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onChannelCreate(ChannelCreateEvent event) {
        super.onChannelCreate(event);
        this.eventHandler.accept(event);
    }

}
