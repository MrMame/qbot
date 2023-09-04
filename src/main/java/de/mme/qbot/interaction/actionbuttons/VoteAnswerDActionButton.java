package de.mme.qbot.interaction.actionbuttons;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Component;

@Component
public class VoteAnswerDActionButton extends AbstractActionButton {

    public static final String BUTTON_ID = "answer-d-button-id";


    public VoteAnswerDActionButton() {
        super(ButtonStyle.SECONDARY,BUTTON_ID, Emoji.fromUnicode("U+1F1E9"));
    }


}
