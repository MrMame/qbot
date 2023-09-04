package de.mme.qbot.interaction.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public abstract class AbstractSlashCommand extends ListenerAdapter implements ISlashCommand {

    private CommandData commandData;
    private Consumer<SlashCommandFiredEvent> commandHandler;

    static Logger logger = LoggerFactory.getLogger(AbstractSlashCommand.class);


    public AbstractSlashCommand() {
    }

    @Override
    public CommandData getCommandData() {
        return commandData;
    }

    public void setCommandData(CommandData commandData) {
        this.commandData = commandData;
    }

    @Override
    public Consumer<SlashCommandFiredEvent> getCommandHandler() {
        return commandHandler;
    }

    public void setCommandHandler(Consumer<SlashCommandFiredEvent> commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent)
    {
        if(slashCommandInteractionEvent.getName().equals(this.commandData.getName())){
            SlashCommandFiredEvent slashEvent = new SlashCommandFiredEvent(this,slashCommandInteractionEvent);
            logger.info("...Start handling slash command " + this.commandData.getName());
            this.commandHandler.accept(slashEvent);
        }
    }




}
