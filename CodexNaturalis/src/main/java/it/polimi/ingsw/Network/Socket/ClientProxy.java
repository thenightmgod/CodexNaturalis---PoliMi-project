package it.polimi.ingsw.Network.Socket;


import java.io.PrintWriter;
import java.rmi.RemoteException;


public class ClientProxy {
    final PrintWriter output;
    public ClientProxy(PrintWriter output) {
        this.output = new PrintWriter(output);
    }

    public void showException(String StringMessage) {
        sendMessage(StringMessage);
    }

    public void updatePoints(String StringMessage) {
        sendMessage(StringMessage);
    }

    public void showGoals(String StringMessage) {
        sendMessage(StringMessage);
    }


    public void showHand(String StringMessage) {
        sendMessage(StringMessage);

    }


    public void updateField(String StringMessage) {
        sendMessage(StringMessage);
    }


    public void showFreePositions(String StringMessage)  {
        sendMessage(StringMessage);
    }

    public void update()  {
    }

    public void showOtherField(String StringMessage) {
        sendMessage(StringMessage);
    }
    public void sendMessage(String stringMessage){
        output.write(stringMessage);
        output.flush();
    }


}
