package it.polimi.ingsw.Model.Messages;

public class EndTurnMessage extends Message{
    private String name;
    public EndTurnMessage(String name){
        super("EndTurnMessage");
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
