package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaCard.Card;

public abstract class GoalCard extends Card {
    private final int points;
    public GoalCard(int id, int p) {
        super(id);
        this.points=p;
    }
    @Override
    public int getId() {
        return super.getId();
    }

    public int getPoints() {
        return this.points;
    }
}
