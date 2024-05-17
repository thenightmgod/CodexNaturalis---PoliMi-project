package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Messages.CreateGameMessage;
import it.polimi.ingsw.Model.Messages.JoinExistingGameMessage;
import it.polimi.ingsw.Model.Messages.LeaveGameMessage;
import it.polimi.ingsw.Model.Messages.PlaceCardMessage;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

import static java.lang.System.exit;

//il client deve
public class SocketClient implements CommonClient {
    final ServerProxy server;
    final BufferedReader input;
    final String name;

    public SocketClient(BufferedReader input, BufferedWriter output, String name) { //input e output sono rispettivamente il socket.getinputstream e socket.outputstream
        this.input = input;
        this.server = new ServerProxy(output);
        this.name = name;

    }
    public void initializeClient(String hostname, int ServerPort) {
        Socket socket;
        OutputStreamWriter socketTx;
        InputStreamReader socketRx;
        try {
            socket = new Socket(hostname, ServerPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socketRx = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
            exit(1);
        }
        try {
            socketTx = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
            exit(1);
        }
        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
    }

    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        this.runClient();
    }

    //si mette in ascolto sul client e
    private void runClient() {
        new Thread(() -> {
            BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = terminalReader.readLine()) != null) {
                switch(userInput){
                    case "placeCard" -> {
                        this.placeCard()
                    }
                }
            }
        }
    }


    public void runVirtualServer() {

    }

    @Override
    public void joinGame(String name) throws RoomFullException, RoomNotExistsException, RemoteException, NameAlreadyTakenException {
        name = this.name;
        JoinExistingGameMessage msg = new JoinExistingGameMessage(name, color, RoomId);
        String gson = msg.MessageToJson();
        server.joinGame(gson);
    }

    @Override
    public void createGame(String name, int numPlayers) throws WrongPlayersNumberException, RemoteException {
        name = this.name;
        CreateGameMessage msg = new CreateGameMessage(name, numPlayers, color);
        String gson = msg.MessagetoJson();
        server.createGame(gson);
    }

    @Override
    public void leaveGame(String name, CommonClient client) {
        name = this.name
        LeaveGameMessage msg = new LeaveGameMessage(name, );
    }

    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) throws WrongIndexException {
        PlaceCardMessage msg = new PlaceCardMessage()
    }

    @Override
    public void setStartCardFace(boolean face, CommonClient client) {

    }

    @Override
    public void chooseGoalCard(int i, CommonClient client) throws WrongIndexException {

    }

    //il client deve avere un main che
    //si connette al socket del server
    //passandogli negli argomenti del main al posto 0 l'host,
    //al posto 1 la porta

    //lo stato della partita è sul model
    //

}
