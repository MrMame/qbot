package de.mme.qbot.logics.discordapi.eventlisteners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadyListener implements EventListener
{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if (genericEvent instanceof ReadyEvent)
            this.logger.info("FIRED ReadyEvent - API is ready");
    }
}
