package it.polimi.ingsw.Network.Socket;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import it.polimi.ingsw.Actions.*;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.CardRes;
import it.polimi.ingsw.Model.CornerPackage.CardResDeserializer;
import it.polimi.ingsw.Model.CornerPackage.CardResSerializer;
import it.polimi.ingsw.Model.Messages.*;
import it.polimi.ingsw.Model.PlayerPackage.*;
import it.polimi.ingsw.Network.RMI.MultipleFlow;
import it.polimi.ingsw.Network.VirtualView;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
/**
 * The SocketClientHandler class extends Thread and represents a handler for a client connected via a socket.
 */
public class SocketClientHandler extends Thread implements VirtualView {
    /**
     * The main controller of the game.
     */
    private final MainController controller;
    /**
     * The client's socket.
     */
    private final Socket clientSocket;
    /**
     * The client's proxy.
     */
    private ClientProxy proxy;
    /**
     * The server.
     */
    private final SocketServer server;
    /**
     * The client's input.
     */
    private BufferedReader input;
    /**
     * The client's name.
     */
    private String name;
    /**
     * The map of multiple flows of actions in the game.
     */
    ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame;
    /**
     * The queue of actions to be executed (only for CreateGameAction and JoinGameAction).
     */
    PriorityBlockingQueue<Actions> joins;
    /**
     * Constructor for the SocketClientHandler class.
     *
     * @param controller The main controller of the game.
     * @param server The server.
     * @param socket The client's socket.
     * @param actionsPerGame The map of multiple flows of actions in the game.
     * @param joins The queue of actions to be executed (only for CreateGameAction and JoinGameAction).
     * @throws IOException If an I/O error occurs.
     */
    public SocketClientHandler(MainController controller, SocketServer server, Socket socket, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins) throws IOException {
        this.controller = controller;
        this.actionsPerGame = actionsPerGame;
        this.joins = joins;
        this.server = server;
        this.clientSocket = socket;
    }
    /**
     * Runs the client handler.
     */
    @Override
    public void run(){
        try {
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.proxy = new ClientProxy(new PrintWriter(clientSocket.getOutputStream(), true));

            String receivedmessage;
            while (true) {
                try {
                    while ((receivedmessage = input.readLine()) != null) {
                        Gson gson = new GsonBuilder().
                                registerTypeAdapter(CardRes.class, new CardResDeserializer())
                                .registerTypeAdapter(CardRes.class, new CardResSerializer())
                                .registerTypeAdapter(PlayingField.class, new PlayingFieldAdapter())
                                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                                .create();
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(receivedmessage).getAsJsonObject();
                        String type = jsonObject.get("type").getAsString();
                        Class<?> clazz = Class.forName("it.polimi.ingsw.Model.Messages." + type);
                        Message message = gson.fromJson(receivedmessage, (Type) clazz);                        //gestire le eccezioni di handleCommand in questo metodo
                        handleCommand(message);
                    }
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch (IOException e){
            System.out.println("Error in Socket connection");
        }
    }
    /**
     * Checks if the client is connected.
     *
     * @return True if the client is connected, false otherwise.
     */
    public boolean isClientConnected() {
        try {
            clientSocket.sendUrgentData(0xFF);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * Handles a command received from the client.
     *
     * @param msg The message received from the client.
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If the specified name is not currently bound.
     */
    public void handleCommand(Message msg) throws RemoteException, NotBoundException {
        switch(msg.getType()){
            case "EndColorMessage" -> {
                String name = ((EndColorMessage) msg).getName();
                PlayerColor color = ((EndColorMessage) msg).getColor();
                int roomId = controller.getYourRoomId(name);
                Actions eColor = new EndColorAction(this, controller, 0, color, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(eColor);
            }
            case "ChatMessageMessage" ->{
                String name = ((ChatMessageMessage) msg).getName();
                ChatMessage message = ((ChatMessageMessage) msg).getMessage();
                int roomId = controller.getYourRoomId(name);
                Actions cAction = new ChatMessageAction(this, controller, 0, message, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(cAction);
            }
            case "JoinExistingGameMessage" -> {
                name = ((JoinExistingGameMessage) msg).getName();
                synchronized (actionsPerGame) {
                    int roomId = controller.getControllers().size() - 1;
                    if (roomId < 0) {
                        actionsPerGame.put(0, new MultipleFlow());
                        Actions jGame = new JoinAction(this, controller, name, 1, 0);
                        joins.add(jGame);
                    } else {
                        Actions jGame = new JoinAction(this, controller, name, 1, roomId);
                        joins.add(jGame);

                    }
                }
            }
            case "CreateGameMessage" -> {
                String name = ((CreateGameMessage) msg).getName();
                int numPlayers = ((CreateGameMessage) msg).getNumPlayers();
                synchronized (actionsPerGame) {
                    int roomId = controller.getControllers().size();
                    if (roomId > 0) {
                        actionsPerGame.put(roomId, new MultipleFlow());
                        Actions cGame = new CreateAction(numPlayers, this, controller, name, 0, roomId);
                        joins.add(cGame);
                    } else {
                        Actions cGame = new CreateAction(numPlayers, this, controller, name, 0, 0);
                        joins.add(cGame);
                    }
                    this.name = name;
                }
            }
            case "PlaceCardMessage" -> {
                String name = ((PlaceCardMessage) msg).getName();
                int x = ((PlaceCardMessage) msg).getX();
                int y = ((PlaceCardMessage) msg).getY();
                int whichInHand = ((PlaceCardMessage) msg).getWhichInHand();
                FB face = ((PlaceCardMessage) msg).getFace();
                int roomId = controller.getYourRoomId(name);
                Actions pAction = new PlaceCardAction(controller, this, whichInHand, x, y, face, 0, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(pAction);
            }
            case "SetStartCardFaceMessage" -> {
                String name = ((SetStartCardFaceMessage) msg).getName();
                boolean face = ((SetStartCardFaceMessage) msg).getFace();
                int roomId = controller.getYourRoomId(name);
                Actions ssAction = new SetStartCardFaceAction(face, this, controller, 0, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(ssAction);
            }
            case "ChooseGoalCardMessage" -> {
                int i= ((ChooseGoalCardMessage)msg).getI();
                String name = ((ChooseGoalCardMessage)msg).getName();
                int roomId = controller.getYourRoomId(name);
                Actions cgAction = new ChooseGoalCardAction(i, this, controller, 0, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(cgAction);
            }
            case "DrawCardMessage" -> {
                int i= ((DrawCardMessage)msg).getI();
                int whichOne= ((DrawCardMessage)msg).getWhichOne();
                String name = ((DrawCardMessage)msg).getName();
                int roomId = controller.getYourRoomId(name);
                Actions dAction = new DrawCardAction(i, whichOne, this, controller, 0, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(dAction);
            }
            case "EndTurnMessage" -> {
                String name = ((EndTurnMessage)msg).getName();
                String mex = ((EndTurnMessage)msg).getMex();
                int roomId = controller.getYourRoomId(name);
                Actions eAction = new EndTurnAction(this, controller, mex, 0, roomId);
                actionsPerGame.get(roomId).getActionsQueue().add(eAction);
            }
        }
    }
    /**
     * Notifies the client when the 20 points are achieved by someone
     *
     * @param name the name of the player that does it
     */
    @Override
    public void twenty(String name){
        TwentyMessage message = new TwentyMessage(name);
        String gson = message.MessageToJson();
        proxy.twenty(gson);
    }
    /**
     * Notifies the clients when the last round begins
     */
    @Override
    public void lastRound(){
        LastRoundMessage message = new LastRoundMessage();
        String gson = message.MessageToJson();
        proxy.lastRound(gson);
    }

    /**
     * Checks if this client is alive.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void isAlivee() throws IOException {
        IsAliveMessage message = new IsAliveMessage(name, "I'm alive");
        String gson = message.MessageToJson();
        proxy.isAlivee(gson);
    }

    /**
     * Allows this client to leave the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void leaveGame() throws RemoteException {
        int roomId = controller.getYourRoomId(name);
        Actions lAction = new LeaveAction(this, controller, 2, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(lAction);
    }

    /**
     * Sends a message to this client that someone has left the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void leaveGameMessage() throws RemoteException {
        LeaveGameMessage message = new LeaveGameMessage();
        String gson = message.MessageToJson();
        proxy.leaveGameMessage(gson);
    }

    /**
     * Updates the chat for this client.
     *
     * @param name The name of the client.
     * @param chat The updated chat messages.
     */
    @Override
    public void updateChat(String name, LinkedList<ChatMessage> chat) {
        UpdateChatMessage message = new UpdateChatMessage(name, chat);
        String gson = message.MessageToJson();
        proxy.updateChat(gson);
    }

    /**
     * Sends the list of players to this client.
     *
     * @param players The list of players to be sent.
     */
    @Override
    public void sendPlayers(LinkedList<String> players){
        SendPlayerMessage message = new SendPlayerMessage(players);
        String gson = message.MessageToJson();
        proxy.sendPlayers(gson);
    }

    /**
     * Shows an exception to this client.
     *
     * @param exception The exception to be shown.
     * @param details The details of the exception.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void showException(String exception, String details) throws RemoteException{
        ExceptionMessage message= new ExceptionMessage(exception, details);
        String gson = message.MessageToJson();
        proxy.showException(gson);
    }

    /**
     * Updates the points for this client.
     *
     * @param points The points to be updated.
     * @param name The name of the client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException {
        UpdatePointsMessage message= new UpdatePointsMessage(points, name);
        String gson = message.MessageToJson();
        proxy.updatePoints(gson);
    }

    /**
     * Updates the turn for this client.
     *
     * @param turn The player whose turn it is.
     * @param mex The message to be displayed when the turn updates.
     */
    @Override
    public void updateTurn(Player turn, String mex) throws RemoteException {
        UpdateTurnMessage message = new UpdateTurnMessage(turn, mex);
        String gson = message.MessageToJson();
        proxy.updateTurn(gson);
    }

    /**
     * Notifies a player that it's not their turn.
     *
     * @param turn The player to be notified.
     * @param mex  The message to be displayed.
     */

    @Override
    public void notYourTurn(Player turn, String mex){
        NotYourTurnMessage message = new NotYourTurnMessage(turn, mex);
        String gson = message.MessageToJson();
        proxy.notYourTurn(gson);
    }

    /**
     * Declares the winner to this client.
     *
     * @param standings The final standings of the game.
     */
    @Override
    public void declareWinner(LinkedList<String> standings){
        DeclareWinnerMessage message = new DeclareWinnerMessage(standings);
        String gson = message.MessageToJson();
        proxy.declareWinner(gson);
    }

    /**
     * Gets the name of this client.
     *
     * @return The name of this client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public String getNames() throws RemoteException {
        return this.name;
    }

    /**
     * Notifies this client that the game is starting.
     *
     * @param p The player who is starting the game.
     */
    @Override
    public void startingGame(Player p){
        StartingGameMessage message = new StartingGameMessage();
        String gson = message.MessageToJson();
        proxy.startingGame(gson);
    }

    /**
     * Shows the start card to this client.
     *
     * @param card The start card to show.
     */
    @Override
    public void showStartCard(StartCard card){
        ShowStartCardMessage message = new ShowStartCardMessage(card);
        String gson = message.MessageToJson();
        proxy.showStartCard(gson);
    }

    /**
     * Updates the goals for this client.
     *
     * @param goals The goals to be updated.
     */
    @Override
    public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {
        LinkedList<Integer> goalIds= new LinkedList<>();
        for(int i=0; i<goals.size(); i++){
            goalIds.add(goals.get(i).getId());
        }
        ShowGoalsMessage message= new ShowGoalsMessage(goalIds, name);
        String gson = message.MessageToJson();
        proxy.showGoals(gson);
    }

    /**
     * Updates the common goals for this client.
     *
     * @param goals The common goals to be updated.
     */
    @Override
    public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException{
        LinkedList<Integer> goalIds = new LinkedList<>();
        for(int i=0; i<goals.size(); i++){
            goalIds.add(goals.get(i).getId());
        }
        UpdateCommonGoalsMessage message = new UpdateCommonGoalsMessage(goalIds, name);
        String gson = message.MessageToJson();
        proxy.updateCommonGoals(gson);
    }

    /**
     * Shows the hand to this client.
     *
     * @param hand The hand to be shown.
     */
    @Override
    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {
        ShowHandMessage message= new ShowHandMessage(hand, name);
        String gson = message.MessageToJson();
        proxy.showHand(gson);
    }

    /**
     * Updates the playing field for this client.
     *
     * @param name The name of the client.
     * @param field The playing field to be updated.
     */
    @Override
    public void updateField(String name, PlayingField field) throws RemoteException {
        UpdateFieldMessage message= new UpdateFieldMessage(name,field);
        String gson=message.MessageToJson();
        proxy.updateField(gson);
    }

    /**
     * Shows the free positions to this client.
     *
     * @param name The name of the client.
     * @param freePosition The free positions to show.
     */
    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {
        ShowFreePositionsMessage message = new ShowFreePositionsMessage(name, freePosition);
        String gson = message.MessageToJson();
        proxy.showFreePositions(gson);
    }

    /**
     * Updates the gold deck for this client.
     *
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The gold deck to be updated.
     */
    @Override
    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck){
        UpdateGoldDeckMessage message = new UpdateGoldDeckMessage(name, start, deck);
        String gson = message.MessageToJson();
        proxy.updateGoldDeck(gson);
    }

    /**
     * Updates the resource deck for this client.
     *
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The resource deck to be updated.
     */
    @Override
    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck){
        UpdateResourceDeckMessage message = new UpdateResourceDeckMessage(name, start, deck);
        String gson = message.MessageToJson();
        proxy.updateResourceDeck(gson);
    }



    /**
     * Updates the colors for this client.
     *
     * @param turn The player whose turn it is.
     * @param colors The colors updated.
     */
    @Override
    public void updateColors(Player turn, LinkedList<PlayerColor> colors) throws RemoteException {
        UpdateColorsMessage message = new UpdateColorsMessage(turn, colors);
        String gson = message.MessageToJson();
        proxy.updateColors(gson);
    }


}

