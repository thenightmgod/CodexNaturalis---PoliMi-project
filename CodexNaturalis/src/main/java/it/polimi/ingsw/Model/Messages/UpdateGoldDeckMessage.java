package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;

import java.util.LinkedList;
/**
 * The UpdateGoldDeckMessage class extends the Message class and represents a message that contains the updated gold deck.
 */
public class UpdateGoldDeckMessage extends Message{
    /**
     * The name of the player to whom is sent the updated gold deck.
     */
    public String name;
    /**
     * The updated gold deck.
     */
    public LinkedList<GoldCard> deck;
    /**
     * A boolean that indicates if the game has just started.
     */
    public boolean start;
    /**
     * Constructs a new UpdateGoldDeckMessage with the specified name, boolean and updated gold deck.
     *
     * @param n The name of the player to whom is sent the updated gold deck.
     * @param start A boolean that indicates if the game has just started.
     * @param d The updated gold deck.
     */
    public UpdateGoldDeckMessage(String n, boolean start, LinkedList<GoldCard> d){
        super("UpdateGoldDeckMessage");
        this.name = n;
        this.deck = d;
        this.start = start;
    }
    /**
     * Returns the name of the player to whom is sent the updated gold deck.
     *
     * @return The name of the player to whom is sent the updated gold deck.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the updated gold deck.
     *
     * @return The updated gold deck.
     */
    public LinkedList<GoldCard> getDeck() {
        return deck;
    }
    /**
     * Returns a boolean that indicates if the game has just started.
     *
     * @return A boolean that indicates if the game has just started.
     */
    public boolean isStart() {
        return start;
    }
}
