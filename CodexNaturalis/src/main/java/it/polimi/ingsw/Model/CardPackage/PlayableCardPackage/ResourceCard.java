package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;


import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.io.Serializable;
import java.util.LinkedList;

import static it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PointsCondition.*;

/**
 * Represents a Resource Card in the game.
 * Extends the PlayableCard class.
 */
public class ResourceCard extends PlayableCard implements Serializable {
    private final CardColor Color;
    private final int Points;

    private boolean Check;

    /**
     * Constructs a new ResourceCard.
     *
     * @param id      The unique identifier of the ResourceCard.
     * @param R       The array representing the resources at the back of the card.
     * @param c       The color of the ResourceCard.
     * @param p       The points associated with the ResourceCard.
     * @param Corners The list of corners for the ResourceCard.
     */
    public ResourceCard(int id, boolean[] R, CardColor c, int p, LinkedList<Corner> Corners) {
        super(id, R, Corners);
        this.Color = c;
        this.Points = p;
        this.Check = false;
    }

    /**
     * Gets the color of the ResourceCard.
     *
     * @return The color of the ResourceCard.
     */
    public CardColor getColor() {
        return Color;
    }

    /**
     * Gets the points associated with the ResourceCard.
     *
     * @return The points of the ResourceCard.
     */
    public int getPoints() {
        return Points;
    }

    /**
     * Returns a string representation of the ResourceCard.
     *
     * @return A string containing the ID, color, points, back resources, and corners of the ResourceCard.
     */

    public void setCheck(){
        Check = true;
    }

    public boolean getCheck(){
        return Check;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceCard ID: ").append(getId()).append("\n");
        sb.append("Color: ").append(Color).append("\n");
        sb.append("Points: ").append(Points).append("\n");
        sb.append("BackRes: ").append(getBackRes()).append("\n");
        sb.append("Corners:").append("\n");

        // Iterates over each Orientation value
        for (Orientation orientation : Orientation.values()) {
            // Gets the corner for the current orientation
            Corner corner = getCorner(orientation);

            // Adds the corner information to the output string
            sb.append(orientation).append(": ").append(corner.getRes()).append("\n");
        }
        return sb.toString();
    }
}

