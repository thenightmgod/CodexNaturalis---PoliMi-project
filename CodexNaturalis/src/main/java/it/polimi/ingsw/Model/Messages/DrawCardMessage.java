package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

public class DrawCardMessage extends Message {
    private int i;
    private int whichOne;
    private CommonClient client;
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
    public SocketClient castCommonToSocket(CommonClient client) {
        return (SocketClient) client;
    }
}
