package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
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

    void updateTurn(Player p, String mex) throws RemoteException;

    void notYourTurn(Player turn) throws RemoteException;

    void showException(String exception, String details) throws RemoteException, NotBoundException;

    void updatePoints(int points, String name) throws RemoteException;
    // int points, String name

    void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;  //mostro due carte goal da scegliere
    // LinkedList<GoalCard> goals
    void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException; //setto i common goals (non li faccio vedere)

    void showHand(LinkedList<PlayableCard> hand, String name) throws RemoteException;
    // LinkedList<PlayableCard> hand

    void updateField(String name, PlayingField field) throws RemoteException;
    // String name, PlayingField field

    void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException;

    void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException;

    void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException;

    void declareWinner(LinkedList<String> standings) throws RemoteException;

    String getName() throws RemoteException;

    void showStartCard(StartCard card) throws RemoteException;

    void startingGame(Player p) throws RemoteException;

    void twenty(String name) throws RemoteException;

    void lastRound() throws RemoteException;

    void isAlive() throws RemoteException;

    void leaveGame() throws RemoteException;

    void leaveGameMessage() throws RemoteException;

    void update() throws RemoteException; //update il clientModel

    void showOtherField(String player) throws RemoteException;
}
