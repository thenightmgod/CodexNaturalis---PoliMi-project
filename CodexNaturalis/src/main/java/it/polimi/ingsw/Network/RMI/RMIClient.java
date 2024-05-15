package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.ClientModel;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.View.GameView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    private String name;
    private GameView view;
    private VirtualServer server; //in modo da chiamare tutti i metodi del server
    private Registry registry;
    private ClientModel model;


    public RMIClient(VirtualServer server, String name) throws RemoteException{
        this.model = new ClientModel(name);
        this.name = name;
        this.server = server;
        //andrà runnato this.runClient();
    }


    public void initializeClient(String name) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        Registry registry = LocateRegistry.getRegistry(666);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);
    }

    public void placeCard(int whichInHand, int x, int y, FB face) throws WrongIndexException{
        server.placeCard(this, whichInHand, x, y, face);
    };

    @Override
    public void setStartCardFace(boolean face, VirtualView client){
        server.setStartCardFace(face, this);
    }

    @Override
    public void leaveGame(String name, VirtualView client){
        server.leaveGame(name, this);
    }

    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws WrongIndexException {

    }

    @Override
    public void chooseGoalCard(int i, VirtualView client) throws WrongIndexException{
        server.chooseGoalCard(i, this);
    };

    @Override
    public void createGame(String Name, PlayerColor color, int numPlayers) throws WrongPlayersNumberException, RemoteException {
        server.createGame(Name, color, numPlayers);
    };

    @Override
    public void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException,
            RemoteException, NameAlreadyTakenException {
        server.joinGame(Name, color);
    }

    public void drawCard(int whichDeck, int whichOne) throws WrongIndexException, RemoteException {
        server.drawCard(whichDeck, whichOne, this);
    }

    @Override
    public void showException(String details) throws RemoteException {
        this.view.showException(name, details);
    }

    @Override
    public void updatePoints(int points, String name) throws RemoteException {
        //model.setPointsCounter(points);
        this.view.updatePoints(points, name);
    }

    @Override
    public void showGoals(LinkedList<GoalCard> goals) throws RemoteException {
        this.view.showGoals(goals, name);
    }

    @Override
    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {
        this.view.showHands(hand, name);
        //farla, forse sbatti con playablecard
    }

    @Override
    public void updateField(String name, PlayingField field) throws RemoteException {
        this.view.updateField(field, name);
    }

    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {
        this.view.showFreePosition(name, freePosition);
    }

    @Override
    public void update() throws RemoteException {

    }

    @Override
    public void showOtherField(String player) throws RemoteException {

    }


}
