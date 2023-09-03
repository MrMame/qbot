package de.mme.qbot.interaction.actionbuttons;

import java.util.function.Consumer;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractActionButton extends ListenerAdapter implements IActionButton {


    private Button button;
    private String id;
    private String label;



    private Consumer<ButtonInteractionEvent> eventHandler;

    static Logger logger = LoggerFactory.getLogger(AbstractActionButton.class);

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



    @Override
    public Consumer<ButtonInteractionEvent> getEventHandler() {
        return eventHandler;
    }
    @Override
    public void setEventHandler(Consumer<ButtonInteractionEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }


    public AbstractActionButton(String id, String label) {
        this.id = id;
        this.label = label;
        this.button = Button.secondary(id,label);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
    if(event.getComponentId().equals(this.id)){
        logger.info("...Start handling actionButton " + this.id);
        this.eventHandler.accept(event);
    }

    }
}
