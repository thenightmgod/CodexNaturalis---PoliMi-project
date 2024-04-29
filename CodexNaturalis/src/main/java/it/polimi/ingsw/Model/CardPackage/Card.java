package it.polimi.ingsw.Model.CardPackage;

/**
 * Represents a generic card in the game.
 */
public abstract class Card {
    private final int Id;

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


}
