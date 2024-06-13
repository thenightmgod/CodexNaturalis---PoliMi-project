package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class SetStartCardFaceMessage extends Message {
    private boolean Face;
    private String name;

    public SetStartCardFaceMessage(boolean face, String name) {
        super("SetStartCardFaceMessage");
        this.Face=face;
        this.name = name;
    }

    public boolean getFace() {
        return Face;
    }

    public String getName() {
        return name;
    }
}
