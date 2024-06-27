package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.util.LinkedList;

public class UpdateColorsMessage extends Message{

    public String name;
    public Player turn;
    public LinkedList<PlayerColor> colors;

    public UpdateColorsMessage(String name, Player turn, LinkedList<PlayerColor> colors){
        super("UpdateColorsMessage");
        this.name = name;
        this.turn = turn;
        this.colors = colors;
    }

    public String getName(){
        return name;
    }

    public Player getTurn(){
        return turn;
    }

    public LinkedList<PlayerColor> getColors(){
        return colors;
    }
}
