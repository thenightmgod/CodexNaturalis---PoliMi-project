package it.polimi.ingsw.Model.Messages;

public class ChoosePersonalGoalMessage extends Message {
    public int i;

    public ChoosePersonalGoalMessage(int i, String name){
        super("ChoosePersonalGoalMessage", name);
        this.i=i;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getI() {
        return i;
    }
}
