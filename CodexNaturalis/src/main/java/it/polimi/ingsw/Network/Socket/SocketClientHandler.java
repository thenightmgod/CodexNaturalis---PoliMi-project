package it.polimi.ingsw.Network.Socket;

import com.google.gson.Gson;
import it.polimi.ingsw.Actions.Actions;
import it.polimi.ingsw.Actions.LeaveAction;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
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
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.net.*;

public class SocketClientHandler extends Thread implements VirtualView {

    private final MainController controller;
    private final Socket clientSocket;
    private ClientProxy proxy;
    private final SocketServer server;
    private BufferedReader input;
    private final String name;


    public SocketClientHandler(MainController controller, SocketServer server, Socket socket) throws IOException {
        this.controller = controller;
        this.server = server;
        this.clientSocket = socket;
        this.name = "a";
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
                        Gson gson = new Gson();
                        Message message = gson.fromJson(receivedmessage, Message.class);
                        //gestire le eccezioni di handleCommand in questo metodo
                        handleCommand(message);
                    }
                } catch (RuntimeException | IOException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
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
                /*String name = ((JoinExistingGameMessage) msg).getName();
                controller.joinGame(name, this); //capire come gestire i messaggi di errore e recapitarli al client giusto*/
            }
            case "CreateGameMessage" -> {
                /*String name = ((CreateGameMessage) msg).getName();
                int numPlayers = ((CreateGameMessage) msg).getNumPlayers();
                controller.createGame(name, numPlayers, this);*/
            }
            case "LeaveGameMessage" -> {
                // VA MESSO
                /*
                String name = ((LeaveGameMessage) msg).getName();
                HashMap<Integer, GameController> map = controller.getControllersPerGame();
                GameController gc = findGameController(name);
                int k = gc.getRoomId();
                GameController useless = controller.leaveGame(name, k);*/
            }
            case "PlaceCardMessage" -> {
                String name = ((PlaceCardMessage) msg).getName();
                int x = ((PlaceCardMessage) msg).getX();
                int y = ((PlaceCardMessage) msg).getY();
                int whichInHand = ((PlaceCardMessage) msg).getWhichInHand();
                FB face = ((PlaceCardMessage) msg).getFace();
                GameController gc = findGameController(name);
                gc.placeCard(whichInHand, x, y, face);
            }
            case "SetStartCardMessage" -> {
                //VA MESSO
            }
            case "ChooseGoalCardMessage" -> {
                int i= ((ChooseGoalCardMessage)msg).getI();
                String name = ((ChooseGoalCardMessage)msg).getName();
                GameController gc = findGameController(name);
                gc.chooseGoalCard(name, i);
            }
            case "DrawCardMessage" -> {
                int i= ((DrawCardMessage)msg).getI();
                int WhichOne= ((DrawCardMessage)msg).getWhichOne();
                String name = ((DrawCardMessage)msg).getName();
                GameController gc = findGameController(name);
                gc.drawCard(i,WhichOne);
            }
            case "EndTurnMessage" -> {
                String name = ((EndTurnMessage)msg).getName();
                String mex = ((EndTurnMessage)msg).getMex();
                GameController gc = findGameController(name);
                gc.changeTurns(mex);
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
        /*int roomId = controller.getYourRoomId(client.getName());
        Actions lAction = new LeaveAction(client, controller, this, 2, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(lAction); */
    }

    /**
     * Sends a message to this client that they have left the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void leaveGameMessage() throws RemoteException {

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
     * @throws RemoteException If a remote access error occurs.
     */

    @Override
    public void notYourTurn(Player turn, String mex){
        /*NotYourTurnMessage message = new NotYourTurnMessage(turn);
        String gson = message.MessageToJson();
        proxy.notYourTurn(gson);*/
    }
    public void declareWinner(LinkedList<String> standings){
        DeclareWinnerMessage message = new DeclareWinnerMessage(standings);
        String gson = message.MessageToJson();
        proxy.declareWinner(gson);
    }


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

    public void showOtherField(String player) throws RemoteException {
        ShowOtherFieldMessage message= new ShowOtherFieldMessage(player);
        String gson= message.MessageToJson();
        proxy.showOtherField(gson);
    }


    public GameController findGameController(String name) throws RemoteException {
        GameController gc = null;
        /*LinkedList<GameController> gameControllers = controller.getControllers();
        for(GameController elem : gameControllers){
            Room curr = elem.getGame();
            LinkedList<Player> players = curr.getPlayers();
            for(Player p : players){
                if(p.getName().equals(name)){
                    return elem;
                }
            }
        }*/
        return gc;
    }

    public static void main(String[] args){
        int x = 4;
        System.out.println(4);
    }

}

