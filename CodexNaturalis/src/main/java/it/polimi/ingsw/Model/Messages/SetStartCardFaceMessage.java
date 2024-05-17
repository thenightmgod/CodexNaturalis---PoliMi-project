package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class SetStartCardFaceMessage extends Message {
    public boolean Face;
    public CommonClient client;

    public SetStartCardFaceMessage(boolean face, CommonClient client) {
        super("SetStartCardFaceMessage");
        this.client=client;
        this.Face=face;
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public CommonClient getClient() {
        return client;
    }

    public boolean getFace() {
        return Face;
    }
}
