package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;

import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;

import java.io.Serializable;
import java.util.LinkedList;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static it.polimi.ingsw.Model.CornerPackage.Resources.*;
import static it.polimi.ingsw.Model.CornerPackage.Resources.INSECT_KINGDOM;

/**
 * Represents the starting card used in the game.
 * Each player receives a StartCard in their first turn, and it will be the first card placed in their playing field.
 */
public class StartCard extends PlayableCard implements Serializable {
    /**
     * A list of corners at the back of the StartCard.
     */
    private LinkedList<Corner> BackCorners;

    /**
     * Constructs a new StartCard.
     *
     * @param id          the specific identifier of the StartCard
     * @param R           the array of 4 positions, each representative of a resource (in the same order of the enum "Resources"),
     *                    containing in each position the number of that resource that the StartCard has in its back.
     * @param BackCorners the list of four corners that each StartCard has in the back
     * @param Corners the list of four corners that each StartCard has in the front
     */
    public StartCard(int id, boolean[] R, LinkedList<Corner> BackCorners, LinkedList<Corner> Corners) {
        super(id, R, Corners);
        this.BackCorners = new LinkedList<>();
        this.BackCorners.add(new Corner(BackCorners.get(0).getRes(), BackCorners.get(0).getOrientation()));
        this.BackCorners.add(new Corner(BackCorners.get(1).getRes(), BackCorners.get(1).getOrientation()));
        this.BackCorners.add(new Corner(BackCorners.get(2).getRes(), BackCorners.get(2).getOrientation()));
        this.BackCorners.add(new Corner(BackCorners.get(3).getRes(), BackCorners.get(3).getOrientation()));
    }

    /**
     * Returns the specified corner based on the provided orientation.
     * @param Orient the orientation
     * @return the corner based on the orientation
     */
    public Corner getBackCorner(Orientation Orient){
        return switch(Orient){
            case HR -> this.BackCorners.get(0);
            case HL -> this.BackCorners.get(1);
            case LR -> this.BackCorners.get(2);
            case LL -> this.BackCorners.get(3);
        };
    }
    /**
     * Adds a corner to the back of the StartCard.
     *
     * @param corner The corner to be added to the back of the StartCard.
     */
    public void addBackCorner(Corner corner){
        this.BackCorners.add(corner);
    }
}