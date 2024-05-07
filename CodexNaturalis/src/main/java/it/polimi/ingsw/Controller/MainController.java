package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.util.LinkedList;

//questo controlla game multipli
public class MainController {
    LinkedList<GameController> controllers;

    public MainController(){
        this.controllers = new LinkedList<>();
    }


    //numPlayers arriva da
    public void createGame(String Name, PlayerColor color, int numPlayers){
        controllers.add(new GameController(controllers.getLast().getRoomId() + 1, numPlayers));
        controllers.getLast().addPlayer(Name, color);
    }

    public void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException{
        //come gestire il fatto che debba essere chiamata la createGame se non ne esistono
        //RoomNotExistsException
        if(this.controllers.isEmpty()){
            throw new RoomNotExistsException("there isn't a game to join, create one");
        }
        else {
            if (this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers())
                throw new RoomFullException("The room is already full");
            else this.controllers.getLast().addPlayer(Name, color);
        }

    }


}
