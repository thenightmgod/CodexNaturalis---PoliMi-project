package it.polimi.ingsw.Model.Messages;

public class ExceptionMessage extends Message {
    private String details;
    private String exception;

    public ExceptionMessage(String exception, String details) {
        super("ExceptionMessage");
        this.details=details;
        this.exception = exception;
    }

    public String getDetails() {
        return details;
    }

    public String getException() {
        return exception;
    }
}
