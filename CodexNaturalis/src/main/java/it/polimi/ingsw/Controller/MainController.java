package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.util.LinkedList;

//questo controlla game multipli
public class MainController {
    LinkedList<GameController> controllers;

    public MainController(){
        this.controllers = new LinkedList<>();
    }

    public void createGame(String Name, PlayerColor color){

    }

    public void joinGame(String Name, PlayerColor color) throws RoomFullException {
        try {
            if(this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers())
                throw new RoomFullException("The room is already full");
            else this.controllers.getLast().addPlayer(Name, color);
        } catch (RoomFullException e) {
            System.out.println(e);
        }
    }


}
