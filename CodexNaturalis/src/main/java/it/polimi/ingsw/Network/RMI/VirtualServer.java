package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    void connect(VirtualView client) throws RemoteException;

    //le funzioni del server che vuole chiamare il client

    void joinGame(String Name, PlayerColor color);

    void createGame(String Name, PlayerColor color, int numPlayers);

    void leaveGame(String name, VirtualView client);

    void placeCard(ResourceCard card, int x, int y, FB face);

    void setStartCardFace(FB face); //il player setta la variabile face della sua start card

    // ci dev'essere anche la chooseStartCard()



}
