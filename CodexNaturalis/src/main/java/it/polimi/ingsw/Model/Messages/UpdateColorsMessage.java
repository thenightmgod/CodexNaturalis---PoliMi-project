package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.util.LinkedList;

public class UpdateColorsMessage extends Message{

    String name;
    Player turn;
    LinkedList<PlayerColor> colrs;

    public UpdateColorsMessage(String name, Player turn, LinkedList<PlayerColor> colrs){
        super("UpdateColorsMessage");
        this.name = name;
        this.turn = turn;
        this.colrs = colrs;
    }

    public String getName(){
        return name;
    }

    public Player getTurn(){
        return turn;
    }

    public LinkedList<PlayerColor> getColors(){
        return colrs;
    }
}
