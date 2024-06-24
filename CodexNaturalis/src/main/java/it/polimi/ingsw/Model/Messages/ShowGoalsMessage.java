package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;

public class ShowGoalsMessage extends Message{
    public LinkedList<Integer> goalIds;
    public String name;

    public ShowGoalsMessage(LinkedList<Integer> cards, String name) {

        super("ShowGoalsMessage");
        this.goalIds = cards;
        this.name = name;
    }
    public LinkedList<Integer> getGoals() {
        return goalIds;
    }

    public String getName() {
        return name;
    }
}
