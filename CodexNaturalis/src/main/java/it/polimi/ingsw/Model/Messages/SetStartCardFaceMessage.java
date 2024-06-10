package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class SetStartCardFaceMessage extends Message {
    private boolean Face;

    public SetStartCardFaceMessage(boolean face) {
        super("SetStartCardFaceMessage");
        this.Face=face;
    }

    public boolean getFace() {
        return Face;
    }
}
