package it.polimi.ingsw.View;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public interface GameView {

     void updatePoints(int points, String name);

     void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;

    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;

    public void updateHands(LinkedList<PlayableCard> hand, String name);

    public void updateField(PlayingField field, String name);

    public void updateFreePosition(String name, LinkedList<Position> freePositions);

    public void showException(String name, String exception) throws RemoteException, NotBoundException;

    public void showStartCard(StartCard card);

    public void updateTurn(Player player) throws RemoteException;

    public void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name);

    public void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name);

    void startingGame() throws RemoteException;

    void declareWinner(HashMap<String, Integer> classifica);


    //public void joinGame
}
