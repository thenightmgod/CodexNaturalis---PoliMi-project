package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.Messages.Message;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.View.GameView;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public interface VirtualView extends Remote {

    //le funzioni del client che vuole chiamare il server

    void updateTurn(Player p) throws RemoteException;

    void showException(String details) throws RemoteException;

    void updatePoints(int points, String name) throws RemoteException;
    // int points, String name

    void updateGoals(LinkedList<GoalCard> goals) throws RemoteException;
    // LinkedList<GoalCard> goals

    void showHand(LinkedList<PlayableCard> hand) throws RemoteException;
    // LinkedList<PlayableCard> hand

    void updateField(String name, PlayingField field) throws RemoteException;
    // String name, PlayingField field

    void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException;

    void updateResourceDeck(String name, LinkedList<ResourceCard> deck) throws RemoteException;

    void updateGoldDeck(String name, LinkedList<GoldCard> deck) throws RemoteException;

    void declareWinner(HashMap<String, Integer> classifica) throws RemoteException;

    String getName() throws RemoteException;

    void showStartCard(StartCard card) throws RemoteException;

    void update() throws RemoteException; //update il clientModel

    void showOtherField(String player) throws RemoteException;
}
