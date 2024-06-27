package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;

import java.util.LinkedList;
/**
 * The ShowHandMessage class extends the Message class and represents a message that contains the list of the cards in the hand of a player.
 */
public class ShowHandMessage extends Message{
    /**
     * The list of the cards in the hand of a player.
     */
    public LinkedList<PlayableCard> hand;
    /**
     * The name of the player to whom is sent the list of cards in the hand.
     */
    public String name;
    /**
     * Constructs a new ShowHandMessage with the specified list of cards and name.
     *
     * @param cards The list of the cards in the hand of a player.
     * @param name The name of the player to whom is sent the list of cards in the hand.
     */
    public ShowHandMessage(LinkedList<PlayableCard> cards, String name) {
        super("ShowHandMessage");
        this.hand=cards;
        this.name = name;
    }
    /**
     * Returns the list of the cards in the hand of a player.
     *
     * @return The list of the cards in the hand of a player.
     */
    public LinkedList<PlayableCard> getHand() {
        return hand;
    }
    /**
     * Returns the name of the player to whom is sent the list of cards in the hand.
     *
     * @return The name of the player to whom is sent the list of cards in the hand.
     */
    public String getName() {
        return name;
    }
}
