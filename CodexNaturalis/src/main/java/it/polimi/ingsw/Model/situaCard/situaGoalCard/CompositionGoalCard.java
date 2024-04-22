package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import  it.polimi.ingsw.Model.situaPlayer.Player;
public class CompositionGoalCard extends GoalCard {

    private final Composition comp;
    public CompositionGoalCard(int id, int points, Composition c) {
        super(id,points);
        this.comp=c;
    }
    public Composition getComp() {
        return comp;
    }

    //non posso realizzarla fino a quando non so come funziona precisamente player ed il suo campo da gioco
    public int pointsCalc(Player p){
        return 0; //todo
    }
}
