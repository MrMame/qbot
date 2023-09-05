package de.mme.qbot.interaction.modals;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import de.mme.qbot.interaction.slashcommands.AbstractSlashCommand;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.Modal;
import okhttp3.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class AbstractQbModal extends ListenerAdapter implements IQbModal{

    private String id = "";
    protected Modal modal;


    private Consumer<ModalInteractionEvent> commandHandler;

    static Logger logger = LoggerFactory.getLogger(AbstractQbModal.class);




    public Modal getModal() {
        return modal;
    }

    @Override
    public Consumer<ModalInteractionEvent> getEventHandler() {
        return this.commandHandler;
    }

    @Override
    public void setEventHandler(Consumer<ModalInteractionEvent> commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String getId(){
        return this.id;
    }


    public AbstractQbModal(String id) {
        this.id = id;
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if(event.getModalId().equals(this.id)){
            logger.info("...Start handling modal event from modal-id: " + this.id);
            this.commandHandler.accept(event);
        }
    }


}
