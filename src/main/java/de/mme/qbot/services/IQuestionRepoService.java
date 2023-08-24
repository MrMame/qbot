package de.mme.qbot.services;

import de.mme.qbot.model.domain.Question;

import java.util.Optional;

public interface IQuestionRepoService {

    Integer MAXIMUM_TEXT_LENGTH = 6000;

    public Optional<Question> getUniqueRandomQuestion();

    public Optional<Question> getQuestionById(long id);

    public Question saveQuestion(Question question) throws MaximumQuestionsStoredException,TextIsTooLongException;

    public Iterable<Question> getAllQuestions();

    public void removeAll();

    public void removeQuestionById(long id);

    }
