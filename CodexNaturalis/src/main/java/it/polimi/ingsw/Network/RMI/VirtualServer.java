package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {


    //le funzioni del server che vuole chiamare il client

    void joinGame(String Name, VirtualView client);
    void createGame(String Name, int numPlayers, VirtualView client);

    void leaveGame(String name, VirtualView client);

    void placeCard(VirtualView client, int whichInHand, int x, int y, FB face);

    void setStartCardFace(boolean face, VirtualView client); //il player setta la variabile face della sua start card
    void chooseGoalCard(int i, VirtualView client);

    //aggiungere metodo pesca carta e vedere lì come gestirlo
    void drawCard(int i, int whichOne, VirtualView client);

}
