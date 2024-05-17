package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class DrawCardMessage extends Message {
    int i;
    int whichOne;
    CommonClient client;

    public DrawCardMessage(int i, int whichOne, CommonClient client){
        super("DrawCardMessage");
        this.i=i;
        this.whichOne=whichOne;
        this.client=client;
    }

    public CommonClient getClient() {
        return client;
    }

    public int getI() {
        return i;
    }

    public int getWhichOne() {
        return whichOne;
    }
}
