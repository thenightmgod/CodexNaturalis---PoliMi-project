package it.polimi.ingsw.Model.situaDeck;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;

/**
 * Represents a deck of Resource Cards used in the game. Each Resource Card in the deck corresponds
 * to a JSON object with specific attributes:
 * - "Id": the unique identifier of the Resource Card.
 * - "BackRes": an array representing the resources present on the back of the card.
 * - "Color": the color of the card.
 * - "Points": the number of points awarded by the card.
 * - "Corners": an array of objects representing the corners of the card, including the resource and orientation of each corner.
 *
 * @see Deck
 * @see ResourceCard
 */

public class ResourceDeck extends Deck{

    /**
     * Constructs a new ResourceDeck, initializing it with Resource Cards read from a JSON file.
     */
    public ResourceDeck(){
        super("Resource");
    }
}
