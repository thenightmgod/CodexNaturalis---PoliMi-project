package it.polimi.ingsw.Model.Messages;

public class DrawCardMessage extends Message {
    int i;
    int whichOne;

    public DrawCardMessage(int i, int whichOne, String name){
        super("DrawCardMessage", name);
        this.i=i;
        this.whichOne=whichOne;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getI() {
        return i;
    }

    public int getWhichOne() {
        return whichOne;
    }
}
