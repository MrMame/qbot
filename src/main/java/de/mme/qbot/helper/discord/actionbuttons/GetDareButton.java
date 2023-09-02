package de.mme.qbot.helper.discord.actionbuttons;

import java.util.function.Consumer;

public class GetDareButton extends AbstractButton{

    private static final String BUTTON_LABEL = "Pflicht";
    public static final String BUTTON_ID = "new-dare-button-id";


    public GetDareButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
