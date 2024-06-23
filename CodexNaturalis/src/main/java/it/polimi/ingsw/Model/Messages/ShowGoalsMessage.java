package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;

public class ShowGoalsMessage extends Message{
    public LinkedList<GoalCard> goals;
    public String name;

    public ShowGoalsMessage(LinkedList<GoalCard> cards, String name) {
        super("ShowGoalsMessage");
        this.goals=cards;
        this.name = name;
    }
    public LinkedList<GoalCard> getGoals() {
        return goals;
    }

    public String getName() {
        return name;
    }
}
