package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

public interface VirtualServerSocket {
    void joinGame(String stringMessage) throws RoomFullException, RoomNotExistsException;

    void createGame(String stringMessage) throws WrongPlayersNumberException;

    void leaveGame(String name, it.polimi.ingsw.Network.RMI.VirtualView client);

    void placeCard(VirtualView client, int whichInHand, int x, int y, FB face);

    void setStartCardFace(FB face); //il player setta la variabile face della sua start card

    // ci dev'essere anche la chooseStartCard()
}
