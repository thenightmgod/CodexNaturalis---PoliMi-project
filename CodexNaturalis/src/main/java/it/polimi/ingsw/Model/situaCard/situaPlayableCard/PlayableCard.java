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
    private Corner HR; //ragionare poi su final eventualità
    private Corner HL;
    private Corner LR;
    private Corner LL;

    public PlayableCard(int id, LinkedList<Resources> R, LinkedList<Corner> Corners){

        super(id);

        this.HR = Corners.get(0);
        this.HL = Corners.get(1);
        this.LR = Corners.get(2);
        this.LL = Corners.get(3);

        BackRes = new boolean[4];

        BackRes[0] = false;
        BackRes[1] = false;
        BackRes[2] = false;
        BackRes[3] = false;

        for(Resources elemento : R) {
            switch (elemento) {
                case PLANT_KINGDOM -> this.BackRes[0] = true;
                case ANIMAL_KINGDOM -> this.BackRes[1] = true;
                case FUNGI_KINGDOM -> this.BackRes[2] = true;
                case INSECT_KINGDOM -> this.BackRes[3] = true;
            }
        }
    }
    public LinkedList<Resources> getBackRes(){
        LinkedList<Resources> Res = new LinkedList<Resources>();

        if(this.BackRes[0])
            Res.add(Resources.PLANT_KINGDOM);
        else if(this.BackRes[1])
            Res.add(Resources.ANIMAL_KINGDOM);
        else if(this.BackRes[2])
            Res.add(Resources.FUNGI_KINGDOM);
        else if(this.BackRes[3])
            Res.add(Resources.INSECT_KINGDOM);

        return Res;

    }
    public Corner getCorner(Orientation Orient){
        return switch (Orient) {
            case HR -> this.HR;
            case HL -> this.HL;
            case LR -> this.LR;
            case LL -> this.LL;
        };
    }


}

