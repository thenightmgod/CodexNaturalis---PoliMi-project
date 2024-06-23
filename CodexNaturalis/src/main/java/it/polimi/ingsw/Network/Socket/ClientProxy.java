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
    public void twenty(String StringMessage) { sendMessage(StringMessage); }
    public void lastRound(String StringMessage) { sendMessage(StringMessage); }
    public void notYourTurn(String StringMessage) { sendMessage(StringMessage); }
    public void updatePoints(String StringMessage) {
        sendMessage(StringMessage);
    }
    public void updateTurn(String StringMessage) { sendMessage(StringMessage); }
    public void declareWinner(String StringMessage) { sendMessage(StringMessage); }
    public void startingGame(String StringMessage) { sendMessage(StringMessage);}
    public void showStartCard(String StringMessage) {  sendMessage(StringMessage);}
    public void showGoals(String StringMessage) {
        sendMessage(StringMessage);
    }
    public void updateCommonGoals(String StringMessage) { sendMessage(StringMessage); }
    public void showHand(String StringMessage) {  sendMessage(StringMessage);  }
    public void updateField(String StringMessage) {
        sendMessage(StringMessage);
    }
    public void showFreePositions(String StringMessage)  {
        sendMessage(StringMessage);
    }
    public void updateGoldDeck(String StringMessage) { sendMessage(StringMessage); }
    public void updateResourceDeck(String StringMessage) { sendMessage(StringMessage); }
    public void showOtherField(String StringMessage) {
        sendMessage(StringMessage);
    }
    public void leaveGameMessage(String StringMessage) { sendMessage(StringMessage); }


    public void sendMessage(String stringMessage){
        output.write(stringMessage);
        output.flush();
    }
}
