package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;

public class NotYourTurnMessage extends Message{
    private Player p;
    public NotYourTurnMessage(Player p){
        super("NotYourTurnMessage");
        this.p = p;
    }

    public Player getPlayer() {
        return p;
    }
}
