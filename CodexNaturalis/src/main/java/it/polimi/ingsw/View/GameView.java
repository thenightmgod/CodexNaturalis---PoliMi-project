package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public interface GameView {

     void updatePoints(HashMap<String, Integer> points, String name);

     void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;

    void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;

    void updateHands(LinkedList<PlayableCard> hand, String name);

    void updateField(PlayingField field, String name);

    void updateFreePosition(String name, LinkedList<Position> freePositions);

    void showException(String name, String exception) throws RemoteException, NotBoundException;

    void showStartCard(StartCard card) throws RemoteException;
    void setStartCardFace() throws RemoteException;
    void createGame() throws RemoteException, NotBoundException;
    void joinGame() throws RemoteException;
    CommonClient chooseClient(String name);
    void getNickname();
    void isYourTurn() throws RemoteException;
    void updateTurn(Player player, String mex) throws RemoteException;

    void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name);

    void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name);

    void startingGame() throws RemoteException;

    void declareWinner(LinkedList<String> standings);

    void twenty(String name);

    void lastRound();

    void printNotYourTurn(Player turn, String mex);

    void leaveGameMessage();

    //public void joinGame
}
