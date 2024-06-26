package it.polimi.ingsw.Model.Messages;

public class IsAliveMessage extends Message {

    public String name;
    public String message;

    public IsAliveMessage(String name, String message) {
        super("IsAliveMessage");
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
