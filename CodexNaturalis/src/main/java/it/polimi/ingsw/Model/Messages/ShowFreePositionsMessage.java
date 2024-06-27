package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Position;
import java.util.LinkedList;
/**
 * The ShowFreePositionsMessage class extends the Message class and represents a message that contains the list of the free positions in the board.
 */
public class ShowFreePositionsMessage extends Message {
    /**
     * The name of the player to whom is sent the list of free positions.
     */
    public String name;
    /**
     * The list of the free positions in the board.
     */
    public LinkedList<Position> freePosition;
    /**
     * Constructs a new ShowFreePositionsMessage with the specified name and list of free positions.
     *
     * @param name The name of the player to whom is sent the list of free positions.
     * @param positions The list of the free positions in the board.
     */
    public ShowFreePositionsMessage(String name, LinkedList<Position> positions) {
        super("ShowFreePositionsMessage");
        this.name=name;
        this.freePosition=positions;
    }
    /**
     * Returns the list of the free positions in the board.
     *
     * @return The list of the free positions in the board.
     */
    public LinkedList<Position> getFreePosition() {
        return freePosition;
    }
    /**
     * Returns the name of the player to whom is sent the list of free positions.
     *
     * @return The name of the player to whom is sent the list of free positions.
     */
    public String getName() {
        return name;
    }
}
