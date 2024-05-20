package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
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

    void showException(String details) throws RemoteException, WrongIndexException, WrongPositionException, WrongPlayersNumberException, RoomNotExistsException, RoomFullException, RequirementsNotSatisfied, NameAlreadyTakenException, InvalidOperationException;

    void updatePoints(int points, String name) throws RemoteException;
    // int points, String name

    void showGoals(LinkedList<GoalCard> goals) throws RemoteException;
    // LinkedList<GoalCard> goals

    void showHand(LinkedList<PlayableCard> hand) throws RemoteException;
    // LinkedList<PlayableCard> hand

    void updateField(String name, PlayingField field) throws RemoteException;
    // String name, PlayingField field

    void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException;

    String getName();
    // String name, LinkedList<Position> freePosition

    void update() throws RemoteException; //update il clientModel

    void showOtherField(String player) throws RemoteException;
}
