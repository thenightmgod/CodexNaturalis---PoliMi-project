package it.polimi.ingsw.Network.Socket;


import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

public interface VirtualServerSocket {
    void joinGame(String stringMessage) ;

    void createGame(String stringMessage) ;

    void leaveGame(String stringMessage);
    void placeCard(String stringMessage);

    void setStartCardFace(String stringMessage); //il player setta la variabile face della sua start card
    void chooseGoalcard(String stringMessage);
    void drawCard(String stringMessage);
    void setView(String stringMessage);
    void sendMessage(String message);
    void checkGoals(String name);

    // ci dev'essere anche la chooseStartCard()
}
