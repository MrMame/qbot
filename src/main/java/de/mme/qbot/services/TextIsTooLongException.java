package de.mme.qbot.services;

public class TextIsTooLongException extends Exception{

    public TextIsTooLongException(String message) {
        super(message);
    }

}
