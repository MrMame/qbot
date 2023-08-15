package de.mme.qbot.logics.discordapi.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.function.Consumer;

public interface SlashCommand extends EventListener {
    CommandData getCommandData();

    Consumer<SlashCommandInteractionEvent> getCommandHandler();
}
