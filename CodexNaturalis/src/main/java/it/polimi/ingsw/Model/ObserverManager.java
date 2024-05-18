package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ObserverManager {

    Map<String, VirtualView> observers;

    public ObserverManager(){
        this.observers = new HashMap<>();
    }

    public void addObserver(VirtualView client, String name){
        observers.put(name, client);
    }

    public void removeObserver(VirtualView client, String name){
        observers.remove(name);
    }

    public VirtualView getObserver(String name){
        return observers.get(name);
    }

    // tutti i metodi della virtual view

    public void updatePoints(int points, String name) throws RemoteException {
        for(String s : observers.keySet()) {
            observers.get(s).updatePoints(points, name);
        }
    }

    public void showException(String exception, String name) throws RemoteException {
        observers.get(name).showException(exception);
    }

    public void showGoals(String name, LinkedList<GoalCard> goals) throws RemoteException {
        observers.get(name).showGoals(goals);
    }

    public void showNewHand(String name, LinkedList<PlayableCard> hand) throws RemoteException{
        observers.get(name).showHand(hand);
        //anche per starter card, eventuali sbatti con l'id farli qua
    }

    public void updateField(String name, PlayingField field) throws RemoteException{
        for(String s : observers.keySet())
            observers.get(s).updateField(name, field);
    }

    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException{
        observers.get(name).showFreePositions(name, freePosition);
    }

    //eventualmente implementarli
    public void message(String name, String mex){};

    public void broadcastMessage(String name){};

}
