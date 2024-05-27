package it.polimi.ingsw.Model.Messages;

//MEX SERVER --> CLIENT

public class UpdatePointsMessage extends Message {
    private int points;
    private String name;

    public UpdatePointsMessage(int points, String name) {
        super("UpdatePointsMessage");
        this.points=points;
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public int getPoints() {
        return points;
    }
}
