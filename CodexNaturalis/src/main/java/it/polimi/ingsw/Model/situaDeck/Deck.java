package it.polimi.ingsw.Model.situaDeck;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;
import it.polimi.ingsw.Model.situaCorner.Resources;

import java.util.List;
import java.util.Collections;


public class Deck {
    private List<ResourceCard> cards;

    public Deck(List<ResourceCard> cards) {
        this.cards = cards;
    }

    public List<ResourceCard> getCards() {
        return cards;
    }

    public int getSize(){
        return cards.size();
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }

}

