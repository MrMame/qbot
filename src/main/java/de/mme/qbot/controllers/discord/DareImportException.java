package de.mme.qbot.controllers.discord;

import de.mme.qbot.model.domain.Dare;

import de.mme.qbot.model.domain.IEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DareImportException extends Exception{

    private List<Dare> errorDares = new ArrayList<>();

    public List<Dare> getErrorDares() {
        return errorDares;
    }

    public List<IEntity> getErrorEntites() {
        List<IEntity> errEntities = errorDares.stream().map((dare)-> dare).collect(Collectors.toList());
        return errEntities;
    }



    public DareImportException(String message, List<Dare> errorDares ) {
        super(message);
        this.errorDares = errorDares;
    }
}
