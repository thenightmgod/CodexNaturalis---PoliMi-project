package it.polimi.ingsw.Network;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.View.GameView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommonClient {

    void joinGame(String Name);

    void createGame(String Name, int numPlayers);

    void leaveGame(String name, CommonClient client);

    void placeCard(CommonClient client, int whichInHand, int x, int y, FB face);

    void setStartCardFace(boolean face, CommonClient client); //il player setta la variabile face della sua start card

    void chooseGoalCard(int i, CommonClient client);

    void drawCard(int i, int whichOne, CommonClient client);

    void setView(GameView view);
}
