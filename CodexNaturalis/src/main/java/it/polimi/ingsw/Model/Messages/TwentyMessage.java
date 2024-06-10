package it.polimi.ingsw.Model.Messages;

public class TwentyMessage extends Message {
    public String name;

    public TwentyMessage(String name){
        super("TwentyMessage");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
