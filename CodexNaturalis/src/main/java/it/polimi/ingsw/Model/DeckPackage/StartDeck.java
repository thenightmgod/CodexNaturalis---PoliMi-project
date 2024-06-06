package it.polimi.ingsw.Model.DeckPackage;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;


/**
 * Represents a deck of Start Cards used in the game. Each Start Card in the deck corresponds
 * to a JSON object with specific attributes:
 * - "Id": the unique identifier of the Start Card.
 * - "BackRes": an array representing the resources on the back side of the card.
 * - "Corners": an array of Corner objects representing the corners of the card.
 *   Each Corner object has attributes:
 *     - "Res": the resource type associated with the corner.
 *     - "Orient": the orientation of the corner.
 *
 * @see Deck
 * @see StartCard
 */
public class StartDeck extends Deck{


    /**
     * Constructs a new StartDeck, initializing it with Start Cards read from a JSON file.
     */
    public StartDeck(){
        super("Start");
    }
}
