package it.polimi.ingsw.Model.CardPackage.GoldCardPackage;

import  it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;

/**
 * Represents the GoalCard whose goal requires a specific configuration
 * of cards' positions on the playing field.
 */
public class CompositionGoalCard extends GoalCard {

    private final Composition comp;
    private final CardColor color;

    /**
     * Constructs a new CompositionGoalCard, with its id, points and Composition.
     * @param id
     * @param points
     * @param c The specific composition between the three possible
     * @param color the specific color of the CompositionGoalCard
     */
    public CompositionGoalCard(int id, int points, Composition c, CardColor color) {
        super(id,points);
        this.comp=c;
        this.color=color;
    }

    /**
     * Get the specific Composition
     * @return The specific Composition
     */
    public Composition getComp() {
        return comp;
    }


    /**
     * For every player, Calculate the points related to the GoalCards' achievement (both the individual one and the common one)
     *
     *
     * @param p The player whose Points
     *         related to GoalCards' achievement
     *          we need to calculate
     * @return the total Goalpoints of the Player
     */
    //non posso realizzarla fino a quando non so come funziona precisamente player ed il suo campo da gioco
    public int pointsCalc(Player p){
        return 0; //todo
    }
}