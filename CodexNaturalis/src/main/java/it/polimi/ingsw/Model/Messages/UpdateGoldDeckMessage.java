package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;

import java.util.LinkedList;

public class UpdateGoldDeckMessage extends Message{
    public String name;
    public LinkedList<GoldCard> deck;
    public boolean start;
    public UpdateGoldDeckMessage(String n, boolean start, LinkedList<GoldCard> d){
        super("UpdateGoldDeckMessage");
        this.name = n;
        this.deck = d;
        this.start = start;
    }

    public String getName() {
        return name;
    }
    public LinkedList<GoldCard> getDeck() {
        return deck;
    }

    public boolean isStart() {
        return start;
    }
}
