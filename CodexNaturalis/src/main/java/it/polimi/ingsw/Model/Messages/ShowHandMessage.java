package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;

import java.util.LinkedList;

public class ShowHandMessage extends Message{
    private LinkedList<PlayableCard> hand;
    private String name;

    public ShowHandMessage(LinkedList<PlayableCard> cards, String name) {
        super("ShowHandMessage");
        this.hand=cards;
        this.name = name;
    }
    public LinkedList<PlayableCard> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }
}
