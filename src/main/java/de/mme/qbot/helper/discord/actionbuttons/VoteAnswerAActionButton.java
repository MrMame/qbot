package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class VoteAnswerAActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "A";
    public static final String BUTTON_ID = "answer-a-button-id";


    public VoteAnswerAActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
