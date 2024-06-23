package it.polimi.ingsw.Model.Messages;

import java.util.LinkedList;

public class DeclareWinnerMessage extends Message{
    public LinkedList<String> standings;
    public DeclareWinnerMessage(LinkedList<String> standings){
        super("DeclareWinnerMessage");
        this.standings = standings;
    }

    public LinkedList<String> getStandings() {
        return standings;
    }
}
