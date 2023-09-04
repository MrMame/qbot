package de.mme.qbot.services;

import de.mme.qbot.model.domain.Question;
import de.mme.qbot.exceptions.MaximumQuestionsStoredException;
import de.mme.qbot.exceptions.TextIsTooLongException;

import java.util.Optional;

public interface IQuestionService {

    Integer MAXIMUM_QUESTION_TEXT_LENGTH = 250;
    Integer MAXIMUM_ANSWER_TEXT_LENGTH = 600;

    public Optional<Question> getUniqueRandomQuestion();

    public Optional<Question> getQuestionById(long id);

    public Question saveQuestion(Question question) throws MaximumQuestionsStoredException, TextIsTooLongException;

    public Iterable<Question> getAllQuestions();

    public void removeAll();

    public void removeQuestionById(long id);

    }
