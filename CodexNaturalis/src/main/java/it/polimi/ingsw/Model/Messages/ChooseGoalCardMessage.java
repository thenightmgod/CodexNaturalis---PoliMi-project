package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;

public class ChooseGoalCardMessage extends Message {
    public int i;
    public CommonClient client;

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

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }

}
