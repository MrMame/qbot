package de.mme.qbot.interaction.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class AnonymAnswerActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "Anonym";
    public static final String BUTTON_ID = "anonym-button-id";


    public AnonymAnswerActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
