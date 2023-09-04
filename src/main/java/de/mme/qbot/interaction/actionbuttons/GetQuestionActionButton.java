package de.mme.qbot.interaction.actionbuttons;

import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Component;

@Component
public class GetQuestionActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "Wahrheit";
    public static final String BUTTON_ID = "new-question-button-id";


    public GetQuestionActionButton() {
        super(ButtonStyle.PRIMARY,BUTTON_ID,BUTTON_LABEL);
    }


}
