package de.mme.qbot.services;

import de.mme.qbot.model.domain.Question;
import de.mme.qbot.repositories.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionsService implements IQuestionService{

    IQuestionRepository repository;

    List<Question> alreadyGottenQuestion = new ArrayList<>();

    @Autowired
    public QuestionsService(IQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Question> getUniqueRandomQuestion(){
        Question retQuestion = null;
        boolean isReturnQuestionFound = false;

        for(Question q :repository.findAll()){
            if(isReturnQuestionFound == false
                    && !alreadyGottenQuestion.contains(q)){
                alreadyGottenQuestion.add(q);
                retQuestion = q;
                isReturnQuestionFound = true;
            }

        }
        return Optional.of(retQuestion);
    }

    @Override
    public Optional<Question> getQuestionById(long id) {
        return Optional.of(repository.findById(id));
    }

    @Override
    public Question saveQuestion(Question question) {
        return repository.save(question);
    }


}
