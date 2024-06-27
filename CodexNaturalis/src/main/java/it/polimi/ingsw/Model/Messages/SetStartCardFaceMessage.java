package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
/**
 * The SetStartCardFaceMessage class extends the Message class and represents a message that is sent when a player sets the face of the start card.
 * Each SetStartCardFaceMessage has a boolean Face and the name of the player who placed the card.
 */
public class SetStartCardFaceMessage extends Message {
    /**
     * The face of the card (true if it is front, false if it is back).
     */
    public boolean Face;
    /**
     * The name of the player who placed the card.
     */
    public String name;
    /**
     * Constructs a new SetStartCardFaceMessage with the specified face and name.
     *
     * @param face The face of the card.
     * @param name The name of the player who placed the card.
     */
    public SetStartCardFaceMessage(boolean face, String name) {
        super("SetStartCardFaceMessage");
        this.Face=face;
        this.name = name;
    }
    /**
     * Returns the face of the card.
     *
     * @return The face of the card.
     */
    public boolean getFace() {
        return Face;
    }
    /**
     * Returns the name of the player who placed the card.
     *
     * @return The name of the player who placed the card.
     */
    public String getName() {
        return name;
    }
}
