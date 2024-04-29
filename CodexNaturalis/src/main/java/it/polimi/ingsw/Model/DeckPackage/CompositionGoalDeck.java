package it.polimi.ingsw.Model.DeckPackage;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;

/**
 * Represents a deck of CompositionGoalCard objects in the game.
 * This deck is specifically designed to contain CompositionGoalCard instances.
 *Each Composition Goal Card in the deck
 * corresponds to a JSON object with specific attributes:
 * - "Id": the unique identifier of the Composition Goal Card.
 * - "Points": the number of points awarded when the goal is achieved.
 * - "comp": the composition pattern required to achieve the goal (DIAGONAL_UP, DIAGONAL_DOWN, L, REVERSE_L, T, REVERSE_T).
 * - "color": the color of the goal card.
 *
 * @see Deck
 * @see CompositionGoalCard
 */

public class CompositionGoalDeck extends Deck{


    /**
     * Constructs a new CompositionGoalDeck.
     * Initializes the deck by loading CompositionGoalCard objects from a JSON file.
     */
    public CompositionGoalDeck(){
        super("CompositionGoal");
        //costruiamo 3 deck diversi e li inizializziamo assieme in un Goal Deck?
    }

    /**
     * Adds a CompositionGoalCard to the deck.
     * The card is added to the bottom of the deck, maintaining the order of existing cards.
     *
     * @param c The CompositionGoalCard to add to the bottom of the deck.
     */
    public void add(CompositionGoalCard c){
        this.getCards().add(c);
    }
}
