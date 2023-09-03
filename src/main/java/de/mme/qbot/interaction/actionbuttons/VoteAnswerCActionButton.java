package de.mme.qbot.interaction.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerCActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "C";
    public static final String BUTTON_ID = "answer-c-button-id";


    public VoteAnswerCActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
