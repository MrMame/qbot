package de.mme.qbot.services;

import de.mme.qbot.model.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class MaximumQuestionsStoredException extends Exception{
    private List<Question> errQuestion = new ArrayList<>();

    public List<Question> getErrQuestion() {
        return errQuestion;
    }

    public MaximumQuestionsStoredException() {
    }

    public MaximumQuestionsStoredException(Throwable cause, List<Question> errQuestion) {
        super(cause);
        this.errQuestion = errQuestion;
    }
}
