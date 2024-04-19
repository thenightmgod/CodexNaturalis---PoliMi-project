package it.polimi.ingsw.Model.situaPlayer;

import java.util.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.situaCard.*;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.*;
import it.polimi.ingsw.Model.situaCorner.*;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaCorner.Objects;
import it.polimi.ingsw.Model.Deck;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import static it.polimi.ingsw.Model.situaCorner.CornerState.EMPTY;

import static it.polimi.ingsw.Model.situaCorner.CardRes.*;

public class Player {
    private final String Name;
    private final PlayerColor Color;
    private int PointsCounter;
    private int[] ResourceCounter;
    private int[] ObjectCounter;
    private PlayableCard[] Hand;
    private GoalCard PlayerGoal;
    private PlayingField PlayingField; //todo il come inizializzarlo

    public Player(String name, PlayerColor color) {
        Name = name;
        Color = color;
        PointsCounter = 0;
        ResourceCounter = new int[4];
        for(int i=0; i<4; i++){
            ResourceCounter[i]=0;
        }
        ObjectCounter = new int[3];
        for(int i=0; i<3; i++){
            ObjectCounter[i]=0;
        }
        PlayingField = new PlayingField();
    }

    public String getName() {
        return Name;
    }

    public PlayerColor getColor() {
        return Color;
    }

    public int getPointsCounter() {
        return PointsCounter;
    }

    public int getResourceCounter(Resources R) {
        if (R == Resources.PLANT_KINGDOM)
            return ResourceCounter[0];
        else if (R == Resources.ANIMAL_KINGDOM)
            return ResourceCounter[1];
        else if (R == Resources.FUNGI_KINGDOM)
            return ResourceCounter[2];
        else
            return ResourceCounter[3];
    }

    public int getObjectCounter(Objects O) {
        if (O == Objects.QUILL)
            return ObjectCounter[0];
        else if (O == Objects.INKWELL)
            return ObjectCounter[1];
        else
            return ObjectCounter[2];
    }

    public ResourceCard draw(Deck d) {
        //todo dopo il deck
        return null;
    }

    public void placeCard(PlayableCard c, Position p) {
        this.PlayingField.getField().put(p, c);
        if(p.getFace().equals(FB.BACK)){
            LinkedList<Resources> Res = c.getBackRes();
            for(Resources elemento : Res){
                switch(elemento){
                    case PLANT_KINGDOM -> this.ResourceCounter[0]++;
                    case ANIMAL_KINGDOM -> this.ResourceCounter[1]++;
                    case FUNGI_KINGDOM -> this.ResourceCounter[2]++;
                    case INSECT_KINGDOM -> this.ResourceCounter[3]++;
                }
            }
            for(Orientation Orien : Orientation.values()){
                c.getCorner(Orien).setRes(EMPTY);
            }
        }
        else{
            for(Orientation Orien : Orientation.values()){
                switch (c.getCorner(Orien).getRes()){
                    case PLANT_KINGDOM
                }
            }
        }
        //coverare angoli e togliere roba dai resources cosi
        this.PlayingField.updateFreePositions(p);
        return;
    }

    public GoalCard pickGoalCard(GoalCard A, GoalCard B) {
        //TODO

    }

}
