package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.LinkedList;

public class ClientModel {

    //potrebbe esserci anche la chat ma vedremo
    private String Name;
    private LinkedList<PlayableCard> Hand;
    private PlayingField Field;
    private int PointsCounter;
    private LinkedList<GoalCard> CommonGoals;
    private LinkedList<Position> FreePosition;

    //vedere bene come gestirle
    private LinkedList<GoldCard> DrawableGoldCards;
    private LinkedList<ResourceCard> DrawableResourceCards;

    public ClientModel(String name){
        Name = name;
        Hand = new LinkedList<>();
        Field = new PlayingField();
        PointsCounter = 0;
        CommonGoals = new LinkedList<>();
        FreePosition = new LinkedList<>();
    }


    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public void setFreePositions(LinkedList<Position> FreePosition){
        this.FreePosition = FreePosition;
    }

    public LinkedList<Position> getFreePositions(){
        return FreePosition;
    }

    public LinkedList<PlayableCard> getHand(){
        return Hand;
    }

    public void setHand(LinkedList<PlayableCard> Hand){
        this.Hand = Hand;
    }

    public PlayingField getField(){
        return Field;
    }

    public void setField(PlayingField Field){
        this.Field = Field;
    }

    public int getPointsCounter(){
        return PointsCounter;
    }

    public void setPointsCounter(int Points){
        PointsCounter = Points;
    }

    public LinkedList<GoalCard> getCommonGoals(){
        return CommonGoals;
    }

    public void setCommonGoals(LinkedList<GoalCard> commonGoals){
        CommonGoals = commonGoals;
    }

}
