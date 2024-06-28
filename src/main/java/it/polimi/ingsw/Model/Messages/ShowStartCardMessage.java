package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
/**
 * The ShowStartCardMessage class extends the Message class and represents a message that contains the start card of the player.
 */
public class ShowStartCardMessage extends Message{
    /**
     * The start card of the player.
     */
    public StartCard start;
    /**
     * Constructs a new ShowStartCardMessage with the specified start card.
     *
     * @param s The start card of the player.
     */
    public ShowStartCardMessage(StartCard s){
        super("ShowStartCardMessage");
        this.start = s;
    }
    /**
     * Returns the start card of the player.
     *
     * @return The start card of the player.
     */
    public StartCard getStart() {
        return start;
    }
}
