package de.mme.qbot.services;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.repositories.IDareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class DareService implements IDareService {

    public static final int MAXIMUM_NUMBERS_OF_DARES_ALLOWED = 500;
    IDareRepository repository;

    List<Dare> alreadyGottenDares = new ArrayList<>();

    @Autowired
    public DareService(IDareRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Dare> getUniqueRandomDare() {
        Dare retDare = null;
        boolean isReturnDareFound = false;

        // if we already delievered all available questions, we clear the list and begin from the beginning
        if(alreadyGottenDares.size()>= repository.count()){
            alreadyGottenDares.clear();
        }

        // Get all Questions and shuffle the List for randomness
        List<Dare> shuffledDareList = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(shuffledDareList::add);
        Collections.shuffle(shuffledDareList);

        // Get Question from shuffled List. If Question was not gotten already, return the question
        qLoop : for(Dare d :shuffledDareList){
            if(!alreadyGottenDares.contains(d)){
                alreadyGottenDares.add(d);
                retDare = d;
                break qLoop;
            }
        }
        return Optional.ofNullable(retDare);
    }

    @Override
    public Optional<Dare> getDareById(long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public Dare saveDare(Dare dare) throws MaximumDaresStoredException, TextIsTooLongException {
        if(repository.count() >= MAXIMUM_NUMBERS_OF_DARES_ALLOWED) throw new MaximumDaresStoredException();
        if(dare.getText().length()> MAXIMUM_DARE_TEXT_LENGTH) throw new TextIsTooLongException("Text of dare is too long. Maximum is " + MAXIMUM_DARE_TEXT_LENGTH + " characters.");
        return repository.save(dare);
    }

    @Override
    public Iterable<Dare> getAllDares() {
        return repository.findAll();
    }

    @Override
    public void removeAll() {
        alreadyGottenDares.clear();
        repository.deleteAll();
    }

    @Override
    public void removeDareById(long id) {
        repository.deleteById(id);
    }
}
