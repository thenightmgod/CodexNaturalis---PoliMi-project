package it.polimi.ingsw.Network.Socket;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.Messages.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.VirtualServer;
import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.ClientModel;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.System.exit;

//QUANDO CREO IL SOCKETCLIENT DEVO DARGLI COME NOME IL NOME DEL PLAYER
public class SocketClient implements CommonClient {
    private ServerProxy server;
    private BufferedReader input;
    private String name;
    private GameView view;
    private ClientModel model;

    public SocketClient(String name) {
        this.name = name;
        String ip = "127.0.0.1";
        this.initializeClient(this, ip, 44458);
    }

    public String getName() {
        return this.name;
    }


    //VA ASSOCIATO IL BUFFERED READER INPUT del client al PRINT WRITER DEL CLIENT PROXY
    public void initializeClient(SocketClient client, String ip, int ServerPort) {
        Socket socket;
        PrintWriter socketTx= null;
        InputStreamReader socketRx= null;
        try {
            socket = new Socket(ip, ServerPort);
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
            socketTx = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e);
            exit(1);
        }
        this.model = new ClientModel(client.getName());
        this.input = new BufferedReader(socketRx);
        this.server = new ServerProxy(socketTx);
        this.run();
    }

    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    //si mette in ascolto sul client e traduce le cose digitate sulla tui nelle chiamate ai propri metodi giusti


    //      GESTISCE LE FUNZIONI DELLA VIRTUAL VIEW:
    public void runVirtualServer()  {
        String receivedMessage;
        while (true) {
            try {
                while ((receivedMessage = input.readLine()) != null) {
                    receivedMessage = input.readLine();
                    Gson gson = new Gson();
                    Message mex = gson.fromJson(receivedMessage, Message.class);
                    handleCommand(mex);
                }
            } catch (RuntimeException | IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCommand(Message mex) throws RemoteException, NotBoundException {
        switch(mex.getType()) {
            case "TwentyMessage" -> {
                String name = ((TwentyMessage)mex).getName();
                this.view.twenty(name);
            }
            case "LastRoundMessage" -> {
                this.view.lastRound();
            }
            case "ExceptionMessage" -> {
                String details= ((ExceptionMessage)mex).getDetails();
                String exception = ((ExceptionMessage)mex).getException();
                this.view.showException(exception, details);
            }
            case "UpdatePointsMessage" -> {
                HashMap<String, Integer> points= ((UpdatePointsMessage)mex).getPoints();
                String name= ((UpdatePointsMessage)mex).getName();
                this.view.updatePoints(points, name);
            }
            case "UpdateTurnMessage" -> {
                Player p = ((UpdateTurnMessage)mex).getPlayer();
                String messaggio = ((UpdateTurnMessage)mex).getMex();
                this.view.updateTurn(p, messaggio);
            }
            case "NotYourTurnMessage" -> {
                Player p = ((NotYourTurnMessage)mex).getPlayer();
                String mess = ((NotYourTurnMessage)mex).getMex();
                this.view.printNotYourTurn(p, "blabla");
            }
            case "StartingGameMessage" -> {
                this.view.startingGame();
            }
            case "ShowStartCardMessage" -> {
                StartCard card = ((ShowStartCardMessage)mex).getStart();
                this.view.showStartCard(card);
            }
            case "ShowGoalsMessage" -> {
                LinkedList<GoalCard> cards= ((ShowGoalsMessage)mex).getGoals();
                String name = ((ShowGoalsMessage)mex).getName();
                this.view.updateGoals(cards, name);
            }
            case "UpdateCommonGoalsMessage" -> {
                LinkedList<GoalCard> cards= ((UpdateCommonGoalsMessage)mex).getGoals();
                String name = ((UpdateCommonGoalsMessage)mex).getName();
                this.view.updateCommonGoals(cards, name);
            }
            case "ShowHandMessage" -> {
                LinkedList<PlayableCard> hand = ((ShowHandMessage)mex).getHand();
                String name = ((ShowHandMessage)mex).getName();
                this.view.updateHands(hand, name);
            }
            case "UpdateFieldMessage" -> {
                PlayingField playingField= ((UpdateFieldMessage)mex).getPlayingField();
                String name = ((UpdateFieldMessage)mex).getName();
                this.view.updateField(playingField, name);
            }
            case "ShowFreePositionsMessage" -> {
                String name = ((ShowFreePositionsMessage)mex).getName();
                LinkedList<Position> freePositions = ((ShowFreePositionsMessage)mex).getFreePosition();
                this.view.updateFreePosition(name, freePositions);
            }
            case "UpdateGoldDeckMessage" -> {
                String name = ((UpdateGoldDeckMessage)mex).getName();
                LinkedList<GoldCard> goldy = ((UpdateGoldDeckMessage)mex).getDeck();
                boolean start = ((UpdateGoldDeckMessage)mex).isStart();
                this.view.updateGoldDeck(goldy, start, name);
            }
            case "UpdateResourceDeckMessage" -> {
                String name = ((UpdateResourceDeckMessage)mex).getName();
                LinkedList<ResourceCard> resourcy = ((UpdateResourceDeckMessage)mex).getDeck();
                boolean start = ((UpdateResourceDeckMessage)mex).isStart();
                this.view.updateResourceDeck(resourcy, start, name);
            }
            case "ShowOtherField"-> {
                //Lori non sa ancora se mettere la showOtherField nella GameView o no
            }
            /*case "DeclareWinnerMessage" -> {
                HashMap<String, Integer> c = ((DeclareWinnerMessage)mex).getClassifica();
                this.view.declareWinner(c); */
        }
    }


    //             FUNZIONI DEL COMMONCLIENT
    @Override
    public void setStartCardFace(boolean face, CommonClient client) {
        SetStartCardFaceMessage msg = new SetStartCardFaceMessage(face, name);
        String gson = msg.MessageToJson();
        server.setStartCardFace(gson);
    }

    @Override
    public void joinGame(String name) {
        JoinExistingGameMessage msg = new JoinExistingGameMessage(name);
        String gson = msg.MessageToJson();
        server.joinGame(gson);
    }

    @Override
    public void createGame(String name, int numPlayers) {
        CreateGameMessage msg = new CreateGameMessage(name, numPlayers);
        String gson = msg.MessageToJson();
        server.createGame(gson);
    }


    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) {
        PlaceCardMessage msg = new PlaceCardMessage(name, whichInHand, x, y, face);
        String gson = msg.MessageToJson();
        server.placeCard(gson);
    }

    @Override
    public void chooseGoalCard(int i, CommonClient client)  {
        ChooseGoalCardMessage msg = new ChooseGoalCardMessage(i, name);
        String gson = msg.MessageToJson();
        server.chooseGoalcard(gson);
    }
    @Override
    public void drawCard(int i, int whichOne, CommonClient client){
        DrawCardMessage msg = new DrawCardMessage(i, whichOne, name);
        String gson = msg.MessageToJson();
        server.drawCard(gson);
    }
    public void setView(GameView view){
        this.view = view;
    }
    //il client deve avere un main che
    //si connette al socket del server
    //passandogli negli argomenti del main al posto 0 l'host,
    //al posto 1 la porta
    //
    @Override
    public ClientModel getClient() {
        return model;
    }
    public void checkGoals(String name){
        CheckGoalCardMessage msg = new CheckGoalCardMessage(name);
        String gson = msg.MessageToJson();
        server.checkGoals(gson);
    }
    @Override
    public void endTurn(String name, String mex){
        EndTurnMessage msg = new EndTurnMessage(name, mex);
        String gson = msg.MessageToJson();
        server.endTurn(gson);
    }
    /**
     * Gets the server.
     *
     * @return The server.
     */
    @Override
    public VirtualServer getServer() {
        return null;
    }

}
