package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;

public class UpdateTurnMessage extends Message{
    private Player player;

    public UpdateTurnMessage(Player p){
        super("UpdateTurnMessage");
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }
}
