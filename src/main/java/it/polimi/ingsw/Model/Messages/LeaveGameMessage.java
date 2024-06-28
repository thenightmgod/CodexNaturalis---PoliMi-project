package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
/**
 * The LeaveGameMessage class extends the Message class and represents a message that is sent when a player leaves the game.
 */
public class LeaveGameMessage extends Message {
    /**
     * Constructs a new LeaveGameMessage.
     */
    public LeaveGameMessage() {
        super("LeaveGameMessage");
    }

}
