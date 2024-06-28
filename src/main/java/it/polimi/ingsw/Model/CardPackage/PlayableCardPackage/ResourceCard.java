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
    /**
     * The color of the ResourceCard. The color can be one of the values defined in the CardColor enum.
     */
    private CardColor Color;
    /**
     * The points associated with the ResourceCard.
     */
    private int Points;
    /**
     * The check status of the ResourceCard. This is used to determine whether this card has been already used to calculate a player's goal card.
     */
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
     * Sets the color of the ResourceCard.
     *
     * @param color The color of the ResourceCard.
     */
    public void setColor(CardColor color) {
        Color = color;
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
     * Sets the points of the ResourceCard.
     *
     * @param points The points of the ResourceCard.
     */
    public void setPoints(int points) {
        Points = points;
    }

    /**
     * Sets the check status of the ResourceCard to true.
     * This is used to mark the card as used in the game logic.
     */
    public void setCheck(){
        Check = true;
    }
    /**
     * Sets the check status of the ResourceCard.
     * This is used to mark the card as used or unused in the game logic.
     *
     * @param check The new check status for the ResourceCard.
     */
    public void setCheckk(boolean check) {
        Check = check;
    }
    /**
     * Gets the check status of the ResourceCard.
     * This is used to check whether the card has been used in the game logic.
     *
     * @return The check status of the ResourceCard.
     */
    public boolean getCheck(){
        return Check;
    }

    /**
     * Returns a string representation of the ResourceCard.
     *
     * @return A string containing the ID, color, points, back resources, and corners of the ResourceCard.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceCard ID: ").append(getId()).append("\n");
        sb.append("Color: ").append(Color).append("\n");
        sb.append("Points: ").append(Points).append("\n");
        sb.append("BackRes: ").append(getBackRes()).append("\n");
        sb.append("Corners:").append("\n");

        for (Orientation orientation : Orientation.values()) {
            Corner corner = getCorner(orientation);
            sb.append(orientation).append(": ").append(corner.getRes()).append("\n");
        }
        return sb.toString();
    }
}

