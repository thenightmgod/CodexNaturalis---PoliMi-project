package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;

import java.util.LinkedList;

public class UpdateResourceDeckMessage extends Message{
    private String name;
    LinkedList<ResourceCard> deck;
    public UpdateResourceDeckMessage(String n, LinkedList<ResourceCard> d){
        super("UpdateResourceDeckMessage");
        this.name = n;
        this.deck = d;
    }

    public String getName() {
        return name;
    }

    public LinkedList<ResourceCard> getDeck() {
        return deck;
    }
}
