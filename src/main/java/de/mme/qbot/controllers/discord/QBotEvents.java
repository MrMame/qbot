package de.mme.qbot.controllers.discord;

import de.mme.qbot.interaction.events.QbotChannelCreateEvent;
import de.mme.qbot.interaction.events.QbotChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QBotEvents {




    private QbotChannelCreateEvent qbotChannelCreateEvent;
    private QbotChannelDeleteEvent qbotChannelDeleteEvent;

    private List<EventListener> events = new ArrayList<>();

    public QBotEvents(QbotChannelCreateEvent qbotChannelCreateEvent, QbotChannelDeleteEvent qbotChannelDeleteEvent) {
        this.qbotChannelCreateEvent = qbotChannelCreateEvent;
        this.qbotChannelDeleteEvent = qbotChannelDeleteEvent;

        this.events.add(qbotChannelCreateEvent);
        this.events.add(qbotChannelDeleteEvent);
    }

    public List<EventListener> getAsList(){
        return this.events;
    }

    public QbotChannelCreateEvent getQbotChannelCreateEvent() {
        return qbotChannelCreateEvent;
    }

    public QbotChannelDeleteEvent getQbotChannelDeleteEvent() {
        return qbotChannelDeleteEvent;
    }

}
