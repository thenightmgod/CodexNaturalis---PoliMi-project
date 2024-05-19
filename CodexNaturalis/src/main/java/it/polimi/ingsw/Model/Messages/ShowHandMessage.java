package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;

import java.util.LinkedList;

public class ShowHandMessage extends Message{
    private LinkedList<PlayableCard> hand;

    public ShowHandMessage(LinkedList<PlayableCard> cards) {
        super("ShowHandMessage");
        this.hand=cards;
    }

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public LinkedList<PlayableCard> getHand() {
        return hand;
    }

}
