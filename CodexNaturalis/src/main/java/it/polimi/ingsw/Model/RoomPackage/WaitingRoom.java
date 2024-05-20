package it.polimi.ingsw.Model.RoomPackage;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.VirtualView;

import java.util.LinkedList;

public class WaitingRoom {

    private LinkedList<it.polimi.ingsw.Network.CommonClient> clients;

    private ObserverManager observers;

    public WaitingRoom(){
        clients = new LinkedList<>();
        observers = new ObserverManager();
    }

    public void addPlayer(CommonClient player){
        clients.add(player);
        observers.addObserver((VirtualView) player, ((VirtualView)player).getName());
    }

}
