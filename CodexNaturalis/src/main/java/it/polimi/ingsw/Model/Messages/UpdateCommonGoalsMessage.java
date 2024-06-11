package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;

public class UpdateCommonGoalsMessage extends Message {
    private LinkedList<GoalCard> goals;
    private String name;

    public UpdateCommonGoalsMessage(LinkedList<GoalCard> cards, String name) {
        super("UpdateCommonGoalsMessage");
        this.goals = cards;
        this.name = name;
    }
    public LinkedList<GoalCard> getGoals() {
        return goals;
    }

    public String getName() {
        return name;
    }
}
