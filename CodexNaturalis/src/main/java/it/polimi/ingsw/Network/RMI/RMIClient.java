package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.View.TUI.ClientModel;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.VirtualView;
import it.polimi.ingsw.View.GameView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    private String name;
    private GameView view;
    private VirtualServer server; //in modo da chiamare tutti i metodi del server
    private Registry registry;
    private ClientModel model;


    //               FUNZIONI PER CREARE ED INIZIALIZZARE IL CLIENT RMI

    public RMIClient(String name) throws RemoteException, NotBoundException {
        this.model = new ClientModel(name);
        this.name = name;
        //andrà runnato this.runClient();
        initializeClient(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initializeClient(String name) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        /*
        registry = LocateRegistry.getRegistry("localhost", 4446);
        VirtualServer stub = (VirtualServer) registry.lookup(serverName);
        server = (VirtualServer) UnicastRemoteObject.exportObject(stub, 4446);*/

        registry = LocateRegistry.getRegistry("localhost", 4446);
        this.server = (VirtualServer) registry.lookup(serverName);

    }

    public String getName(){
        return name;
    }
    @Override
    public ClientModel getClient(){
        return model;
    }


    //                   FUNZIONI DEL COMMON CLIENT



    @Override
    public void setStartCardFace(boolean face, String name) throws RemoteException {
        server.setStartCardFace(face, this);
    }

    @Override
    public void leaveGame(String name) throws RemoteException {
        server.leaveGame(name, this);
    }

    @Override
    public void placeCard(int whichInHand, int x, int y, FB face) throws RemoteException {
        server.placeCard(this, whichInHand, x, y, face);
    }

    @Override
    public void chooseGoalCard(int i, String name) throws RemoteException {
        server.chooseGoalCard(i, this);
    }

    @Override
    public void drawCard(int whichDeck, int whichone, String name) throws RemoteException {
        server.drawCard(whichDeck, whichone, this);
    }

    @Override
    public void createGame(String Name, int numPlayers) throws RemoteException {
        server.createGame(Name, numPlayers, this);
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }

    @Override
    public void endTurn(String name, String mex) throws RemoteException {
        server.endTurn(this, mex);
    }


    @Override
    public void joinGame(String name) throws RemoteException {
        server.joinGame(name, this);
    }


    //    FUNZIONI DELLA VIRTUAL VIEW

    @Override
    public void twenty(String name) throws RemoteException {
        this.view.twenty(name);
    }

    @Override
    public void lastRound() throws RemoteException {
        this.view.lastRound();
    }

    @Override
    public void updateTurn(Player p, String mex) throws RemoteException {
        this.view.updateTurn(p, mex);
    }

    public void notYourTurn(Player turn) throws RemoteException {
        this.view.printNotYourTurn(turn);
    }

    @Override
    public void showException(String exception, String details) throws RemoteException, NotBoundException {
        this.view.showException(exception, details);
    }

    @Override
    public void updatePoints(int points, String name) throws RemoteException {
        //model.setPointsCounter(points);
        this.view.updatePoints(points, name);
    }

    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        this.view.showStartCard(card);
    }

    @Override
    public void startingGame(Player p) throws RemoteException {
        this.view.startingGame();
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        this.view.updateGoals(goals, name);
    }

    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        this.view.updateCommonGoals(goals, name);
    }
    @Override
    public void showHand(LinkedList<PlayableCard> hand, String name) throws RemoteException {
        this.view.updateHands(hand, name);
        //farla, forse sbatti con playablecard
    }

    @Override
    public void updateField(String name, PlayingField field) throws RemoteException {
        this.view.updateField(field, name);
    }

    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {
        this.view.updateFreePosition(name, freePosition);
    }

    @Override
    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {
        this.view.updateResourceDeck(deck, start, name);
    }

    @Override
    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {
        this.view.updateGoldDeck(deck, start, name);
    }

    @Override
    public void declareWinner(LinkedList<String> standings) throws RemoteException {
        this.view.declareWinner(standings);
    }

    @Override
    public void update() throws RemoteException {

    }

    @Override
    public void showOtherField(String player) throws RemoteException {

    }


}