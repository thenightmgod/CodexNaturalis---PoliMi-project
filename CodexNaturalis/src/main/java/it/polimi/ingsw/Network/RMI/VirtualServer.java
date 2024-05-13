package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {


    //le funzioni del server che vuole chiamare il client

    void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException, RemoteException;

    void createGame(String Name, PlayerColor color, int numPlayers) throws WrongPlayersNumberException, RemoteException;

    void leaveGame(String name, VirtualView client);

    void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws WrongIndexException;

    void setStartCardFace(boolean face, VirtualView client); //il player setta la variabile face della sua start card
    void chooseGoalCard(int i, VirtualView client) throws WrongIndexException;

    //aggiungere metodo pesca carta e vedere lì come gestirlo
    void drawCard(int i, int whichone, VirtualView client) throws WrongIndexException;

}
