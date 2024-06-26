package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;

import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Represents the GoldCard that, to be deployed on the playing field,
 * need the player to meet the resource requirements.
 * In addition to standard ResourceCard, a GoldCard can give points based on
 * certain positions in which they are fielded or based on the number of Objects the player has.
 *
 */
public class GoldCard extends ResourceCard implements Serializable {
    /**
     * An array representing the resource requirements to play this GoldCard.
     * Each position in the array corresponds to a resource in the order they are declared
     * in the "Resources" enum, and each value represents the quantity of that resource required.
     */
    private int[] requirements;
    /**
     * The condition under which additional points can be earned when this GoldCard is played.
     * The condition can be positional ("CORNERS"), based on a number of objects (OBJECTS_QUILL,OBJECTS_INKWELL or OBJECTS_MANUSCRIPT)
     * or nothing ("FREE"). If the player meets this condition, they can get a certain number of points calculated by pointsCalc().
     */
    private PointsCondition PointsC;


    /**
     *Constructs a new GoldCard
     *
     * @param id the specific GoldCard's identifier
     * @param R the array of 4 positions, each rappresentative of a resource (in the same order of the enum "Resources"),
     *          containing in each position the number of that resource that the GoldCard has in its back.
     * @param Corners the List of four corners that each GoldCard has
     * @param c the GoldCard's color
     * @param p the GoldCard's points
     * @param requirementsInput the array of 4 positions, each rappresentative of a resource (in the same order of the enum "Resources"),
     *      *          containing in each position the number of that resource that the player must have to deploy the card on his playing field
     * @param pointsC the condition that can be positional ("CORNERS"), based on a number of objects (OBJECTS_QUILL,OBJECTS_INKWEL or OBJECTS_MANUSCRIPT)
     *                or nothing ("FREE"). If the player meets this condition, he can get a certain number of points calculated by pointsCalc().
     *
     */
    public GoldCard(int id, boolean[] R, LinkedList<Corner> Corners, CardColor c, int p, int[] requirementsInput, PointsCondition pointsC) {
        super(id, R, c, p, Corners);
        if (requirementsInput.length != 4) {
            throw new IllegalArgumentException("L'array di input deve essere di dimensione 4");
        }
        requirements = new int[4];
        // Copia gli elementi dall'array di requirementsinput a requirements
        for (int i = 0; i < 4; i++) {
            requirements[i] = requirementsInput[i];
        }
        this.PointsC = pointsC;
    }

    /**
     *
     * @return the array of 4 positions, each representative of a resource (in the same order of the enum "Resources"),
     *         containing in each position the number of that resource that the player must have to deploy the card on his playing field
     */
    public int[] getRequirements() {
        return requirements;
    }
    /**
     * Sets the resource requirements for this GoldCard.
     * The requirements are represented as an array of integers, where each position corresponds to a resource in the order they are declared
     * in the "Resources" enum, and each value represents the quantity of that resource required.
     *
     * @param requirements An array of integers representing the resource requirements for this GoldCard.
     */
    public void setRequirements(int[] requirements) {
        for (int i = 0; i < 4; i++) {
            this.requirements[i] = requirements[i];
        }
    }

    /**
     * @return the condition that can be positional ("CORNERS"), based on a number of objects (OBJECTS_QUILL,OBJECTS_INKWEL or OBJECTS_MANUSCRIPT)
     *         or nothing ("FREE"). If the player meets this condition, he can get a certain number of points calculated by pointsCalc().
     */
    public PointsCondition getPointsCondition() {
        return PointsC;
    }

    /**
     * Sets the condition under which additional points can be earned when this GoldCard is played.
     * If the player meets this condition, they can get a certain number of points calculated by pointsCalc().
     *
     * @param pointsCondition The new points condition for this GoldCard.
     */
    public void setPointsCondition(PointsCondition pointsCondition) {
        PointsC = pointsCondition;
    }
    /**
     *
     *Determines whether the player has the necessary resources, as described in the requirements, to place the GoldCard in the playing field.
     * Returns 1 if and only if the player has enough resources.
     *
     * @param p the player who wants to place the card on his field
     * @return a boolean that's 1 if the player can place the card
     */
    public boolean RequirementsOk(Player p) {
        boolean ok=true;
        for (int i=0; i<4; i++) {
                if(i==0) {
                    if(requirements[0] > p.getResourceCounter(Resources.PLANT_KINGDOM))
                        ok=false;
                }
                else if (i==1) {
                    if(requirements[1] > p.getResourceCounter(Resources.ANIMAL_KINGDOM))
                        ok=false;

                }
                else if(i==2) {
                    if (requirements[2] > p.getResourceCounter(Resources.FUNGI_KINGDOM))
                        ok=false;
                }
                else if(i==3) {
                    if (requirements[3] > p.getResourceCounter(Resources.INSECT_KINGDOM))
                        ok = false;
                }
            }
        return ok;
    }

    /**
     * Calculates the points that the player makes if he meets the resource requirements and places the card.
     * If the condition type of the GoldCard is positional, he gets point for each corner he covers with the GoldCard placement,
     * if the condition concerns an object, the player gets 1 point for each object of that type he owns, including the one on the goldCard.
     * Finally, if the condition is free, simply return the GoldCard's points.
     *
     * @param player the player who places the card and whose points we calculate
     * @return the total points the player makes after placing the card
     */
    public int PointsCalc(Player player, Position pos) {
        int PointsTot=0;
        switch(PointsC){
            case FREE -> PointsTot = this.getPoints();
            case OBJECTS_QUILL -> PointsTot = this.getPoints() * player.getObjectCounter(Objects.QUILL);
            case OBJECTS_INKWELL -> PointsTot = this.getPoints() * player.getObjectCounter(Objects.INKWELL);
            case OBJECTS_MANUSCRIPT -> PointsTot = this.getPoints() * player.getObjectCounter(Objects.MANUSCRIPT);
            case CORNERS -> PointsTot = PointsCorner(player, pos, PointsTot);
        };
        return PointsTot;
    }

    /**
     * For each covered corner in the playing field, if the field contains the position derived from the corner,
     * the total points are increased by 2.
     *
     * @param player The player whose points are being calculated.
     * @param pos The position on the playing field being checked.
     * @param PointsTot The initial total points.
     * @return The updated total points.
     */
    int PointsCorner(Player player, Position pos, int PointsTot){
        PlayingField field = player.getPlayerField();
        for(Orientation Orien : Orientation.values()) {
            if (field.getField().containsKey(field.getPosFromCorner(pos, Orien)))
                PointsTot += 2;
        }
        return PointsTot;
    }

    /**
     * * Returns a string representation of the GoldCard object.
     *  * This representation includes the following information:
     *  * - The ID of the GoldCard.
     *  * - The color of the GoldCard.
     *  * - The number of points associated with the GoldCard.
     *  * - The array of resources at the back of the GoldCard.
     *  * - The resources present at each corner of the GoldCard, along with their orientations.
     *  *
     *  * @return A string containing the ID, color, points, back resources, and corner resources of the GoldCard.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceCard ID: ").append(getId()).append("\n");
        sb.append("Color: ").append(getColor()).append("\n");
        sb.append("Points: ").append(getPoints()).append("\n");
        sb.append("BackRes: ").append((getBackRes())).append("\n");
        sb.append("Corners:").append("\n");

        // Itera su ogni angolo
        for (Orientation Orien : Orientation.values()) {
            Corner corner = getCorner(Orien);
            sb.append(Orien).append(": ").append(corner.getRes()).append("\n");
        }

        return sb.toString();
    }
}