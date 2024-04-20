package it.polimi.ingsw.Model.situaDeck;

import it.polimi.ingsw.Model.situaCard.Card;

import java.util.Collections;
import java.util.LinkedList;

public abstract class Deck {
    LinkedList<Card> cards;
    //todo
    //public Deck(String type){     va passato il tipo della carta attraverso il gson, e si costruisce carta per carta
    // this.cards = cards;
    //}

    public LinkedList<Card> getCards() {
        return cards;
    }

    public int getSize(){
        return cards.size();
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
}
