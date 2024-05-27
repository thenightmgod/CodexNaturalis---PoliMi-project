package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

public class ChooseGoalCardMessage extends Message {
    private int i;
    private CommonClient client;

    public ChooseGoalCardMessage(int i, CommonClient client){
        super("ChooseGoalCardMessage");
        this.i=i;
        this.client=client;
    }
    public int getI() {
        return i;
    }

    public CommonClient getClient() {
        return client;
    }

    public SocketClient castCommonToSocket(CommonClient client) {
        return (SocketClient) client;
    }
}
