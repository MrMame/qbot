package de.mme.qbot.interaction.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class GetQuestionActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "Wahrheit";
    public static final String BUTTON_ID = "new-question-button-id";


    public GetQuestionActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
