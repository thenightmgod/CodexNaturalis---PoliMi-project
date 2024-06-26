package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.PlayerPackage.Player;

/**
 * Represent the generic GoalCard. The GoalDeck contains 16 of them.
 */

public class GoalCard extends Card {
    /**
     * The points of the GoalCard
     */
    private final int points;

    /**
     * Constructor of the GoalCard
     * @param id the id of the GoalCard
     * @param p the points of the GoalCard
     */
    public GoalCard(int id, int p) {
        super(id);
        this.points=p;
    }

    /**
     * Calculate the points of the GoalCard
     * @param player the player to calculate the points
     * @param c the color of the card
     * @return the points of the GoalCard
     */
    public int pointsCalc(Player player, CardColor c){
        return 0;
    }

    /**
     * Calculate the points of the GoalCard
     * @param p the player to calculate the points
     * @return the points of the GoalCard
     */
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