package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class VoteAnswerAButton extends AbstractButton{

    private static final String BUTTON_LABEL = "A";
    public static final String BUTTON_ID = "answer-a-button-id";


    public VoteAnswerAButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
