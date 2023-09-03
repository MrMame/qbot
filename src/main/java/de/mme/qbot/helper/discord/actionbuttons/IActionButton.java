package de.mme.qbot.helper.discord.actionbuttons;

import java.util.EventListener;
import java.util.function.Consumer;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;


public interface IActionButton extends EventListener {
    String getId();
    String getLabel();
    Button getButton();

    Consumer<ButtonInteractionEvent> getEventHandler();
    void setEventHandler(Consumer<ButtonInteractionEvent> eventHandler);

}
