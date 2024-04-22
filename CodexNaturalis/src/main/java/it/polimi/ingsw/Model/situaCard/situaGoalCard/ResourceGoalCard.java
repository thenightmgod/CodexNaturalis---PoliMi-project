package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaPlayer.Player;

public class ResourceGoalCard extends GoalCard {
    private final Resources Res;
    public ResourceGoalCard(int id, int points, Resources R) {
        super(id, points);
        this.Res=R;
    }
    public Resources getRes() {
        return Res;
    }
    public int PointsCalc(Player player) {
        int ricorrenze, totpoints=0;

        ricorrenze= player.getResourceCounter(Res);
        totpoints = ( ricorrenze/ 3 ) * this.getPoints();
        //se getpoints() mi ritorna 2, mi dà due punti ogni ogni 3 risorse di quel tipo
        return totpoints;
    }
}
