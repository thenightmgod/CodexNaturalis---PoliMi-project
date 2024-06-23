package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;

public class ShowStartCardMessage extends Message{
    public StartCard start;
    public ShowStartCardMessage(StartCard s){
        super("ShowStartCardMessage");
        this.start = s;
    }

    public StartCard getStart() {
        return start;
    }
}
