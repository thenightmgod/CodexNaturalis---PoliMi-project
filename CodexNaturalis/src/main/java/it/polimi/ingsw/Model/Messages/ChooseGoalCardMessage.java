package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

public class ChooseGoalCardMessage extends Message {
    public int i;
    public CommonClient client;

    public ChooseGoalCardMessage(int i, CommonClient client){
        super("ChooseGoalCardMessage");
        this.i=i;
        this.client=client;
    }

    @Override
    public String getType() {
        return super.getType();
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

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }


}
