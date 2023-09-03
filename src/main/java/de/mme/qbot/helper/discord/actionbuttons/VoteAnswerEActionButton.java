package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerEActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "E";
    public static final String BUTTON_ID = "answer-e-button-id";


    public VoteAnswerEActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
