package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

@Component
public class GetDareButton extends AbstractButton {

    private static final String BUTTON_LABEL = "Pflicht";
    public static final String BUTTON_ID = "new-dare-button-id";


    public GetDareButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
