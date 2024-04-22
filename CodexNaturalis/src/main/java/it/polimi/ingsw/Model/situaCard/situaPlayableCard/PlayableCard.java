package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCard.Card;
import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.LinkedList;
import java.util.SortedSet;

public abstract class PlayableCard extends Card{
    private final boolean[] BackRes;
    private LinkedList<Corner> Corners;

    public PlayableCard(int id, boolean[] robo, LinkedList<Corner> Corners){

        super(id);
        this.Corners = new LinkedList<>();
        this.Corners.add(new Corner(Corners.get(0).getRes(), Corners.get(0).getOrientation()));
        this.Corners.add(new Corner(Corners.get(1).getRes(), Corners.get(1).getOrientation()));
        this.Corners.add(new Corner(Corners.get(2).getRes(), Corners.get(2).getOrientation()));
        this.Corners.add(new Corner(Corners.get(3).getRes(), Corners.get(3).getOrientation()));


        BackRes = new boolean[4];

        for(int i=0; i<4; i++){
            BackRes[i] = robo[i];
        }
    }


    public LinkedList<Resources> getBackRes(){
        LinkedList<Resources> Res = new LinkedList<Resources>();

        if(this.BackRes[0])
            Res.add(Resources.PLANT_KINGDOM);
        if(this.BackRes[1])
            Res.add(Resources.ANIMAL_KINGDOM);
        if(this.BackRes[2])
            Res.add(Resources.FUNGI_KINGDOM);
        if(this.BackRes[3])
            Res.add(Resources.INSECT_KINGDOM);

        return Res;

    }
    public Corner getCorner(Orientation Orient){
        return switch(Orient){
            case HR -> this.Corners.get(0);
            case HL -> this.Corners.get(1);
            case LR -> this.Corners.get(2);
            case LL -> this.Corners.get(3);
        };
    }



}

