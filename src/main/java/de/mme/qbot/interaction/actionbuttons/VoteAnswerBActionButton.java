package de.mme.qbot.interaction.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerBActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "B";
    public static final String BUTTON_ID = "answer-b-button-id";


    public VoteAnswerBActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
