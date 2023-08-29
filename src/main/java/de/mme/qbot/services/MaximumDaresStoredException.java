package de.mme.qbot.services;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class MaximumDaresStoredException extends Exception{
    private List<Dare> errDares = new ArrayList<>();

    public List<Dare> getErrDares() {
        return errDares;
    }

    public MaximumDaresStoredException() {
    }

    public MaximumDaresStoredException(Throwable cause, List<Dare> errDares) {
        super(cause);
        this.errDares = errDares;
    }
}
