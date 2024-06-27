package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;

import java.util.LinkedList;
/**
 * The UpdateResourceDeckMessage class extends the Message class and represents a message that contains the updated resource deck.
 */
public class UpdateResourceDeckMessage extends Message{
    /**
     * The name of the player to whom is sent the updated resource deck.
     */
    public String name;
    /**
     * The updated resource deck.
     */
    public LinkedList<ResourceCard> deck;
    /**
     * A boolean that indicates if the game has just started.
     */
    public boolean start;
    /**
     * Constructs a new UpdateResourceDeckMessage with the specified name, boolean and updated resource deck.
     *
     * @param n The name of the player to whom is sent the updated resource deck.
     * @param start A boolean that indicates if the game has just started.
     * @param d The updated resource deck.
     */
    public UpdateResourceDeckMessage(String n, boolean start, LinkedList<ResourceCard> d){
        super("UpdateResourceDeckMessage");
        this.name = n;
        this.deck = d;
        this.start = start;
    }
    /**
     * Returns the name of the player to whom is sent the updated resource deck.
     *
     * @return The name of the player to whom is sent the updated resource deck.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the updated resource deck.
     *
     * @return The updated resource deck.
     */
    public LinkedList<ResourceCard> getDeck() {
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
