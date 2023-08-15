package de.mme.qbot.logics.discordapi.eventlisteners;

import de.mme.qbot.logics.discordapi.slashcommands.SlashCommand;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 *  The SlashCommandListener is listening to the SlashCommandinteractionEvent.
 *  This Class is necessary to react on slashCommands at all. Without the app will not recognize that the
 *  Discord Bot receives a slash command.
 *  If this event gets received, all SlashCommand Objects EventHandlers, registered by the addSlashCommand() Method,
 *  will be invoked.
 *  If you want to add a new SlashCommand, you only have to create a new SlashCommand Object,
 *  derived from AbstractSlashCommand. Creating those SlashCommand Objects is all done inside the
 *  @Configuration SlashCommandConfig Object via Springs IOC-Container. There is the only place wehere you
 *  have to add the new SlashCommand.
 * */
public class SlashCommandListener implements EventListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SlashCommand> slashCommands = new ArrayList<>();


    public void addSlashCommand(SlashCommand slashCommand){
        slashCommands.add(slashCommand);
    }


    @Override
    public void onEvent(GenericEvent event) {

        // If we have a SlashCommand Event, we search for the registered slashCommand in
        // slashCommands List and run its EventHandler
        if (event instanceof SlashCommandInteractionEvent){
            SlashCommandInteractionEvent scEvent = (SlashCommandInteractionEvent)event;
            this.logger.info("FIRED SlashCommandInteractionEvent - Name=" + scEvent.getName());
            // Search for the Slashcommands registered event handler
            slashCommands.forEach((slashCommand)->{
                if(slashCommand.getCommandData().getName().equals(scEvent.getName())){
                    slashCommand.getCommandHandler().accept(scEvent);   // Start the Event handler for the command
                }
            });
        }
    }

}

