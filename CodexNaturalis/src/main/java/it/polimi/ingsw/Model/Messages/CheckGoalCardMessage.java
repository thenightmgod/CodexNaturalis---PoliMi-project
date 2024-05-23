package it.polimi.ingsw.Model.Messages;

public class CheckGoalCardMessage extends Message{
    public String name;
    public CheckGoalCardMessage(String name){
        super("CheckGoalCardMessage");
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return super.getType();
    }
}
