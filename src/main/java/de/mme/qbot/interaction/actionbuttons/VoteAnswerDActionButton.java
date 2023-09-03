package de.mme.qbot.interaction.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerDActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "D";
    public static final String BUTTON_ID = "answer-d-button-id";


    public VoteAnswerDActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
