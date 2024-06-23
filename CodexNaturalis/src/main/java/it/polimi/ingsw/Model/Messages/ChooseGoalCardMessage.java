package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

public class ChooseGoalCardMessage extends Message {
    public int i;
    public String name;

    public ChooseGoalCardMessage(int i, String name){
        super("ChooseGoalCardMessage");
        this.i=i;
        this.name = name;
    }
    public int getI() {
        return i;
    }

    public String getName() {
        return name;
    }
}
