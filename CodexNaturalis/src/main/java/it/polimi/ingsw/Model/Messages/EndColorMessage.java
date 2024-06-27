package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

public class EndColorMessage extends Message{

    public String name;
    public PlayerColor color;

    public EndColorMessage(String name, PlayerColor color){
        super("EndColorMessage");
        this.name = name;
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public PlayerColor getColor(){
        return color;
    }
}
