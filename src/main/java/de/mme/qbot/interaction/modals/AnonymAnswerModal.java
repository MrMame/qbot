package de.mme.qbot.interaction.modals;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.springframework.stereotype.Component;

@Component
public class AnonymAnswerModal extends AbstractQbotModal {

    public static final String MODAL_ID = "anonym-answer-modal-id";
    public static final String MODAL_TITLE = "Anonym Antworten";
    public static final String MODAL_ANONYM_ASNWER_INPUTFIELD_ID = "anonym-message";

    public static final String MODAL_ANONYM_ANSWER_INPUTFIELD_LABEL = "Antworttext";
    public static final String MODAL_ANONYM_ANSWER_INPUTFIELD_PLACEHOLDER = "Schreibe hier deinen Text hinein...";

    private static final int minAnswerLength = 1;
    private static final int maxAnswerLength = 1000;



    public AnonymAnswerModal() {
        super(MODAL_ID);
        this.modal = createAnonymQuestionModal(MODAL_ANONYM_ASNWER_INPUTFIELD_ID);
    }


    private Modal createAnonymQuestionModal(String bodyFieldId){
        TextInput body = TextInput.create(bodyFieldId, MODAL_ANONYM_ANSWER_INPUTFIELD_LABEL, TextInputStyle.PARAGRAPH)
                .setPlaceholder(MODAL_ANONYM_ANSWER_INPUTFIELD_PLACEHOLDER)
                .setMinLength(minAnswerLength)
                .setMaxLength(maxAnswerLength)
                .build();
        // Create the Modal object
        Modal modal = Modal.create(AnonymAnswerModal.MODAL_ID, AnonymAnswerModal.MODAL_TITLE)
                .addActionRows(ActionRow.of(body))
                .build();
        return modal;
    }

}
