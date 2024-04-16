package it.polimi.ingsw.Model.situaCard.situaGoalCard;

public class CompositionGoalCard extends GoalCard {

    private final Composition comp;
    public CompositionGoalCard(Comp c) {

        this.comp=c;
    }
    public Composition getComp() {
        return comp;
    }

    //non posso realizzarla fino a quando non so come funziona precisamente player ed il suo campo da gioco
    public int PointsCalc(Player p);
}
