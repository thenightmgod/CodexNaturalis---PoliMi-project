package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class LeaveGameMessage extends Message {
    public String name;
    public LeaveGameMessage(String name) {
        super("LeaveGameMessage");
        this.name=name;
    }
    public String getName() {
        return this.name;
    }

}
