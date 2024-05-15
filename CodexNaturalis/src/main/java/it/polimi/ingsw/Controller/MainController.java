package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.NameAlreadyTakenException;
import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.util.HashMap;
import java.util.LinkedList;

//questo controlla game multipli
public class MainController {
    LinkedList<GameController> controllers;

    public MainController(){
        this.controllers = new LinkedList<>();
    }

    public LinkedList<GameController> getControllers(){
        return this.controllers;
    }

    //numPlayers arriva da
    public GameController createGame(String Name, int numPlayers, VirtualView client) throws WrongPlayersNumberException {
        if(numPlayers<2 || numPlayers>4)
            throw new WrongPlayersNumberException("the number of players has to be between 2 and 4");
        else {
            if(controllers.isEmpty())
                controllers.add(new GameController(0, numPlayers));
            else
                controllers.add(new GameController(controllers.getLast().getRoomId() + 1, numPlayers));
            controllers.getLast().addPlayer(Name, PlayerColor.RED, client);
        }
        return controllers.getLast();
    }

    public GameController joinGame(String Name, VirtualView client) throws RoomFullException, RoomNotExistsException, NameAlreadyTakenException {
        //come gestire il fatto che debba essere chiamata la createGame se non ne esistono
        //RoomNotExistsException
        if(this.controllers.isEmpty()){
            throw new RoomNotExistsException("there isn't a game to join, create one");
        }
        else {
            for(Player p : this.controllers.getLast().getPlayers()){
                if(p.getName() == Name)
                    throw new NameAlreadyTakenException("the name is already taken");
            }
            if (this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers())
                throw new RoomFullException("The room is already full");
            else {
                switch(this.controllers.getLast().getNumPlayers()){
                    case 1 ->
                            this.controllers.getLast().addPlayer(Name, PlayerColor.YELLOW, client);
                    case 2 ->
                            this.controllers.getLast().addPlayer(Name, PlayerColor.BLUE, client);
                    case 3 ->
                            this.controllers.getLast().addPlayer(Name, PlayerColor.GREEN, client);
                }
            }
        }
        return controllers.getLast();
    }

    public GameController leaveGame(String Name, int RoomId){
        controllers.get(RoomId).removePlayer(Name);
        return controllers.get(RoomId);
    }

}
