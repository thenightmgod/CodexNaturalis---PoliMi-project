package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

//questo controlla game multipli
public class MainController {
    LinkedList<GameController> controllers;

    HashMap<Integer, GameController> controllersPerGame;

    public MainController(){
        this.controllers = new LinkedList<>();
        this.controllersPerGame = new HashMap<>();
    }

    public synchronized LinkedList<GameController> getControllers(){
        return this.controllers;
    }

    public synchronized HashMap<Integer, GameController> getControllersPerGame(){
        return controllersPerGame;
    }

    //numPlayers arriva da
    public synchronized GameController createGame(String Name, int numPlayers, VirtualView client) throws WrongPlayersNumberException {
        //controllo sui numeri lo fa la tui
        if(controllers.isEmpty()) {
            GameController Garfield = new GameController(0, numPlayers);
            controllers.add(Garfield);
            controllersPerGame.put(0, Garfield);
        }
        else {
            GameController Garfield = new GameController(controllers.getLast().getRoomId() + 1, numPlayers);
            controllers.add(Garfield);
            controllersPerGame.put(controllers.getLast().getRoomId() + 1, Garfield);
        }
        controllers.getLast().addPlayer(Name, PlayerColor.RED, client);

        return controllers.getLast();
    }

    public synchronized GameController joinGame(String Name, VirtualView client) throws RoomFullException, RoomNotExistsException, NameAlreadyTakenException, RequirementsNotSatisfied, InvalidOperationException, WrongIndexException, RemoteException, WrongPositionException, WrongPlayersNumberException {
        //come gestire il fatto che debba essere chiamata la createGame se non ne esistono
        //RoomNotExistsException
        int i;
        if(this.controllers.isEmpty()){
            throw new RoomNotExistsException("there isn't a game to join, create one");
        }
        else {
            for(Player p : this.controllers.getLast().getPlayers()){
                if(p.getName() == Name)
                    //non funzionerà mai
                    this.controllers.getLast().getGame().getObserverManager().showException("NameAlreadyTakenException", this.controllers.getLast().getPlayerByName(p.getName()).getName());
            }
            if (this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers())
                //non funziona perchè il player non è nella room, fare da tui
                i = 5;
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

    public synchronized GameController leaveGame(String Name, int RoomId){
        controllers.get(RoomId).removePlayer(Name);
        return controllers.get(RoomId);
    }


}