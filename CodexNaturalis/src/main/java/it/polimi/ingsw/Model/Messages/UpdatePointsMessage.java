package it.polimi.ingsw.Model.Messages;

//MEX SERVER --> CLIENT

public class UpdatePointsMessage extends Message {
    int points;
    String name;

    public UpdatePointsMessage(int points, String name) {
        super("UpdatePointsMessage");
        this.points=points;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public int getPoints() {
        return points;
    }
    public String MessagetoJson() {
        return super.MessageToJson();
    }
}
