package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;
/**
 * The UpdateCommonGoalsMessage class extends the Message class and represents a message that contains the common goals.
 */
public class UpdateCommonGoalsMessage extends Message {
    /**
     * The list of the common goals.
     */
    public LinkedList<Integer> goalIds;
    /**
     * The name of the player to whom is sent the list of common goals.
     */
    public String name;
    /**
     * Constructs a new UpdateCommonGoalsMessage with the specified list of common goals and name.
     *
     * @param goalIds The list of the common goals.
     * @param name The name of the player to whom is sent the list of common goals.
     */
    public UpdateCommonGoalsMessage(LinkedList<Integer> goalIds, String name) {
        super("UpdateCommonGoalsMessage");
        this.goalIds = goalIds;
        this.name = name;
    }
    /**
     * Returns the list of the common goals.
     *
     * @return The list of the common goals.
     */
    public LinkedList<Integer> getGoalIds() {
        return goalIds;
    }
    /**
     * Returns the name of the player to whom is sent the list of common goals.
     *
     * @return The name of the player to whom is sent the list of common goals.
     */
    public String getName() {
        return name;
    }
}
