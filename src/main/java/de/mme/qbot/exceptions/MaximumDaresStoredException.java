package de.mme.qbot.exceptions;

import de.mme.qbot.model.domain.Dare;

import de.mme.qbot.model.domain.IEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MaximumDaresStoredException extends Exception{
    private List<Dare> errDares = new ArrayList<>();

    public List<Dare> getErrDares() {
        return errDares;
    }

    public List<IEntity> getErrEntities(){
        return errDares.stream().map(dare -> dare).collect(Collectors.toList());
    }


    public MaximumDaresStoredException() {
    }

    public MaximumDaresStoredException(Throwable cause, List<Dare> errDares) {
        super(cause);
        this.errDares = errDares;
    }
}
