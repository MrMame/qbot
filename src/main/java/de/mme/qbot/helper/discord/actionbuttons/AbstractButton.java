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
    }

    @Override
    public Consumer<ActionButtonFiredEvent> getActionButtonEventHandler() {
        return this.eventHandler;
    }

    @Override
    public void setActionButtonEventHandler(Consumer<ActionButtonFiredEvent> commandHandler) {
        this.eventHandler = commandHandler;
    }


}
