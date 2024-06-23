package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;

public class UpdateTurnMessage extends Message{
    public Player player;
    public String mex;

    public UpdateTurnMessage(Player p, String mex){
        super("UpdateTurnMessage");
        this.player = p;
        this.mex = mex;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMex() {
        return mex;
    }
}
