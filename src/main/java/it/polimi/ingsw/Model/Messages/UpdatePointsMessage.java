package it.polimi.ingsw.Model.Messages;

//MEX SERVER --> CLIENT

import java.util.HashMap;
/**
 * The UpdatePointsMessage class extends the Message class and represents a message that contains the points of the players.
 */
public class UpdatePointsMessage extends Message {
    /**
     * The points of the players.
     */
    public HashMap<String, Integer> points;
    /**
     * The name of the player to whom is sent the points.
     */
    public String name;
    /**
     * Constructs a new UpdatePointsMessage with the specified points and name.
     *
     * @param points The points of the players.
     * @param name The name of the player to whom is sent the points.
     */
    public UpdatePointsMessage(HashMap<String, Integer> points, String name) {
        super("UpdatePointsMessage");
        this.points=points;
        this.name=name;
    }

    /**
     * Returns the name of the player to whom is sent the points.
     *
     * @return The name of the player to whom is sent the points.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the points of the players.
     *
     * @return The points of the players.
     */
    public HashMap<String, Integer> getPoints() {
        return points;
    }
}
