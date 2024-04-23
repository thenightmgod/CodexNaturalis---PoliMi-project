package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaCard.Card;

/**
 * Represent the generic GoalCard. The GoalDeck contains 16 of them.
 */

public abstract class GoalCard extends Card {
    private final int points;

    /**
     * Constructs a new GoalCard object with the id and points
     *
     *  @param id
     * @param p
     */
    public GoalCard(int id, int p) {
        super(id);
        this.points=p;
    }

    /**
     * Get the id of the specific GoalCard
     * @return the id of the specific GoalCard
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /**Get the points of the specific GoalCard
     *
     * @return the points of the specific GoalCard
     */
    public int getPoints() {
        return this.points;
    }
}
