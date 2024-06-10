package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.ClientModel;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface CommonClient {


    void joinGame(String Name) throws RemoteException;

    void createGame(String Name, int numPlayers) throws RemoteException;

    void leaveGame(String name) throws RemoteException;

    void placeCard(int whichInHand, int x, int y, FB face) throws RemoteException;

    void setStartCardFace(boolean face) throws RemoteException; //il player setta la variabile face della sua start card

    void chooseGoalCard(int i, String name) throws RemoteException;

    void drawCard(int i, int whichOne, String name) throws RemoteException;

    void setView(GameView view);

    String getName();

    ClientModel getClient();

    void endTurn(String name, String mex) throws RemoteException;
}
