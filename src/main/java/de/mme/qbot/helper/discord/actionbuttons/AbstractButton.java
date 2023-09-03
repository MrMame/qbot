package de.mme.qbot.helper.discord.actionbuttons;

import java.util.function.Consumer;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

public abstract class AbstractButton implements IActionButton {


    private Button button;
    private String id;
    private String label;

    private Consumer<ActionButtonFiredEvent> eventHandler;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Button getButton() {
        return button;
    }

    public AbstractButton(String id, String label) {
        this.id = id;
        this.label = label;
        this.button = Button.secondary(id,label);
    }


}
