package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;
/**
 * The ChooseGoalCardMessage class extends the Message class and represents a message that is sent to the server when a player chooses a goal card.
 * Each ChooseGoalCardMessage has an index i and the name of the player who chose the goal card.
 */
public class ChooseGoalCardMessage extends Message {
    /**
     * The index of the goal card that the player chose.
     */
    public int i;
    /**
     * The name of the player who chose the goal card.
     */
    public String name;
    /**
     * Constructs a new ChooseGoalCardMessage with the specified index and the name of the player who chose the goal card.
     *
     * @param i The index of the goal card that the player chose.
     * @param name The name of the player who chose the goal card.
     */
    public ChooseGoalCardMessage(int i, String name){
        super("ChooseGoalCardMessage");
        this.i=i;
        this.name = name;
    }
    /**
     * Returns the index of the goal card that the player chose.
     *
     * @return The index of the goal card that the player chose.
     */
    public int getI() {
        return i;
    }
    /**
     * Returns the name of the player who chose the goal card.
     *
     * @return The name of the player who chose the goal card.
     */
    public String getName() {
        return name;
    }
}
