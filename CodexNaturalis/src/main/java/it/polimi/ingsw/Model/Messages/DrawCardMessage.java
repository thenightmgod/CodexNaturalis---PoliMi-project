package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

public class DrawCardMessage extends Message {
    public int i;
    public int whichOne;
    public String name;
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
