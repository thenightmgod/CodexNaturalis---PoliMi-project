package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
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

    void updatePoints(int points, String name) throws RemoteException;

    void showGoals(LinkedList<GoalCard> goals) throws RemoteException;

    void showHand(LinkedList<PlayableCard> hand) throws RemoteException;

    void updateField(String name, PlayingField field) throws RemoteException;

    void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException;

    void createGame(String name, int NumPlayers) throws WrongPlayersNumberException, RemoteException, NotBoundException;

    void setView(GameView view);

    void update() throws RemoteException; //update il clientModel

    void showOtherField(String player) throws RemoteException;
}
