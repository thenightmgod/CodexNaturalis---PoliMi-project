package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.LinkedList;

public interface GameView {
    

    boolean connectionType = false;
    public void updatePoints(int points, String name);

    public void showGoals(LinkedList<GoalCard> goals, String name);

    public void showHands(LinkedList<PlayableCard> hand, String name);

    public void updateField(PlayingField field, String name);

    public void showFreePosition(String name, LinkedList<Position> freePositions);

    public void showException(String name, String exception);

    //public void joinGame
}
