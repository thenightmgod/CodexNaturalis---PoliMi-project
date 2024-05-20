package it.polimi.ingsw.Network.Socket;

import com.google.gson.Gson;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.Messages.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.VirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public class SocketClientHandler implements VirtualView {
    final MainController controller;
    final ClientProxy proxy;
    final SocketServer server;
    final BufferedReader input;
    private GameController gc;
    private String name;

    public SocketClientHandler(MainController controller, SocketServer server, BufferedReader input, PrintWriter output){
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.proxy = new ClientProxy(output);
        this.gc = null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void runVirtualView() throws IOException{
        String receivedmessage;
        while(true) {
            try {
                while (input.readLine() != null) {
                    receivedmessage = input.readLine();
                    Gson gson = new Gson();
                    Message message = gson.fromJson(receivedmessage, Message.class);
                    //gestire le eccezioni di handleCommand in questo metodo
                    handleCommand(message);
                }
            }
            catch(RuntimeException | IOException e){
                e.printStackTrace();
            }
        }
    }
    public void handleCommand(Message msg) throws RoomFullException, RoomNotExistsException, NameAlreadyTakenException, WrongPlayersNumberException, WrongIndexException, RemoteException, RequirementsNotSatisfied, InvalidOperationException, WrongPositionException {
        switch(msg.getType()){
            case "JoinExistingGameMessage" -> {
                String name = ((JoinExistingGameMessage) msg).getName();
                this.gc = controller.joinGame(name, this); //capire come gestire i messaggi di errore e recapitarli al client giusto
            }
            case "CreateGameMessage" -> {
                String name = ((CreateGameMessage) msg).getName();
                int numPlayers = ((CreateGameMessage) msg).getNumPlayers();
                this.gc = controller.createGame(name, numPlayers, this );
            }
            case "LeaveGameMessage" -> {
                String name = ((LeaveGameMessage) msg).getName();
                HashMap<Integer, GameController> map = controller.getControllersPerGame();
                int k = gc.getRoomId();
                GameController useless = controller.leaveGame(name, k);
            }
            case "PlaceCardMessage" -> {
                String name = ((PlaceCardMessage) msg).getName();
                int x = ((PlaceCardMessage) msg).getX();
                int y = ((PlaceCardMessage) msg).getY();
                int whichInHand = ((PlaceCardMessage) msg).getWhichInHand();
                FB face = ((PlaceCardMessage) msg).getFace();
                gc.placeCard(whichInHand, x, y, face);
            }
            case "SetStartCardMessage" -> {
                //da fare dopo aver sistemato il metodo nel game controller
            }
            case "ChooseGoalCardMessage" -> {
                int i= ((ChooseGoalCardMessage)msg).getI();
                SocketClient client = ((ChooseGoalCardMessage)msg).castCommonToSocket(((ChooseGoalCardMessage) msg).getClient());
                String name = client.getName();
                Player p= gc.getPlayerByName(name);
                gc.chooseGoalCard(p,i);
            }
            case "DrawCardMessage" -> {
                int i= ((DrawCardMessage)msg).getI();
                int WhichOne= ((DrawCardMessage)msg).getWhichOne();
                gc.drawCard(i,WhichOne);
            }
        }
    }

    @Override
    public void showException(String details) throws RemoteException{
    }

    @Override
    public void updatePoints(int points, String name) throws RemoteException{
        UpdatePointsMessage message= new UpdatePointsMessage(points, name);
        String gson = message.MessagetoJson();
        proxy.updatePoints(gson);
    }
    @Override
    public void showGoals(LinkedList<GoalCard> goals) throws RemoteException{
        ShowGoalsMessage message= new ShowGoalsMessage(goals);
        String gson=message.MessageToJson();
        proxy.showGoals(gson);
    }

    @Override
    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException{
        ShowHandMessage message= new ShowHandMessage(hand);
        String gson=message.MessageToJson();
        proxy.showHand(gson);
    }

    @Override
    public void updateField(String name, PlayingField field) throws RemoteException{
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

    //probabilmente da togliere
    public void update() throws RemoteException{

    } //update il clientModel

    public void showOtherField(String player) throws RemoteException{
        ShowOtherFieldMessage message= new ShowOtherFieldMessage(player);
        String gson= message.MessageToJson();
        proxy.showOtherField(gson);
    }
}
