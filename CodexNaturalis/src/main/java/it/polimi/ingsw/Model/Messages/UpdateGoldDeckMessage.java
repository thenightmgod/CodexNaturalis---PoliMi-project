package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;

import java.util.LinkedList;

public class UpdateGoldDeckMessage extends Message{
    private String name;
    LinkedList<GoldCard> deck;
    public UpdateGoldDeckMessage(String n, LinkedList<GoldCard> d){
        super("UpdateGoldDeckMessage");
        this.name = n;
        this.deck = d;
    }

    public String getName() {
        return name;
    }

    public LinkedList<GoldCard> getDeck() {
        return deck;
    }
}
