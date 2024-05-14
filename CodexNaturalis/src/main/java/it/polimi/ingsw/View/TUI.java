package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.LinkedList;

public class TUI implements GameView{



    @Override
    public void updatePoints(int points, String name) {

    }

    @Override
    public void showGoals(LinkedList<GoalCard> goals, String name) {

    }

    @Override
    public void showHands(LinkedList<PlayableCard> hand, String name) {

    }

    @Override
    public void updateField(PlayingField field, String name) {

    }

    @Override
    public void showFreePosition(String name, LinkedList<Position> freePositions) {

    }
}
