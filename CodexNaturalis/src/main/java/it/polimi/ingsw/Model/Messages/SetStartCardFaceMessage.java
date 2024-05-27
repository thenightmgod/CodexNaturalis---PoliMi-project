package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class SetStartCardFaceMessage extends Message {
    private boolean Face;
    private CommonClient client;

    public SetStartCardFaceMessage(boolean face, CommonClient client) {
        super("SetStartCardFaceMessage");
        this.client=client;
        this.Face=face;
    }
    public CommonClient getClient() {
        return client;
    }

    public boolean getFace() {
        return Face;
    }
}
