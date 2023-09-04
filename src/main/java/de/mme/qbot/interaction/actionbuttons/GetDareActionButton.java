package de.mme.qbot.interaction.actionbuttons;

import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Component;

@Component
public class GetDareActionButton extends AbstractActionButton {

    private static final String BUTTON_LABEL = "Pflicht";
    public static final String BUTTON_ID = "new-dare-button-id";


    public GetDareActionButton() {
        super(ButtonStyle.DANGER,BUTTON_ID,BUTTON_LABEL);
    }


}
