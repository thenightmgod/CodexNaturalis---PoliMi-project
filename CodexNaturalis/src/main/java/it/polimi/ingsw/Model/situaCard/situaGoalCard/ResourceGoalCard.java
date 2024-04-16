package it.polimi.ingsw.Model.situaCard.situaGoalCard;

public class ResourceGoalCard extends GoalCard {

    private final Resources Res;
    public ResourceGoalCard(Resources R) {
        this.Res=R;
    }
    public Resources getRes() {
        return Res;
    }
    public int PointsCalc(Player player) {
        int ricorrenze, totpoints=0;

        ricorrenze= player.GetResourceCounter(Res);
        totpoints = ( ricorrenze/ 3 ) * this.GetPoints();
        //se getpoints() mi ritorna 2, mi dà due punti ogni ogni 3 risorse di quel tipo
        return totpoints;
    }
}
