package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Position;
import java.util.LinkedList;

public class ShowFreePositionsMessage extends Message {
    private String name;
    private LinkedList<Position> freePosition;

    public ShowFreePositionsMessage(String name, LinkedList<Position> positions) {
        super("ShowFreePositionsMessage");
        this.name=name;
        this.freePosition=positions;
    }

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public LinkedList<Position> getFreePosition() {
        return freePosition;
    }
    public String getName() {
        return name;
    }
}
