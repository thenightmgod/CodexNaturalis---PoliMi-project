package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.RoomPackage.ObserverManager;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

//questo controlla game multipli
public class MainController {

    LinkedList<GameController> controllers;

    HashMap<Integer, GameController> controllersPerGame;

    HashMap<Integer, LinkedList<VirtualView>> viewPerGame;

    public MainController(){
        this.controllers = new LinkedList<>();
        this.controllersPerGame = new HashMap<>();
        this.viewPerGame = new HashMap<>();
    }

    public LinkedList<GameController> getControllers(){
        return this.controllers;
    }

    public HashMap<Integer, GameController> getControllersPerGame(){
        return controllersPerGame;
    }

    //numPlayers arriva da
    public void createGame(String Name, int numPlayers, VirtualView client) throws RemoteException{
        //controllo sui numeri lo fa la tui
        if(controllers.isEmpty()) {
            GameController Garfield = new GameController(0, numPlayers);
            controllers.add(Garfield);
            controllersPerGame.put(0, Garfield);
            LinkedList<VirtualView> Grian = new LinkedList<>();
            Grian.add(client);
            viewPerGame.put(0, Grian);
        }
        else {
            GameController Garfield = new GameController(controllers.getLast().getRoomId() + 1, numPlayers);
            controllers.add(Garfield);
            controllersPerGame.put(controllers.getLast().getRoomId() + 1, Garfield);
            LinkedList<VirtualView> Grian = new LinkedList<>();
            Grian.add(client);
            viewPerGame.put(controllers.getLast().getRoomId() + 1, Grian);
        }
        controllers.getLast().addPlayer(Name, PlayerColor.RED, client);
        //fare sbatti per aggiunta client
    }

    public void joinGame(String Name, VirtualView client) throws RemoteException {

        if (this.controllers.isEmpty()) {
            client.showException("RoomNotExistsException", "Nothing");
        } else {
            String boh = controllers.stream().map(GameController::getPlayers).flatMap(Collection::stream).map(Player::getName).filter(x -> x.equals(Name)).findFirst().orElse("");
            if (!boh.isEmpty()) {
                client.showException("NameAlreadyTakenException", "Nothing");
            } else {
                if (this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers()) {
                    client.showException("RoomFullException", "Nothing");
                } else {
                    switch (this.controllers.getLast().getNumPlayers()) {
                        case 1 -> {
                            this.controllers.getLast().addPlayer(Name, PlayerColor.YELLOW, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(this.controllers.getLast().getRoomId());
                            Grian.add(client);
                            this.viewPerGame.put(this.controllers.getLast().getRoomId(), Grian);
                        }
                        case 2 -> {
                            this.controllers.getLast().addPlayer(Name, PlayerColor.BLUE, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(this.controllers.getLast().getRoomId());
                            Grian.add(client);
                            this.viewPerGame.put(this.controllers.getLast().getRoomId(), Grian);
                        }
                        case 3 -> {
                            this.controllers.getLast().addPlayer(Name, PlayerColor.GREEN, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(this.controllers.getLast().getRoomId());
                            Grian.add(client);
                            this.viewPerGame.put(this.controllers.getLast().getRoomId(), Grian);
                        }
                    }
                }
            }
        }
    }

    public GameController leaveGame(String Name, int RoomId) throws RemoteException{
        controllers.get(RoomId).removePlayer(Name);
        return controllers.get(RoomId);
    }


    //come togliere sbatti del try catch
    public LinkedList<VirtualView> getOtherPlayers(String name) throws RemoteException {
        int k = -1;
        LinkedList<VirtualView> otherPlayers = new LinkedList<>();
        for(Map.Entry<Integer, LinkedList<VirtualView>> entry: viewPerGame.entrySet()){
            for(VirtualView v: entry.getValue()){
                if(v.getName().equals(name)){
                    k = entry.getKey();
                }
            }
        }
        if(k != -1){
            for(int i=0; i<viewPerGame.get(k).size(); i++) {
                VirtualView view = viewPerGame.get(k).get(i);
                if(!name.equals(view.getName()))
                    otherPlayers.add(view);
            }
        }
        return otherPlayers;
    }

}