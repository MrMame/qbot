package de.mme.qbot.controllers.discord;

public class ErrorReadingImportFileException extends Exception{
    public ErrorReadingImportFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
