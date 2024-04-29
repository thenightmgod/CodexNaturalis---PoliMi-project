package it.polimi.ingsw.Model.DeckPackage;
import it.polimi.ingsw.Model.CardPackage.GoldCardPackage.ObjectsGoalCard;

/**
 * Represents a deck of Objects Goal Cards used in the game. Each Objects Goal Card in the deck
 * corresponds to a JSON object with specific attributes:
 * - "Id": the unique identifier of the Objects Goal Card.
 * - "Points": the number of points awarded every time the goal is achieved.
 * - "Obj": an array representing the number of each object required to achieve the goal.
 *
 * @see Deck
 * @see ObjectsGoalCard
 */


public class ObjectsGoalDeck extends Deck{

    /**
     * Constructs a new ObjectsGoalDeck, initializing it with Objects Goal Cards read from a JSON file.
     */

    public ObjectsGoalDeck(){
        super("ObjectsGoal");
    }
}
