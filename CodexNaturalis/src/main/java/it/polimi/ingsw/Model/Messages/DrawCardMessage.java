package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

public class DrawCardMessage extends Message {
    private int i;
    private int whichOne;
    private String name;
    public DrawCardMessage(int i, int whichOne, String name){
        super("DrawCardMessage");
        this.i=i;
        this.whichOne=whichOne;
        this.name = name;
    }


    public int getI() {
        return i;
    }

    public int getWhichOne() {
        return whichOne;
    }

    public String getName() {
        return name;
    }
}
