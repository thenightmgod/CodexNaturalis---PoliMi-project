package it.polimi.ingsw.Model.Messages;

public class EndTurnMessage extends Message{
    String name;
    public EndTurnMessage(String name){
        super("EndTurnMessage");
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return super.getType();
    }
}
