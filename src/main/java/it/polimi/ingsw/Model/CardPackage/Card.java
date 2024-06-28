package it.polimi.ingsw.Model.CardPackage;

import java.io.Serializable;

/**
 * Represents a generic card in the game.
 */
public abstract class Card implements Serializable {
    /** Represents the unique identifier of the card. */
    public int Id;
    /**
     * Constructs a new Card with the specified identifier.
     *
     * @param id the unique identifier of the card
     */
    public Card(int id) {
        this.Id = id;
    }

    /**
     * Retrieves the identifier of the card.
     *
     * @return the identifier of the card
     */
    public int getId() {
        return Id;
    }
    /**
     * Sets the identifier of the card.
     *
     * @param id the identifier of the card
     */
    public void setId(int id) {
        Id = id;
    }

}
