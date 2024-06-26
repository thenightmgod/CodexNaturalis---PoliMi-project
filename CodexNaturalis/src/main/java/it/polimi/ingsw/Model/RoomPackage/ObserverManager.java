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
/**
 * This class manages observers in the game.
 */
public class ObserverManager {
    /**
     * Map of observers with the client's names as keys.
     */
    Map<String, VirtualView> observers;
    /**
     * Map of observers with the client's names as keys.
     */
    public ObserverManager(){
        this.observers = new HashMap<>();
    }
    /**
     * Adds an observer to the map.
     * @param client The client to be added as an observer.
     * @param name The name of the client.
     */
    public void addObserver(VirtualView client, String name){
        observers.put(name, client);
    }
    /**
     * Retrieves an observer from the map.
     * @param name The name of the client.
     * @return The client associated with the name.
     */
    public VirtualView getObserver(String name){
        return observers.get(name);
    }

    // tutti i metodi della virtual view
    /**
     * Updates the points for all observers.
     * @param points The points to be updated.
     * @param name The name of the client.
     */
    public void updatePoints(HashMap<String, Integer> points, String name) {
        try {
            for (String s : observers.keySet()) {
                observers.get(s).updatePoints(points, name);
            }
        } catch (RemoteException ignored) {}
    }
    /**
     * Updates the turn for all observers.
     * @param turn The player who is currently taking their turn.
     * @param mex The message to be displayed.
     */
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
    /**
     * Declares the winner to the specified observer.
     * @param name The name of the client.
     * @param standings The final standings of the game.
     */
    public void declareWinner(String name, LinkedList<String> standings) {
        try {
            observers.get(name).declareWinner(standings);
        } catch (RemoteException ignored) {}
    }
    /**
     * Shows the start card to the specified observer.
     * @param name The name of the client.
     * @param card The start card to be shown.
     */
    public void showStartCard(String name, StartCard card) {
        try {
            observers.get(name).showStartCard(card);
        } catch (RemoteException ignored) {}
    }
    /**
     * Shows an exception to the specified observer.
     * @param exception The exception to be shown.
     * @param details The details of the exception.
     * @param name The name of the client.
     * @throws NotBoundException If the client is not bound.
     */
    public void showException(String exception, String details, String name) throws NotBoundException {
        try {
            observers.get(name).showException(exception, details);
        } catch (RemoteException ignored) {}
    }
    /**
     * Shows the goal cards to the specified observer.
     * @param name The name of the client.
     * @param goals The goals to be shown.
     */
    public void showGoals(String name, LinkedList<GoalCard> goals) {
        try {
            observers.get(name).updateGoals(goals);
        } catch (RemoteException ignored) {}
    }
    /**
     * Shows the common goal cards to the specified observer.
     * @param name The name of the client.
     * @param goals The common goals to be shown.
     */
    public void showCommonGoals(String name, LinkedList<GoalCard> goals) {
        try {
            observers.get(name).updateCommonGoals(goals);
        } catch (RemoteException ignored) {}
    }
    /**
     * Shows a new hand to the specified observer.
     * @param name The name of the client.
     * @param hand The new hand to be shown.
     */
    public void showNewHand(String name, LinkedList<PlayableCard> hand) {
        try {
            observers.get(name).showHand(hand);
            System.out.println(hand.size());
        } catch (RemoteException ignored) {}
    }
    /**
     * Updates the playing field for all observers.
     * @param name The name of the client.
     * @param field The new playing field for this client.
     */
    public void updateField(String name, PlayingField field) {
        try {
            for (String s : observers.keySet())
                observers.get(s).updateField(name, field);
        } catch (RemoteException ignored) {}
    }
    /**
     * Shows the free positions to the specified observer.
     * @param name The name of the client.
     * @param freePosition The free positions to show.
     */
    public void showFreePositions(String name, LinkedList<Position> freePosition) {
        try {
            observers.get(name).showFreePositions(name, freePosition);
        } catch (RemoteException ignored) {}
    }
    /**
     * Updates the gold deck for the specified observer.
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The new gold deck for this client.
     */
    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) {
        try {
            observers.get(name).updateGoldDeck(name, start, deck);
        } catch (RemoteException ignored) {}
    }
    /**
     * Updates the resource deck for the specified observer.
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The new resource deck for this client.
     */
    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) {
        try {
            observers.get(name).updateResourceDeck(name, start, deck);
        } catch (RemoteException ignored) {}
    }
    /**
     * Notifies all observers that a player has reached twenty points.
     * @param name The name of the player who reached twenty points.
     */
    public void twenty(String name) {
        try {
            for (String s : observers.keySet())
                observers.get(s).twenty(name);
        } catch (RemoteException ignored) {}
    }
    /**
     * Notifies a specific observer that it is the last round of the game.
     * @param name The name of the player to be notified.
     */
    public void lastRound(String name) {
        try {
            observers.get(name).lastRound();
        } catch (RemoteException ignored) {}
    }
    /**
     * Sends the list of players to all observers.
     * @param players The list of players to be sent.
     */
    public void sendPlayers(LinkedList<String> players){
        try {
            for (String s : observers.keySet())
                observers.get(s).sendPlayers(players);
        } catch (RemoteException ignored) {}
    }
    /**
     * Updates the chat for a specific observer.
     * @param name The name of the observer to update the chat for.
     * @param chat The updated chat messages.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void updateChat(String name, LinkedList<ChatMessage> chat) throws RemoteException {
        observers.get(name).updateChat(name, chat);
    }


    //eventualmente implementarli
    public void message(String name, String mex){};


}
