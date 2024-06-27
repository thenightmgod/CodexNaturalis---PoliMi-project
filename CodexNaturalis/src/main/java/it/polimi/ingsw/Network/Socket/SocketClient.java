package it.polimi.ingsw.Network.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.CardRes;
import it.polimi.ingsw.Model.CornerPackage.CardResDeserializer;
import it.polimi.ingsw.Model.CornerPackage.CardResSerializer;
import it.polimi.ingsw.Model.DeckPackage.GoalCardLoader;
import it.polimi.ingsw.Model.Messages.*;
import it.polimi.ingsw.Model.PlayerPackage.*;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.VirtualServer;
import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.ClientModel;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

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

    public String getNames() {
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
        this.model = new ClientModel(client.getNames());
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
                    Gson gson = new GsonBuilder().
                            registerTypeAdapter(CardRes.class, new CardResDeserializer())
                            .registerTypeAdapter(CardRes.class, new CardResSerializer())
                            .registerTypeAdapter(PlayingField.class, new PlayingFieldAdapter())
                            .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                            .create();
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(receivedMessage).getAsJsonObject();
                    String type = jsonObject.get("type").getAsString();
                    Class<?> clazz = Class.forName("it.polimi.ingsw.Model.Messages." + type);
                    Message message = gson.fromJson(receivedMessage, (Type) clazz);
                    handleCommand(message);
                }
            } catch (RuntimeException | IOException | NotBoundException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCommand(Message mex) throws IOException, NotBoundException {
        switch(mex.getType()) {
            case "UpdateColorsMessage" ->{
                Player turn = ((UpdateColorsMessage)mex).getTurn();
                LinkedList<PlayerColor> colors = ((UpdateColorsMessage)mex).getColors();
                this.view.updateColors(turn, colors);
            }
            case "IsAliveMessage" -> {
                String name = ((IsAliveMessage) mex).getName();
            }
            case "UpdateChatMessage" -> {
                String name = ((UpdateChatMessage) mex).getName();
                LinkedList<ChatMessage> chat = ((UpdateChatMessage) mex).getChat();
                this.view.updateChat(name, chat);
            }
            case "SendPlayerMessage" -> {
                LinkedList<String> players = ((SendPlayerMessage)mex).getPlayers();
                model.setPlayers(players);
            }
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
                this.view.printNotYourTurn(p, mess);
            }
            case "StartingGameMessage" -> {
                this.view.startingGame();
            }
            case "ShowStartCardMessage" -> {
                StartCard card = ((ShowStartCardMessage)mex).getStart();
                this.view.showStartCard(card);
            }
            case "ShowGoalsMessage" -> {
                LinkedList<Integer> goalIds= ((ShowGoalsMessage)mex).getGoals();
                String name = ((ShowGoalsMessage)mex).getName();
                Map<Integer, GoalCard> goalCards = GoalCardLoader.loadGoalCards();
                LinkedList<GoalCard> cards = goalIds.stream()
                        .map(goalCards::get)
                        .collect(Collectors.toCollection(LinkedList::new));
                this.view.updateGoals(cards, name);
            }
            case "UpdateCommonGoalsMessage" -> {
                LinkedList<Integer> goalIds = ((UpdateCommonGoalsMessage)mex).getGoalIds();
                String name = ((UpdateCommonGoalsMessage)mex).getName();
                Map<Integer, GoalCard> goalCards = GoalCardLoader.loadGoalCards();
                LinkedList<GoalCard> cards = goalIds.stream()
                        .map(goalCards::get)
                        .collect(Collectors.toCollection(LinkedList::new));
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
            case "DeclareWinnerMessage" -> {
                LinkedList<String> standings = ((DeclareWinnerMessage) mex).getStandings();
                this.view.declareWinner(standings);
            }
            case "LeaveGameMessage" -> {
                this.view.leaveGameMessage();
            }
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
    public void sendChatMessage(ChatMessage message) {
        ChatMessageMessage msg = new ChatMessageMessage(message, name);
        String gson = msg.MessageToJson();
        server.sendChatMessage(gson);
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

    @Override
    public ClientModel getClient() {
        return model;
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void endColor(PlayerColor color) {
        EndColorMessage msg = new EndColorMessage(name, color);
        String gson = msg.MessageToJson();
        server.endColor(gson);
    }
}
