package it.polimi.ingsw.Network.Socket;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import it.polimi.ingsw.Actions.*;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.CornerPackage.CardRes;
import it.polimi.ingsw.Model.CornerPackage.CardResDeserializer;
import it.polimi.ingsw.Model.CornerPackage.CardResSerializer;
import it.polimi.ingsw.Model.Messages.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Model.RoomPackage.Room;
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

public class SocketClientHandler extends Thread implements VirtualView {

    private final MainController controller;
    private final Socket clientSocket;
    private ClientProxy proxy;
    private final SocketServer server;
    private BufferedReader input;
    private String name;
    ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame;
    PriorityBlockingQueue<Actions> joins;

    public SocketClientHandler(MainController controller, SocketServer server, Socket socket, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins) throws IOException {
        this.controller = controller;
        this.actionsPerGame = actionsPerGame;
        this.joins = joins;
        this.server = server;
        this.clientSocket = socket;
    }

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
                                .create();
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(receivedmessage).getAsJsonObject();
                        String type = jsonObject.get("type").getAsString();
                        Class<?> clazz = Class.forName("it.polimi.ingsw.Model.Messages." + type);
                        Message message = gson.fromJson(receivedmessage, (Type) clazz);                        //gestire le eccezioni di handleCommand in questo metodo
                        handleCommand(message);
                    }
                } catch (RuntimeException | IOException e) {
                    e.printStackTrace();
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
    public void handleCommand(Message msg) throws RemoteException, NotBoundException {
        switch(msg.getType()){
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
                System.err.println("Place Card Action: whichInHand-" + whichInHand + " X: " + x + " Y: " + y + " face" + face + " Player-" + name);
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

    @Override
    public void twenty(String name){
        TwentyMessage message = new TwentyMessage(name);
        String gson = message.MessageToJson();
        proxy.twenty(gson);
    }
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
    public void isAlivee() throws RemoteException {

    }

    /**
     * Leaves the game to this client that they have left the game.
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
     * Sends a message to this client that they have left the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void leaveGameMessage() throws RemoteException {
        LeaveGameMessage message = new LeaveGameMessage(name);
        String gson = message.MessageToJson();
        proxy.leaveGameMessage(gson);
    }

    @Override
    public void showException(String exception, String details) throws RemoteException{
        ExceptionMessage message= new ExceptionMessage(exception, details);
        String gson = message.MessageToJson();
        proxy.showException(gson);
    }


    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException {
        UpdatePointsMessage message= new UpdatePointsMessage(points, name);
        String gson = message.MessageToJson();
        proxy.updatePoints(gson);
    }

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

    @Override
    public void declareWinner(LinkedList<String> standings){
        DeclareWinnerMessage message = new DeclareWinnerMessage(standings);
        String gson = message.MessageToJson();
        proxy.declareWinner(gson);
    }

    @Override
    public String getNames() throws RemoteException {
        return this.name;
    }

    @Override
    public void startingGame(Player p){
        StartingGameMessage message = new StartingGameMessage();
        String gson = message.MessageToJson();
        proxy.startingGame(gson);
    }

    @Override
    public void showStartCard(StartCard card){
        ShowStartCardMessage message = new ShowStartCardMessage(card);
        String gson = message.MessageToJson();
        proxy.showStartCard(gson);
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {
        ShowGoalsMessage message= new ShowGoalsMessage(goals, name);
        String gson = message.MessageToJson();
        proxy.showGoals(gson);
    }

    @Override
    public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException{
        UpdateCommonGoalsMessage message = new UpdateCommonGoalsMessage(goals, name);
        String gson = message.MessageToJson();
        proxy.updateCommonGoals(gson);
    }

    @Override
    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {
        ShowHandMessage message= new ShowHandMessage(hand, name);
        String gson = message.MessageToJson();
        proxy.showHand(gson);
    }

    @Override
    public void updateField(String name, PlayingField field) throws RemoteException {
        UpdateFieldMessage message= new UpdateFieldMessage(name,field);
        String gson=message.MessageToJson();
        proxy.updateField(gson);
    }

    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {
        ShowFreePositionsMessage message = new ShowFreePositionsMessage(name, freePosition);
        String gson = message.MessageToJson();
        proxy.showFreePositions(gson);
    }

    @Override
    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck){
        UpdateGoldDeckMessage message = new UpdateGoldDeckMessage(name, start, deck);
        String gson = message.MessageToJson();
        proxy.updateGoldDeck(gson);
    }

    @Override
    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck){
        UpdateResourceDeckMessage message = new UpdateResourceDeckMessage(name, start, deck);
        String gson = message.MessageToJson();
        proxy.updateResourceDeck(gson);
    }

    //probabilmente da togliere
    public void update() {

    } //update il clientModel

    @Override
    public void showOtherField(String player) throws RemoteException {

    }

}

