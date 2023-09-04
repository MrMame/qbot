package de.mme.qbot.interaction.actionbuttons;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Component;

@Component
public class VoteAnswerBActionButton extends AbstractActionButton {

    public static final String BUTTON_ID = "answer-b-button-id";


    public VoteAnswerBActionButton() {
        super(ButtonStyle.SECONDARY, BUTTON_ID, Emoji.fromUnicode("U+1F1E7"));
    }
}
