package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;
/**
 * The ShowGoalsMessage class extends the Message class and represents a message that contains the list of the goal cards of a player.
 */
public class ShowGoalsMessage extends Message{
    /**
     * The list of the goal cards of a player.
     */
    public LinkedList<Integer> goalIds;
    /**
     * The name of the player to whom is sent the list of goal cards.
     */
    public String name;
    /**
     * Constructs a new ShowGoalsMessage with the specified list of goal cards and name.
     *
     * @param cards The list of the goal cards of a player.
     * @param name The name of the player to whom is sent the list of goal cards.
     */

    public ShowGoalsMessage(LinkedList<Integer> cards, String name) {

        super("ShowGoalsMessage");
        this.goalIds = cards;
        this.name = name;
    }
    /**
     * Returns the list of the goal cards of a player.
     *
     * @return The list of the goal cards of a player.
     */
    public LinkedList<Integer> getGoals() {
        return goalIds;
    }
    /**
     * Returns the name of the player to whom is sent the list of goal cards.
     *
     * @return The name of the player to whom is sent the list of goal cards.
     */
    public String getName() {
        return name;
    }
}
