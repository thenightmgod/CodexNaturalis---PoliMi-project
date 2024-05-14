package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.LinkedList;

public class ClientModel {

    //potrebbe esserci anche la chat ma vedremo
    private String Name;
    private LinkedList<PlayableCard> Hand;
    private GoalCard PrivateGoal;
    private PlayingField Field;
    private int PointsCounter;
    private LinkedList<GoalCard> CommonGoals;

    //vedere bene come gestirle
    private LinkedList<GoldCard> DrawableGoldCards;
    private LinkedList<ResourceCard> DrawableResourceCards;

    public ClientModel(String name){
        Name = name;
        Hand = new LinkedList<>();
        PrivateGoal = null;
        Field = new PlayingField();
        PointsCounter = 0;
        CommonGoals = new LinkedList<>();
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public LinkedList<PlayableCard> getHand(){
        return Hand;
    }

    public void setHand(LinkedList<PlayableCard> Hand){
        this.Hand = Hand;
    }

    public GoalCard getPrivateGoal(){
        return PrivateGoal;
    }

    public void setPrivateGoal(GoalCard card){
        PrivateGoal = card;
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
