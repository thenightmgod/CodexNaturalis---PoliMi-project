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

import java.rmi.RemoteException;
import java.util.LinkedList;

public interface GameView {

    public void updatePoints(int points, String name);

    public void updateGoals(LinkedList<GoalCard> goals, String name);

    public void updateHands(LinkedList<PlayableCard> hand, String name);

    public void updateField(PlayingField field, String name);

    public void updateFreePosition(String name, LinkedList<Position> freePositions);

    public void showException(String name, String exception);

    public void showStartCard(StartCard card);

    public void updateTurn(Player player, boolean LL) throws RemoteException;

    public void updateGoldDeck(LinkedList<GoldCard> deck, String name);

    public void updateResourceDeck(LinkedList<ResourceCard> deck, String name);

    //public void joinGame
}
