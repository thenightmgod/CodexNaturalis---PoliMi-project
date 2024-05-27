package it.polimi.ingsw.Model.Messages;

import java.util.HashMap;

public class DeclareWinnerMessage extends Message{
    private HashMap<String,Integer> classifica;
    public DeclareWinnerMessage(HashMap<String, Integer> c ){
        super("DeclareWinnerMessage");
        this.classifica = c;
    }

    public HashMap<String, Integer> getClassifica() {
        return classifica;
    }
}
