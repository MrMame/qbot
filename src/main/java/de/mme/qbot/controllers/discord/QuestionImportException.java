package de.mme.qbot.controllers.discord;

import de.mme.qbot.model.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionImportException extends Exception{

    private List<Question> errorQuestions = new ArrayList<>();

    public List<Question> getErrorQuestions() {
        return errorQuestions;
    }

    public QuestionImportException(String message,    List<Question> errorQuestions ) {
        super(message);
        this.errorQuestions = errorQuestions;
    }
}
