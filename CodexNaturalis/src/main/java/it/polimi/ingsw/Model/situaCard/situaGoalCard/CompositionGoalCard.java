package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaCard.situaPlayableCard.CardColor;
import  it.polimi.ingsw.Model.situaPlayer.Player;
public class CompositionGoalCard extends GoalCard {

    private final Composition comp;
    private final CardColor color;
    public CompositionGoalCard(int id, int points, Composition c, CardColor color) {
        super(id,points);
        this.comp=c;
        this.color=color;
    }
    public Composition getComp() {
        return comp;
    }
    public CardColor getColor() {
        return color;
    }

    //non posso realizzarla fino a quando non so come funziona precisamente player ed il suo campo da gioco
    public int pointsCalc(Player p){
        return 0; //todo
    }
}
