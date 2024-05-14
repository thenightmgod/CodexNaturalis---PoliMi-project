package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface VirtualView extends Remote {

    //le funzioni del client che vuole chiamare il server

    void showException(String details) throws RemoteException;

    void updatePoints(int points, String name) throws RemoteException;

    void showGoals(LinkedList<GoalCard> goals) throws RemoteException;

    void showHand(LinkedList<PlayableCard> hand) throws RemoteException;

    void updateField(String name, PlayingField field) throws RemoteException;

    void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException;

    void update() throws RemoteException; //update il clientModel

    void showOtherField(String player) throws RemoteException;
}
