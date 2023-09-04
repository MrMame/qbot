package de.mme.qbot.interaction.actionbuttons;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Component;

@Component
public class VoteAnswerAActionButton extends AbstractActionButton {

    public static final String BUTTON_ID = "answer-a-button-id";


    public VoteAnswerAActionButton() {
        super(ButtonStyle.SECONDARY,BUTTON_ID, Emoji.fromUnicode("U+1F1E6"));
    }


}
