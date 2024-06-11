package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;

import java.util.LinkedList;

public class UpdateResourceDeckMessage extends Message{
    private String name;
    private LinkedList<ResourceCard> deck;
    private boolean start;
    public UpdateResourceDeckMessage(String n, boolean start, LinkedList<ResourceCard> d){
        super("UpdateResourceDeckMessage");
        this.name = n;
        this.deck = d;
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public LinkedList<ResourceCard> getDeck() {
        return deck;
    }

    public boolean isStart() {
        return start;
    }
}
