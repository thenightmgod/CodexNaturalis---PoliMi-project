package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CornerPackage.Objects;

/**
 * Represents an ObjectsGoalCard in the game.
 * An ObjectsGoalCard is a type of GoalCard that has a specific set of objects.
 */
public class ObjectsGoalCard extends GoalCard {
    /**
     * An array of integers representing the objects of the ObjectsGoalCard.
     * The array has a fixed size of 3, each index representing a specific object.
     */
    private final int[] obj = new int[3];
    // QUILL, INKWELL, MANUSCRIPT

    /**
     * Constructs an ObjectsGoalCard with the specified id, points, and objects.
     * @param id The id of the card.
     * @param points The points of the card.
     * @param Obj The objects of the card.
     */
    public ObjectsGoalCard(int id, int points, int[] Obj) {
        super(id, points);
        for (int i = 0; i < 3; i++) {
            obj[i] = Obj[i];
        }
    }

    /**
     * Returns the objects of the card.
     * @return The objects of the card.
     */
    public int[] getObj() {
        return obj;
    }

    /**
     * Calculates the points for the specified player based on the objects of the card.
     * @param player The player to calculate the points for.
     * @return The calculated points.
     */
    public int pointsCalc(Player player) {
        int points = 0;
        switch(getPoints()){
            case 3 ->{
                int objj[] = player.getObjects();
                points = Math.min(objj[0], Math.min(objj[1], objj[2]));
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