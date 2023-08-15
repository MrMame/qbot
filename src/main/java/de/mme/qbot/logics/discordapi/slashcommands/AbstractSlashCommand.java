package de.mme.qbot.logics.discordapi.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public abstract class AbstractSlashCommand extends ListenerAdapter implements SlashCommand {

    private CommandData commandData;
    private Consumer<SlashCommandInteractionEvent> commandHandler;

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
    public Consumer<SlashCommandInteractionEvent> getCommandHandler() {
        return commandHandler;
    }

    public void setCommandHandler(Consumer<SlashCommandInteractionEvent> commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        if(event.getName().equals(this.commandData.getName())){
            logger.info("...Start handling slash command " + event.getName());
            this.commandHandler.accept(event);
        }
    }




}
