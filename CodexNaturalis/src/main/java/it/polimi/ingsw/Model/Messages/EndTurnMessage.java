package it.polimi.ingsw.Model.Messages;

public class EndTurnMessage extends Message{
    private String name;
    private String mex;
    public EndTurnMessage(String name, String mex){
        super("EndTurnMessage");
        this.name = name;
        this.mex = mex;
    }
    public String getName() {
        return name;
    }

    public String getMex() {
        return mex;
    }
}
