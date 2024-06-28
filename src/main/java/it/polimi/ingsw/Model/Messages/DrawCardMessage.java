package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;
/**
 * The DrawCardMessage class extends the Message class and represents a message that is sent when a player draws a card.
 * Each DrawCardMessage has an index for the specified deck, for the specified card that was drawn, and the name of the player who drew the card.
 */
public class DrawCardMessage extends Message {
    /**
     * The index for the specified deck.
     */
    public int i;
    /**
     * The specified card that was drawn.
     */
    public int whichOne;
    /**
     * The name of the player who drew the card.
     */
    public String name;
    /**
     * Constructs a new DrawCardMessage with the index for the specified deck, for the specified card that was drawn, and the name of the player who drew the card.
     *
     * @param i The index for the specified deck.
     * @param whichOne The specified card that was drawn.
     * @param name The name of the player who drew the card.
     */
    public DrawCardMessage(int i, int whichOne, String name){
        super("DrawCardMessage");
        this.i=i;
        this.whichOne=whichOne;
        this.name = name;
    }

    /**
     * Returns the index for the specified deck.
     *
     * @return The index for the specified deck.
     */
    public int getI() {
        return i;
    }
    /**
     * Returns the specified card that was drawn.
     *
     * @return The specified card that was drawn.
     */
    public int getWhichOne() {
        return whichOne;
    }
    /**
     * Returns the name of the player who drew the card.
     *
     * @return The name of the player who drew the card.
     */
    public String getName() {
        return name;
    }
}
