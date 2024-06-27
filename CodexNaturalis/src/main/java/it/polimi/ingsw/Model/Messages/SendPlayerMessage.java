package it.polimi.ingsw.Model.Messages;

import java.util.LinkedList;
/**
 * The SendPlayerMessage class extends the Message class and represents a message that contains the list of the other players in the game.
 */
public class SendPlayerMessage extends Message{
    /**
     * The list of the other players in the game.
     */
    public LinkedList<String> players;
    /**
     * Constructs a new SendPlayerMessage with the specified list of players.
     *
     * @param players The list of the other players in the game.
     */
    public SendPlayerMessage(LinkedList<String> players) {
        super("SendPlayerMessage");
        this.players = players;
    }
    /**
     * Returns the list of the other players in the game.
     *
     * @return The list of the other players in the game.
     */
    public LinkedList<String> getPlayers() {
        return players;
    }
}
