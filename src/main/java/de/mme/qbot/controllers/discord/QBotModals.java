package de.mme.qbot.controllers.discord;

import de.mme.qbot.interaction.modals.AnonymAnswerModal;
import de.mme.qbot.interaction.modals.IQbModal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QBotModals {

    private AnonymAnswerModal anonymAnswerModal;
    private List<IQbModal> qbModals = new ArrayList<>();

    public AnonymAnswerModal getAnonymAnswerModal() {
        return anonymAnswerModal;
    }
    public List<IQbModal> getAsList() {
        return qbModals;
    }

    public QBotModals(AnonymAnswerModal anonymAnswerModal) {
        this.anonymAnswerModal = anonymAnswerModal;
        qbModals.add(anonymAnswerModal);
    }
}
