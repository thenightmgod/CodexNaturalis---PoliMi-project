package it.polimi.ingsw.Model.RoomPackage;


import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
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

    public void updatePoints(HashMap<String, Integer> points, String name) {
        try {
            for (String s : observers.keySet()) {
                observers.get(s).updatePoints(points, name);
            }
        } catch (RemoteException ignored) {}
    }

    public void updateTurn(Player turn, String mex) {
        try {
            for (String s : observers.keySet()) {
                if (!observers.get(s).getNames().equals(turn.getName())) { //printo is not your turn a tutti quelli che non sono in turno
                    observers.get(s).notYourTurn(turn, mex);
                }
            }
            for (String s : observers.keySet()) {
                if (observers.get(s).getNames().equals(turn.getName())) {  //inizio a chiamare azioni sul player che ha il turno
                    observers.get(s).updateTurn(turn, mex);
                }
            }
        } catch (RemoteException ignored) {}
    }

    public void declareWinner(String name, LinkedList<String> standings) {
        try {
            observers.get(name).declareWinner(standings);
        } catch (RemoteException ignored) {}
    }

    public void showStartCard(String name, StartCard card) {
        try {
            observers.get(name).showStartCard(card);
        } catch (RemoteException ignored) {}
    }

    public void showException(String exception, String details, String name) throws NotBoundException {
        try {
            observers.get(name).showException(exception, details);
        } catch (RemoteException ignored) {}
    }

    public void showGoals(String name, LinkedList<GoalCard> goals) {
        try {
            observers.get(name).updateGoals(goals);
        } catch (RemoteException ignored) {}
    }

    public void showCommonGoals(String name, LinkedList<GoalCard> goals) {
        try {
            observers.get(name).updateCommonGoals(goals);
        } catch (RemoteException ignored) {}
    }
    public void showNewHand(String name, LinkedList<PlayableCard> hand) {
        try {
            observers.get(name).showHand(hand);
            System.out.println(hand.size());
        } catch (RemoteException ignored) {}
    }

    public void updateField(String name, PlayingField field) {
        try {
            for (String s : observers.keySet())
                observers.get(s).updateField(name, field);
        } catch (RemoteException ignored) {}
    }

    public void showFreePositions(String name, LinkedList<Position> freePosition) {
        try {
            observers.get(name).showFreePositions(name, freePosition);
        } catch (RemoteException ignored) {}
    }

    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) {
        try {
            observers.get(name).updateGoldDeck(name, start, deck);
        } catch (RemoteException ignored) {}
    }

    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) {
        try {
            observers.get(name).updateResourceDeck(name, start, deck);
        } catch (RemoteException ignored) {}
    }

    public void twenty(String name) {
        try {
            for (String s : observers.keySet())
                observers.get(s).twenty(name);
        } catch (RemoteException ignored) {}
    }

    public void lastRound(String name) {
        try {
            observers.get(name).lastRound();
        } catch (RemoteException ignored) {}
    }

    public void sendPlayers(LinkedList<String> players){
        try {
            for (String s : observers.keySet())
                observers.get(s).sendPlayers(players);
        } catch (RemoteException ignored) {}
    }

    public void updateChat(String name, LinkedList<ChatMessage> chat) throws RemoteException {
        observers.get(name).updateChat(name, chat);
    }


    //eventualmente implementarli
    public void message(String name, String mex){};

    public void broadcastMessage(String name){};

}
