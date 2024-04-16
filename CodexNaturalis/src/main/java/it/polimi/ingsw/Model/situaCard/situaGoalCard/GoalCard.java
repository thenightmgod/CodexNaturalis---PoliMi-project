package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaCard.Card;

public abstract class GoalCard extends Card {
    private final int points;
    public GoalCard(int p) {
        this.points=p;
    }
    public int GetPoints() {
        return this.points;
    }

}
