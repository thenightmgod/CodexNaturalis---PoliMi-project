package it.polimi.ingsw.Model.Messages;

//MEX SERVER --> CLIENT

import java.util.HashMap;

public class UpdatePointsMessage extends Message {
    public HashMap<String, Integer> points;
    public String name;

    public UpdatePointsMessage(HashMap<String, Integer> points, String name) {
        super("UpdatePointsMessage");
        this.points=points;
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public HashMap<String, Integer> getPoints() {
        return points;
    }
}
