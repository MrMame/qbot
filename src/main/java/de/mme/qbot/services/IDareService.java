package de.mme.qbot.services;

import de.mme.qbot.model.domain.Dare;

import java.util.Optional;

public interface IDareService {

    Integer MAXIMUM_DARE_TEXT_LENGTH = 250;


    public Optional<Dare> getUniqueRandomDare();

    public Optional<Dare> getDareById(long id);

    public Dare saveDare(Dare dare) throws MaximumDaresStoredException, TextIsTooLongException;

    public Iterable<Dare> getAllDares();

    public void removeAll();

    public void removeDareById(long id);

    }
