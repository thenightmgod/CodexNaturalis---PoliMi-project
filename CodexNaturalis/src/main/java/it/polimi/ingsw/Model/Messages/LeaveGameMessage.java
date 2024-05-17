package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class LeaveGameMessage extends Message {
    String name;
    CommonClient client;
    public LeaveGameMessage(String name, CommonClient client) {
        super("LeaveGameMessage");
        this.name=name;
        this.client= client;
    }


    public String getName() {
        return this.name;
    }

    public CommonClient getClient() {
        return client;
    }

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }
}
