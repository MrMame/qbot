package de.mme.qbot.services;

import de.mme.qbot.model.domain.Question;

import java.util.Optional;

public interface IQuestionService {
    public Optional<Question> getUniqueRandomQuestion();

    public Optional<Question> getQuestionById(long id);

    public Question saveQuestion(Question question);

    public Iterable<Question> getAllQuestions();

    public void removeAll();

    public void removeQuestionById(long id);

    }
