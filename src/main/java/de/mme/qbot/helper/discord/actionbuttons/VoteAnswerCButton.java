package de.mme.qbot.helper.discord.actionbuttons;

import java.util.function.Consumer;

public class VoteAnswerCButton extends AbstractButton{

    private static final String BUTTON_LABEL = "C";
    public static final String BUTTON_ID = "answer-c-button-id";


    public VoteAnswerCButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
