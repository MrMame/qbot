package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerDButton extends AbstractButton {

    private static final String BUTTON_LABEL = "D";
    public static final String BUTTON_ID = "answer-d-button-id";


    public VoteAnswerDButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
