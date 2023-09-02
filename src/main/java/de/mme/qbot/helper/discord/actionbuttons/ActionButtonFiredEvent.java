package de.mme.qbot.helper.discord.actionbuttons;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ActionButtonFiredEvent extends ButtonInteractionEvent {

    private String firedActionButtonId;

    public String getFiredActionButtonId() {
        return firedActionButtonId;
    }

    public ActionButtonFiredEvent(String firedActionButtonId,ButtonInteractionEvent interactionEvent){
//            JDA api, long responseNumber, ButtonInteraction interaction) {
        super(interactionEvent.getJDA(), interactionEvent.getResponseNumber(), interactionEvent.getInteraction());
        this.firedActionButtonId = firedActionButtonId;
    }
}
