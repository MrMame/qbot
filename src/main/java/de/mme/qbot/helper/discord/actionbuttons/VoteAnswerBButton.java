package de.mme.qbot.helper.discord.actionbuttons;

import java.util.function.Consumer;

public class VoteAnswerBButton extends AbstractButton{

    private static final String BUTTON_LABEL = "B";
    public static final String BUTTON_ID = "answer-b-button-id";


    public VoteAnswerBButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
