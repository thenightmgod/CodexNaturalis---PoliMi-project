package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import java.util.LinkedList;

public class ShowGoalsMessage extends Message{
    private LinkedList<GoalCard> goals;

    public ShowGoalsMessage(LinkedList<GoalCard> cards) {
        super("ShowGoalsMessage");
        this.goals=cards;
    }
    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }

    @Override
    public String getType() {
        return super.getType();
    }
    public LinkedList<GoalCard> getGoals() {
        return goals;
    }
}
