package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.PlayerPackage.Player;

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

    public int pointsCalc(Player player, CardColor c){
        return 0;
    }

    public int pointsCalc(Player p){
        return 0;
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