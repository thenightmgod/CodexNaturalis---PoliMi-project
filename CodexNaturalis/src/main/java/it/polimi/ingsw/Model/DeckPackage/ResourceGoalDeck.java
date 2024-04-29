package it.polimi.ingsw.Model.DeckPackage;
import it.polimi.ingsw.Model.CardPackage.GoldCardPackage.ResourceGoalCard;

/**
 * Represents a deck of Resource Goal Cards used in the game. Each Resource Goal Card in the deck corresponds
 * to a JSON object with specific attributes:
 * - "Id": the unique identifier of the Resource Goal Card.
 * - "Points": the number of points awarded by the card.
 * - "Res": the color resource associated with the goal card.
 *
 * @see Deck
 * @see ResourceGoalCard
 */


public class ResourceGoalDeck extends Deck{


    /**
     * Constructs a new ResourceGoalDeck, initializing it with Resource Goal Cards read from a JSON file.
     */
    public ResourceGoalDeck(){
        super("ResourceGoal");
    }
}
