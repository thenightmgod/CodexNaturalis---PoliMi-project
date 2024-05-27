package it.polimi.ingsw.Model.Messages;

public class ExceptionMessage extends Message {
    private String details;

    public ExceptionMessage(String details) {
        super("ExceptionMessage");
        this.details=details;
    }

    public String getDetails() {
        return details;
    }
}
