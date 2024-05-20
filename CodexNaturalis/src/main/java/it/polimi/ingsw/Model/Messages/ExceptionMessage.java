package it.polimi.ingsw.Model.Messages;

public class ExceptionMessage extends Message {
    private String details;

    public ExceptionMessage(String details) {
        super("ExceptionMessage");
        this.details=details;
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }
}
