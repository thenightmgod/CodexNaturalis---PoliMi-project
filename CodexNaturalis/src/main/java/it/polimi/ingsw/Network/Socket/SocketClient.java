package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Messages.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.VirtualView;
import it.polimi.ingsw.View.GameView;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

import static java.lang.System.exit;

//QUANDO CREO IL SOCKETCLIENT DEVO DARGLI COME NOME IL NOME DEL PLAYER
public class SocketClient implements CommonClient {
    final ServerProxy server;
    final BufferedReader input;
    final String name;

    public SocketClient(BufferedReader input, BufferedWriter output, String name) { //input e output sono rispettivamente il socket.getinputstream e socket.outputstream
        this.input = input;
        this.server = new ServerProxy(output);
        this.name = name;

    }
    public String getName() {
        return this.name;
    }

    //VA ASSOCIATO IL BUFFERED READER INPUT del client al PRINT WRITER DEL CLIENT PROXY
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

    //si mette in ascolto sul client e traduce le cose digitate sulla tui nelle chiamate ai propri metodi giusti
    private void runClient() {
        new Thread(() -> {
            BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = terminalReader.readLine()) != null) {
                switch(userInput){
                    case "placeCard" -> {
                        this.placeCard();
                    }
                }
            }
        }
    }


    public void runVirtualServer() {
        while(true){
            try{
                String receivedMessage;
                while((receivedMessage = input.readLine()) != null){

                }
            }
        }
    }

    @Override
    public void joinGame(String name) throws RoomFullException, RoomNotExistsException, RemoteException, NameAlreadyTakenException {
        JoinExistingGameMessage msg = new JoinExistingGameMessage(name);
        String gson = msg.MessageToJson();
        server.joinGame(gson);
    }

    @Override
    public void createGame(String name, int numPlayers) throws WrongPlayersNumberException, RemoteException {
        CreateGameMessage msg = new CreateGameMessage(name, numPlayers);
        String gson = msg.MessagetoJson();
        server.createGame(gson);
    }

    @Override
    public void leaveGame(String name, CommonClient client) {
        LeaveGameMessage msg = new LeaveGameMessage(name, client);
        String gson = msg.MessageToJson();
        server.leaveGame(gson);
    }

    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) throws WrongIndexException {
        PlaceCardMessage msg = new PlaceCardMessage(client, whichInHand, x, y, face);
        String gson = msg.MessageToJson();
        server.placeCard(gson);
    }

    @Override
    public void setStartCardFace(boolean face, CommonClient client) {
        SetStartCardFaceMessage msg = new SetStartCardFaceMessage(face, client);
        String gson = msg.MessageToJson();
        server.setStartCardFace(gson);
    }

    @Override
    public void chooseGoalCard(int i, CommonClient client)  {
        ChooseGoalCardMessage msg = new ChooseGoalCardMessage(i, client);
        String gson = msg.MessageToJson();
        server.chooseGoalcard(gson);
    }
    public void drawCard(int i, int whichOne, CommonClient client){
        DrawCardMessage msg = new DrawCardMessage(i, whichOne, client);
        String gson = msg.MessageToJson();
        server.drawCard(gson);
    }
    public void setView(GameView view){
        SetViewMessage msg = new SetViewMessage(view);
        String gson = msg.MessageToJson();
        server.setView(gson);
    }
    //il client deve avere un main che
    //si connette al socket del server
    //passandogli negli argomenti del main al posto 0 l'host,
    //al posto 1 la porta

    //lo stato della partita è sul model
    //

}
