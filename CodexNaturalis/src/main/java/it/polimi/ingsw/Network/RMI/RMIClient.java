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
import java.util.LinkedList;
import java.util.Objects;

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    private String name;
    private GameView view;
    private VirtualServer server; //in modo da chiamare tutti i metodi del server
    private Registry registry;
    private ClientModel model;


    //               FUNZIONI PER CREARE ED INIZIALIZZARE IL CLIENT RMI

    public RMIClient(String name, String serverIp) throws RemoteException, NotBoundException {
        this.model = new ClientModel(name);
        this.name = name;
        //andrà runnato this.runClient();
        initializeClient(serverIp);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void initializeClient(String serverIp) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        registry = LocateRegistry.getRegistry(serverIp, 49666);
        this.server = (VirtualServer) registry.lookup(serverName);
    }

    public String getName(){
        return name;
    }

    public ClientModel getClient(){
        return model;
    }

    public VirtualServer getServer() {
        return server;
    }

    //                   FUNZIONI DEL COMMON CLIENT



    @Override
    public void setStartCardFace(boolean face, CommonClient client) {
        try {
            server.setStartCardFace(face, this);
        } catch(RemoteException ignored) {}
    }

    @Override
    public void leaveGame() {
        try {
            server.leaveGame(this);
        } catch(RemoteException ignored) {}
    }

    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) {
        try {
            server.placeCard(this, whichInHand, x, y, face);
        } catch(RemoteException ignored) {}
    }

    @Override
    public void chooseGoalCard(int i, CommonClient client) throws RemoteException {
        try {
            server.chooseGoalCard(i, this);
        } catch(RemoteException ignored) {}
    }

    @Override
    public void drawCard(int whichDeck, int whichone, CommonClient client) {
        try {
            server.drawCard(whichDeck, whichone, this);
        } catch(RemoteException ignored) {}
    }

    @Override
    public void createGame(String Name, int numPlayers) {
        try {
            server.createGame(Name, numPlayers, this);
        } catch(RemoteException ignored) {}
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }

    @Override
    public void endTurn(String name, String mex){
        try {
            server.endTurn(this, mex);
        } catch (RemoteException ignored) {}
    }


    @Override
    public void joinGame(String name) throws RemoteException {
        try {
            server.joinGame(name, this);
        } catch (RemoteException ignored) {}
    }


    //    FUNZIONI DELLA VIRTUAL VIEW

    @Override
    public void twenty(String name) {
        this.view.twenty(name);

    }

    @Override
    public void lastRound() {
        this.view.lastRound();
    }

    @Override
    public void updateTurn(Player p, String mex) {
        try {
            this.view.updateTurn(p, mex);
        } catch (RemoteException ignored) {}
    }

    public void notYourTurn(Player turn) {
        this.view.printNotYourTurn(turn);
    }

    @Override
    public void showException(String exception, String details) throws NotBoundException {
        try {
            this.view.showException(exception, details);
        } catch (RemoteException ignored) {}
    }

    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) {
       // model.setPointsCounter(points);
        this.view.updatePoints(points, name);
    }

    @Override
    public void showStartCard(StartCard card) {
        try {
            this.view.showStartCard(card);
        } catch (RemoteException ignored) {}
    }

    @Override
    public void startingGame(Player p) {
        try {
            this.view.startingGame();
        } catch (RemoteException ignored) {}
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals) {
        try {
            this.view.updateGoals(goals, name);
        } catch (RemoteException ignored) {}

    }

    public void updateCommonGoals(LinkedList<GoalCard> goals) {
        try {
            this.view.updateCommonGoals(goals, name);
        } catch (RemoteException ignored) {}
    }
    @Override
    public void showHand(LinkedList<PlayableCard> hand) {
        this.view.updateHands(hand, name);
        //farla, forse sbatti con playablecard
    }

    @Override
    public void updateField(String name, PlayingField field) {
        this.view.updateField(field, name);
    }

    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) {
        this.view.updateFreePosition(name, freePosition);
    }

    @Override
    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) {
        this.view.updateResourceDeck(deck, start, name);
    }

    @Override
    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) {
        this.view.updateGoldDeck(deck, start, name);
    }

    @Override
    public void declareWinner(LinkedList<String> standings) {
        this.view.declareWinner(standings);
    }

    @Override
    public void update() throws RemoteException {

    }

    @Override
    public void showOtherField(String player) {

    }

    @Override
    public void isAlive() throws RemoteException{

    }

    @Override
    public void leaveGameMessage() throws RemoteException {
        this.view.leaveGameMessage();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VirtualView that = (VirtualView) obj;
        try {
            return Objects.equals(getName(), that.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }


}
