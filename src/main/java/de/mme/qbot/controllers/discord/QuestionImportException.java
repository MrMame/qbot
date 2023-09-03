package de.mme.qbot.controllers.discord;

import de.mme.qbot.model.domain.IEntity;
import de.mme.qbot.model.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionImportException extends Exception{

    private List<Question> errorQuestions = new ArrayList<>();

    public List<Question> getErrorQuestions() {
        return errorQuestions;
    }
    public List<IEntity> getErrorEntites() {
        List<IEntity> errEntities = errorQuestions.stream().map((question)-> question).collect(Collectors.toList());
        return errEntities;
    }

    public QuestionImportException(String message,    List<Question> errorQuestions ) {
        super(message);
        this.errorQuestions = errorQuestions;
    }
}
