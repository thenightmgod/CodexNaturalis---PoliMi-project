package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;

public class UpdateCommonGoalsMessage extends Message {
    public LinkedList<Integer> goalIds;
    public String name;

    public UpdateCommonGoalsMessage(LinkedList<Integer> goalIds, String name) {
        super("UpdateCommonGoalsMessage");
        this.goalIds = goalIds;
        this.name = name;
    }
    public LinkedList<Integer> getGoalIds() {
        return goalIds;
    }

    public String getName() {
        return name;
    }
}
