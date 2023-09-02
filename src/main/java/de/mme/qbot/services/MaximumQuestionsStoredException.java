package de.mme.qbot.services;

import de.mme.qbot.model.domain.IQbotEntity;
import de.mme.qbot.model.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaximumQuestionsStoredException extends Exception{
    private List<Question> errQuestion = new ArrayList<>();

    public List<Question> getErrQuestion() {
        return errQuestion;
    }
    public List<IQbotEntity> getErrEntities(){
        return errQuestion.stream().map((question)-> question).collect(Collectors.toList());
    }

    public MaximumQuestionsStoredException() {
    }

    public MaximumQuestionsStoredException(Throwable cause, List<Question> errQuestion) {
        super(cause);
        this.errQuestion = errQuestion;
    }
}
