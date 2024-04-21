package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCard.Card;
import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.LinkedList;
import java.util.SortedSet;

public abstract class PlayableCard extends Card{
    private boolean[] BackRes;
    private Corner HR; //ragionare poi su final eventualità
    private Corner HL;
    private Corner LR;
    private Corner LL;

    public PlayableCard(int id, boolean[] robo, LinkedList<Corner> Corners){

        super(id);
        this.HR = new Corner(Corners.get(0).getRes(), Orientation.HR);
        this.HL = new Corner(Corners.get(1).getRes(), Orientation.HL);
        this.LR = new Corner(Corners.get(2).getRes(), Orientation.LR);
        this.LL = new Corner(Corners.get(3).getRes(), Orientation.LL);

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
        return switch (Orient) {
            case HR -> this.HR;
            case HL -> this.HL;
            case LR -> this.LR;
            case LL -> this.LL;
        };
    }


}

