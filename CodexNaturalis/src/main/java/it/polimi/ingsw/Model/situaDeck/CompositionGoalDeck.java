package it.polimi.ingsw.Model.situaDeck;

import it.polimi.ingsw.Model.situaCard.situaGoalCard.CompositionGoalCard;

public class CompositionGoalDeck extends Deck{

    public CompositionGoalDeck(){
        super("CompositionGoal");
        //costruiamo 3 deck diversi e li inizializziamo assieme in un Goal Deck?
    }
    public void add(CompositionGoalCard c){
        this.getCards().add(c);
    }
}
