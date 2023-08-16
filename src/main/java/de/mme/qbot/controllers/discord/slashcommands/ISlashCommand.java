package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.function.Consumer;

public interface ISlashCommand extends EventListener {
    CommandData getCommandData();

    Consumer<SlashCommandFiredEvent> getCommandHandler();
    void setCommandHandler(Consumer<SlashCommandFiredEvent> commandHandler);


}
