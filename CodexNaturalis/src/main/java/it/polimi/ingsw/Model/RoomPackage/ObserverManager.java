package it.polimi.ingsw.Model.RoomPackage;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.VirtualView;

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

    public void updateTurn(Player turn, boolean LL) throws RemoteException {
        for(String s : observers.keySet()) {
            observers.get(s).updateTurn(turn, LL);
        }
    }

    public void showStartCard(String name, StartCard card) throws RemoteException{
        observers.get(name).showStartCard(card);
    }

    public void showException(String exception, String name) throws RemoteException {
        observers.get(name).showException(exception);
    }

    public void showGoals(String name, LinkedList<GoalCard> goals) throws RemoteException {
        observers.get(name).updateGoals(goals);
    }

    public void showNewHand(String name, LinkedList<PlayableCard> hand) throws RemoteException {
        observers.get(name).showHand(hand);
        //anche per starter card, eventuali sbatti con l'id farli qua
    }

    public void updateField(String name, PlayingField field) throws RemoteException {
        for(String s : observers.keySet())
            observers.get(s).updateField(name, field);
    }

    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {
        observers.get(name).showFreePositions(name, freePosition);
    }

    public void updateGoldDeck(String name, LinkedList<GoldCard> deck) throws RemoteException{
        observers.get(name).updateGoldDeck(name, deck);
    }

    public void updateResourceDeck(String name, LinkedList<ResourceCard> deck) throws RemoteException{
        observers.get(name).updateResourceDeck(name, deck);
    }

    //eventualmente implementarli
    public void message(String name, String mex){};

    public void broadcastMessage(String name){};

}
