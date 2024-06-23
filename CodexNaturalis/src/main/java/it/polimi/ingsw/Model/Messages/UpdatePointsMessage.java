package it.polimi.ingsw.Model.Messages;

//MEX SERVER --> CLIENT

import java.util.HashMap;

public class UpdatePointsMessage extends Message {
    private HashMap<String, Integer> points;
    private String name;

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
