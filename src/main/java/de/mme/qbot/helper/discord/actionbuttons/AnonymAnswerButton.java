package de.mme.qbot.helper.discord.actionbuttons;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Component
public class AnonymAnswerButton extends AbstractButton{

    private static final String BUTTON_LABEL = "Anonym";
    public static final String BUTTON_ID = "anonym-button-id";


    public AnonymAnswerButton() {
        super(BUTTON_ID,BUTTON_LABEL);
    }


}
