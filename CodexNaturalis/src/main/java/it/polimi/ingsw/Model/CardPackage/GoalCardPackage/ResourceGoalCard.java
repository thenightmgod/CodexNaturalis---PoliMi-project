package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;

/**
 * Represents the Goal Card whose specific Goal is the achievement of a
 *  certain number (3) of one of the four resources, counted on the playing field of a player.
 *
 */
public class ResourceGoalCard extends GoalCard {
    private final Resources Res;


    /**
     *Constructs a new ResourceGoalCard with its individual id, points and Resources R
     *
     * @param id the ResourceGoalCard's identifier
     * @param points the points the player makes each time he reaches the goal
     * @param R the specific Resource that concerns the goal of the ResourceGoalCard.
     *
     */
    public ResourceGoalCard(int id, int points, Resources R) {
        super(id, points);
        this.Res=R;
    }

    /**
     * Get the specific Resource that concerns the ResourceGoalCard's goal.
     * @return the one of the Resources that requires the ResourceGoalCard's achievement.
     */
    public Resources getRes() {
        return Res;
    }

    /**
     * Calculates the total points the player has made:
     * for each recurrence of multiple of 3 of the ResourceGoalCard's specific resource (that getRes() returns),
     * visible on the playing field of the player (the input parameter),
     * he earns a total of points equal to the recurrences multiplied by
     * the specific ResourceGoalCard's points (that getPoints() returns)
     *
     *
     * @param player the player on whose playing field we want to calculate the points
     * @return the total points the specific player has made
     */
    public int pointsCalc(Player player) {
        int ricorrenze, totpoints=0;

        ricorrenze= player.getResourceCounter(Res);
        totpoints = ( ricorrenze/ 3 ) * this.getPoints();
        //se getpoints() mi ritorna 2, mi dà due punti ogni ogni 3 risorse di quel tipo
        return totpoints;
    }
}