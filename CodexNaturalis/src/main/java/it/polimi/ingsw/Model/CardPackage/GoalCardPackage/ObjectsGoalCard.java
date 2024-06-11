package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CornerPackage.Objects;

public class ObjectsGoalCard extends GoalCard {
    private final int[] obj = new int[3];
    // QUILL, INKWELL, MANUSCRIPT


    public ObjectsGoalCard(int id, int points, int[] Obj) {
        super(id, points);
        for (int i = 0; i < 3; i++) {
            obj[i] = Obj[i];
        }
    }

    public int[] getObj() {
        return obj;
    }

    public int pointsCalc(Player player) {
        int points = 0;
        switch(getPoints()){
            case 3 ->{
                for(int i: player.getObjects()){
                    if(obj[i] < points)
                        points = obj[i];
                }
                return points * 3;
            }
            case 2 ->{
                if(obj[0] == 2){
                    int counter = player.getObjectCounter(Objects.QUILL);
                    points = counter / 2;
                    return points * 2;
                }
                else if(obj[1] == 2){
                    int counter = player.getObjectCounter(Objects.INKWELL);
                    points = counter / 2;
                    return points * 2;
                }
                else if(obj[2] == 2){
                    int counter = player.getObjectCounter(Objects.MANUSCRIPT);
                    points = counter / 2;
                    return points * 2;
                }
            }
        }
        return points;
    }

}