package de.mme.qbot.controllers.discord;

import de.mme.qbot.controllers.discord.slashcommands.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashCommandFiredEvent extends SlashCommandInteractionEvent {

    private ISlashCommand firedSlashCommand;

    public ISlashCommand getFiredSlashCommand() {
        return firedSlashCommand;
    }



    public SlashCommandFiredEvent(ISlashCommand firedSlashCommand, SlashCommandInteractionEvent interactionEvent) {
        super(interactionEvent.getJDA(),interactionEvent.getResponseNumber(),interactionEvent.getInteraction());
        this.firedSlashCommand = firedSlashCommand;
    }

}
