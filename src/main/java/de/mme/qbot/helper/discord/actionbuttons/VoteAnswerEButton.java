package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerEButton extends AbstractButton {

    private static final String BUTTON_LABEL = "E";
    public static final String BUTTON_ID = "answer-e-button-id";


    public VoteAnswerEButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
