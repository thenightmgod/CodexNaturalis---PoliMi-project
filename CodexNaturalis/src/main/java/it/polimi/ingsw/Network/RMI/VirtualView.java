package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.Messages.Message;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.View.GameView;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface VirtualView extends Remote {



    //le funzioni del client che vuole chiamare il server

    void showException(String details) throws RemoteException;

    void updatePoints(String pointsMex) throws RemoteException;
    // int points, String name

    void showGoals(String goalMex) throws RemoteException;
    // LinkedList<GoalCard> goals

    void showHand(String handMex) throws RemoteException;
    // LinkedList<PlayableCard> hand

    void updateField(String fieldMex) throws RemoteException;
    // String name, PlayingField field

    void showFreePositions(String freePosMex) throws RemoteException;
    // String name, LinkedList<Position> freePosition

    void update() throws RemoteException; //update il clientModel

    void showOtherField(String player) throws RemoteException;
}
