package de.mme.qbot.discordapi.eventlisteners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelListener implements EventListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if (genericEvent instanceof ChannelDeleteEvent){
            logger.info("FIRED ChannelDeleteEvent - Channel '"
                    + ((ChannelDeleteEvent) genericEvent).getChannel().getName()
                    + "' was deleted");
        }
        else if(genericEvent instanceof ChannelCreateEvent){
            logger.info("FIRED ChannelCreateEvent - Channel '"
                    + ((ChannelCreateEvent) genericEvent).getChannel().getName()
                    + "' was created");
        }
    }

}
