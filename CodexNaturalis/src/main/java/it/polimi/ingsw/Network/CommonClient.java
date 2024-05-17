package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.RMI.VirtualView;
import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.TUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommonClient {

    void joinGame(String Name) throws RoomFullException, RoomNotExistsException, RemoteException, NameAlreadyTakenException, NotBoundException;

    void createGame(String Name, int numPlayers) throws WrongPlayersNumberException, RemoteException, NotBoundException;

    void leaveGame(String name, CommonClient client);

    void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) throws WrongIndexException;

    void setStartCardFace(boolean face, CommonClient client); //il player setta la variabile face della sua start card
    void chooseGoalCard(int i, CommonClient client) throws WrongIndexException;

    void setView(GameView view);
}
