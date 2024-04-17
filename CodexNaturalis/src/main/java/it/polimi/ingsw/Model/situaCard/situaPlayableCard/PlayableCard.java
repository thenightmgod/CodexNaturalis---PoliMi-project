package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCard.Card;
import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;


public abstract class PlayableCard extends Card{
    private boolean[] BackRes;
    private Corner HR;
    private Corner HL;
    private Corner LR;
    private Corner LL;

    public PlayableCard(int id,int tiporesource){
        super(id);
        for(int i=0; i<4; i++){
            if(i==tiporesource){
                this.BackRes[i]= true; //metto uno nella posizione corrisponde alla risorsa
            }
            else{
                this.BackRes[i]= false;
            }
        }
    }
    public int getBackRes(){
        for(int i=0; i<4; i++){
            if(this.BackRes[i]==true){
                return i;
            }
        }
        return 0;
    }
    public Corner getCorner(Orientation Orient){
        switch(Orient){
            case HR:
                return this.HR;
            case HL:
                return this.HL;
            case LR:
                return this.LR;
            case LL:
                return this.LL;
        }
        return null;
    }


}

