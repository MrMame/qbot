package de.mme.qbot.controllers.discord;

import de.mme.qbot.interaction.modals.AnonymAnswerModal;
import de.mme.qbot.interaction.modals.IQbotModal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QBotModals {

    private AnonymAnswerModal anonymAnswerModal;
    private List<IQbotModal> qbModals = new ArrayList<>();

    public AnonymAnswerModal getAnonymAnswerModal() {
        return anonymAnswerModal;
    }
    public List<IQbotModal> getAsList() {
        return qbModals;
    }

    public QBotModals(AnonymAnswerModal anonymAnswerModal) {
        this.anonymAnswerModal = anonymAnswerModal;
        qbModals.add(anonymAnswerModal);
    }
}
