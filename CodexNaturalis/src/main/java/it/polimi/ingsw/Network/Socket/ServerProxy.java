package it.polimi.ingsw.Network.Socket;


import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ServerProxy implements VirtualServerSocket {

    final PrintWriter output;

    public ServerProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }
    @Override
    public void joinGame(String stringMessage)  {
        sendMessage(stringMessage);
    }

    @Override
    public void createGame(String stringMessage)  {
        sendMessage(stringMessage);
    }

    @Override
    public void leaveGame(String stringMessage) {
        sendMessage(stringMessage);
    }

    @Override
    public void placeCard(String stringMessage) {
        sendMessage(stringMessage);
    }

    @Override
    public void setStartCardFace(String stringMessage) {
        sendMessage(stringMessage);
    }

    @Override
    public void chooseGoalcard(String stringMessage) {
        sendMessage(stringMessage);
    }

    public void drawCard(String stringMessage){
        sendMessage(stringMessage);
    }

    @Override
    public void setView(String stringMessage) {
        sendMessage(stringMessage);
    }

    public void sendMessage(String stringMessage){
            output.write(stringMessage);
            output.flush();
    }
    //per ogni metodo che chiamo dal client mando sul output del canale output.write(gson)

}
