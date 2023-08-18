package de.mme.qbot.services;

import de.mme.qbot.model.domain.Question;
import de.mme.qbot.repositories.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService{

    IQuestionRepository repository;

    List<Question> alreadyGottenQuestion = new ArrayList<>();

    @Autowired
    public QuestionService(IQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Question> getUniqueRandomQuestion(){
        Question retQuestion = null;
        boolean isReturnQuestionFound = false;

        // if we already delievered all available questions, we clear the list and begin from the beginning
        if(alreadyGottenQuestion.size()>= repository.count()){
            alreadyGottenQuestion.clear();
        }

        // Get all Questions and shuffle the List for randomness
        List<Question> shuffledQuestionList = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(shuffledQuestionList::add);
        Collections.shuffle(shuffledQuestionList);

        // Get Question from shuffled List. If Question was not gotten already, return the question
        qLoop : for(Question q :shuffledQuestionList){
            if(!alreadyGottenQuestion.contains(q)){
                alreadyGottenQuestion.add(q);
                retQuestion = q;
                break qLoop;
            }
        }
        return Optional.ofNullable(retQuestion);
    }

    @Override
    public Optional<Question> getQuestionById(long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public Question saveQuestion(Question question) {
        return repository.save(question);
    }

    @Override
    public Iterable<Question> getAllQuestions(){
        return repository.findAll();
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    public void removeQuestionById(long id) {
        repository.deleteById(id);
    }


}
