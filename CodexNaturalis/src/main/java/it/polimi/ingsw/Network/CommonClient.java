package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.rmi.RemoteException;

public interface CommonClient {

    void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException, RemoteException, NameAlreadyTakenException;

    void createGame(String Name, PlayerColor color, int numPlayers) throws WrongPlayersNumberException, RemoteException;

    void leaveGame(String name, VirtualView client);

    void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws WrongIndexException;

    void setStartCardFace(boolean face, VirtualView client); //il player setta la variabile face della sua start card
    void chooseGoalCard(int i, VirtualView client) throws WrongIndexException;
}
