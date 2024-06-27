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
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.exit;

/**
 * The SocketClient class is used to create a client that connects to the server via socket.
 * It contains methods to send messages to the server and to handle the messages received from the server.
 */
public class SocketClient implements CommonClient {
    /**
     * The server proxy.
     */
    private ServerProxy server;
    /**
     * The input stream of the client.
     */
    private BufferedReader input;
    /**
     * The name of the client.
     */
    private String name;
    /**
     * The view of this client.
     */
    private GameView view;
    /**
     * The ClientModel of this client.
     */
    private ClientModel model;
    /**
     * The constructor for the SocketClient class.
     *
     * @param name The name of the client.
     */
    public SocketClient(String name, String serverIp) {
        this.name = name;
        this.initializeClient(this, serverIp, 44458);
    }

    /**
     * Gets the name of the client.
     *
     * @return The name of the client.
     */
    public String getNames() {
        return this.name;
    }


    /**
     * Initializes the client.
     *
     * @param client The client.
     * @param ip The IP address of the server.
     * @param ServerPort The port of the server.
     */
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

    /**
     * Runs the client.
     */
    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Runs the virtual server. This method is used to handle the messages received from the server.
     */
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

    /**
     * Handles the command received from the server.
     *
     * @param mex The message received from the server.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the server is not bound.
     */
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


    /**
     * Sets the start card face.
     *
     * @param face The face of the start card.
     * @param client The client that represents the player.
     */
    @Override
    public void setStartCardFace(boolean face, CommonClient client) {
        SetStartCardFaceMessage msg = new SetStartCardFaceMessage(face, name);
        String gson = msg.MessageToJson();
        server.setStartCardFace(gson);
    }

    /**
     * Joins an existing game.
     *
     * @param name The name of the player.
     */
    @Override
    public void joinGame(String name) {
        JoinExistingGameMessage msg = new JoinExistingGameMessage(name);
        String gson = msg.MessageToJson();
        server.joinGame(gson);
    }
    /**
     * Creates an existing game.
     *
     * @param name The name of the player.
     * @param numPlayers The number of players.
     */
    @Override
    public void createGame(String name, int numPlayers) {
        CreateGameMessage msg = new CreateGameMessage(name, numPlayers);
        String gson = msg.MessageToJson();
        server.createGame(gson);
    }

    /**
     * Places a card on the playing field for this client.
     *
     * @param client The client who is placing the card.
     * @param whichInHand The index of the card in the client's hand.
     * @param x The x-coordinate on the playing field where the card is to be placed.
     * @param y The y-coordinate on the playing field where the card is to be placed.
     * @param face The face of the card.
     */
    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) {
        PlaceCardMessage msg = new PlaceCardMessage(name, whichInHand, x, y, face);
        String gson = msg.MessageToJson();
        server.placeCard(gson);
    }
    /**
     * Allows this client to choose a goal card.
     *
     * @param i The index of the goal card to choose.
     * @param client The client who is choosing the goal card.
     */
    @Override
    public void chooseGoalCard(int i, CommonClient client)  {
        ChooseGoalCardMessage msg = new ChooseGoalCardMessage(i, name);
        String gson = msg.MessageToJson();
        server.chooseGoalcard(gson);
    }

    /**
     * Sends a chat message.
     *
     * @param message The chat message to send.
     */
    @Override
    public void sendChatMessage(ChatMessage message) {
        ChatMessageMessage msg = new ChatMessageMessage(message, name);
        String gson = msg.MessageToJson();
        server.sendChatMessage(gson);
    }

    /**
     * Draws a card.
     *
     * @param i The index of the deck from which to draw the card.
     * @param whichOne The index of the card to draw.
     * @param client The client who is drawing the card.
     */
    @Override
    public void drawCard(int i, int whichOne, CommonClient client){
        DrawCardMessage msg = new DrawCardMessage(i, whichOne, name);
        String gson = msg.MessageToJson();
        server.drawCard(gson);
    }

    /**
     * Sets the view.
     *
     * @param view The view to set.
     */
    public void setView(GameView view){
        this.view = view;
    }

    /**
     * Gets the ClientModel of this client.
     *
     * @return The ClientModel.
     */
    @Override
    public ClientModel getClient() {
        return model;
    }

    /**
     * Ends the turn for this client.
     *
     * @param name The name of the player.
     * @param mex The message associated with the turn ending.
     */
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

    /**
     * Sets the name.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Allows this client to set their color.
     *
     * @param color The color to set.
     */
    @Override
    public void endColor(PlayerColor color) {
        EndColorMessage msg = new EndColorMessage(name, color);
        String gson = msg.MessageToJson();
        server.endColor(gson);
    }
}
