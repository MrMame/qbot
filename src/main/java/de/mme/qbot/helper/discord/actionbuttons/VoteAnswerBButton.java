package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Component
public class VoteAnswerBButton extends AbstractButton{

    private static final String BUTTON_LABEL = "B";
    public static final String BUTTON_ID = "answer-b-button-id";


    public VoteAnswerBButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
