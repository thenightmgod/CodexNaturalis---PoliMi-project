package it.polimi.ingsw.Network.Socket;


import java.io.BufferedWriter;
import java.io.PrintWriter;
/**
 * The ServerProxy class is used to send messages to the server.
 * It contains methods to send messages to the server. It is used to hide the connection between the client and the server.
 */
public class ServerProxy {

    /**
     * The output stream of the client/
     */
    final PrintWriter output;

    public ServerProxy(PrintWriter output) {
        this.output = new PrintWriter(output);
    }

    public void joinGame(String stringMessage)  {
        sendMessage(stringMessage);
    }

    public void sendChatMessage(String message){
        sendMessage(message);
    }

    public void endColor(String message){
        sendMessage(message);
    }

    public void createGame(String stringMessage)  {
        sendMessage(stringMessage);
    }

    public void leaveGame(String stringMessage) {
        sendMessage(stringMessage);
    }

    public void placeCard(String stringMessage) {
        sendMessage(stringMessage);
    }

    public void setStartCardFace(String stringMessage) {
        sendMessage(stringMessage);
    }

    public void chooseGoalcard(String stringMessage) {
        sendMessage(stringMessage);
    }

    public void drawCard(String stringMessage){
        sendMessage(stringMessage);
    }

    public void setView(String stringMessage) {
        sendMessage(stringMessage);
    }
    public void checkGoals(String stringMessage) { sendMessage(stringMessage); }
    public void endTurn(String stringMessage) { sendMessage(stringMessage); }

    public void sendMessage(String stringMessage){
            output.write(stringMessage);
            output.write("\n");
            output.flush();
    }

}
