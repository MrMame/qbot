package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Component
public class GetQuestionButton extends AbstractButton{

    private static final String BUTTON_LABEL = "Wahrheit";
    public static final String BUTTON_ID = "new-question-button-id";


    public GetQuestionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
