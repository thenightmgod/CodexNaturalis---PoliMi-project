package it.polimi.ingsw.Model.Messages;

import java.util.LinkedList;
/**
 * The DeclareWinnerMessage class extends the Message class and represents a message that is sent when a player wins the game.
 * Each DeclareWinnerMessage has a list of players in the game and the order in which they finished.
 */
public class DeclareWinnerMessage extends Message{
    /**
     * The list of players in the game and the order in which they finished.
     */
    public LinkedList<String> standings;
    /**
     * Constructs a new DeclareWinnerMessage with the specified list of players in the game and the order in which they finished.
     *
     * @param standings The list of players in the game and the order in which they finished.
     */
    public DeclareWinnerMessage(LinkedList<String> standings){
        super("DeclareWinnerMessage");
        this.standings = standings;
    }
    /**
     * Returns the list of players in the game and the order in which they finished.
     *
     * @return The list of players in the game and the order in which they finished.
     */
    public LinkedList<String> getStandings() {
        return standings;
    }
}
