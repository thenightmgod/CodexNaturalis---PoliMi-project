package it.polimi.ingsw.Model.PlayerPackage;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a position on the playing field.
 */
public class Position implements Serializable {
    private int x;
    private int y;
    private final FB face;


    /**
     * Constructs a position with the specified face, x-coordinate, and y-coordinate.
     * @param situa The face of the position.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(FB situa, int x, int y){
        face = situa;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a position with the default face (FRONT) and the specified x-coordinate and y-coordinate.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y){
        face = FB.FRONT;
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x-coordinate of the position.
     * @param x The x-coordinate to set.
     */
    public void setX(int x) { this.x=x; }

    /**
     * Sets the y-coordinate of the position.
     * @param y The y-coordinate to set.
     */
    public void setY(int y) { this.y=y; }


    /**
     * Retrieves the x-coordinate of the position.
     * @return The x-coordinate of the position.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the position.
     * @return The y-coordinate of the position.
     */
    public int getY() {
        return y;
    }

    /**
     * Retrieves the face of the position.
     * @return The face of the position.
     */
    public FB getFace() {
        return face;
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return this.x == position.getX() && this.y == position.getY() && this.face == position.getFace();
    }

    /**
     * Returns a hash code value for the position.
     * @return A hash code value for the position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, face);
    }

    @Override
    public String toString() {
        return "{" +
                 + x + " , "
                 + y +
                '}';
    }

}
