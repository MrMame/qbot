package de.mme.qbot.controllers.discord;

import de.mme.qbot.controllers.discord.slashcommands.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashCommandFiredEvent extends SlashCommandInteractionEvent {

    private SlashCommand firedSlashCommand;

    public SlashCommand getFiredSlashCommand() {
        return firedSlashCommand;
    }



    public SlashCommandFiredEvent(SlashCommand firedSlashCommand, SlashCommandInteractionEvent interactionEvent) {
        super(interactionEvent.getJDA(),interactionEvent.getResponseNumber(),interactionEvent.getInteraction());
        this.firedSlashCommand = firedSlashCommand;
    }

}
