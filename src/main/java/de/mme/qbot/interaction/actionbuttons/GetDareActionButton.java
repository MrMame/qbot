package de.mme.qbot.interaction.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class GetDareActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "Pflicht";
    public static final String BUTTON_ID = "new-dare-button-id";


    public GetDareActionButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
