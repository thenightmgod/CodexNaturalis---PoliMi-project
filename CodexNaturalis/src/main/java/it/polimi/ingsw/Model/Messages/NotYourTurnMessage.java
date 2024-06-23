package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;

public class NotYourTurnMessage extends Message{
    public Player p;
    public String mex;
    public NotYourTurnMessage(Player p, String mess){
        super("NotYourTurnMessage");
        this.p = p;
        this.mex = mess;
    }

    public String getMex() {
        return mex;
    }

    public Player getPlayer() {
        return p;
    }
}
