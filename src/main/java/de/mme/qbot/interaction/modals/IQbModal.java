package de.mme.qbot.interaction.modals;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.function.Consumer;

public interface IQbModal {

    String getId();

    Modal getModal();

    Consumer<ModalInteractionEvent> getEventHandler();
    void setEventHandler(Consumer<ModalInteractionEvent> commandHandler);

}
