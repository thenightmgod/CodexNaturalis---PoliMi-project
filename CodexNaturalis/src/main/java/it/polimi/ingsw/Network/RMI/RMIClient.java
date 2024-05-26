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

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    private String name;
    private GameView view;
    private VirtualServer server; //in modo da chiamare tutti i metodi del server
    private Registry registry;
    private ClientModel model;


    //               FUNZIONI PER CREARE ED INIZIALIZZARE IL CLIENT RMI

    public RMIClient( String name) throws RemoteException, NotBoundException {
        this.model = new ClientModel(name);
        this.name = name;
        //andrà runnato this.runClient();
        initializeClient(name);
    }


    public void initializeClient(String name) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        Registry registry = LocateRegistry.getRegistry(666);
        this.server = (VirtualServer) registry.lookup(serverName);
    }

    public String getName(){
        return name;
    }

    public ClientModel getClient(){
        return model;
    }


    //                   FUNZIONI DEL COMMON CLIENT



    @Override
    public void setStartCardFace(boolean face, CommonClient client) throws RemoteException {
        server.setStartCardFace(face, this);
    }

    @Override
    public void leaveGame(String name, CommonClient client) throws RemoteException {
        server.leaveGame(name, this);
    }

    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) throws RemoteException {
        server.placeCard(this, whichInHand, x, y, face);
    }

    @Override
    public void chooseGoalCard(int i, CommonClient client) throws RemoteException {
        server.chooseGoalCard(i, this);
    }

    @Override
    public void drawCard(int whichDeck, int whichone, CommonClient client) throws RemoteException {
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
    public void checkGoals(String name) throws RemoteException {
        server.checkGoals(this);
    }

    @Override
    public void endTurn(String name) throws RemoteException {
        server.endTurn(this);
    }


    @Override
    public void joinGame(String Name) throws RemoteException {
        server.joinGame(name, this);
    }


    //    FUNZIONI DELLA VIRTUAL VIEW

    @Override
    public void updateTurn(Player p, boolean LL) throws RemoteException {
        this.view.updateTurn(p, LL);
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
    public void showStartCard(StartCard card) throws RemoteException {
        this.view.showStartCard(card);
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {
        this.view.updateGoals(goals, name);
    }

    @Override
    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {
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
    public void updateResourceDeck(String name, LinkedList<ResourceCard> deck) throws RemoteException {
        this.view.updateResourceDeck(deck, name);
    }

    @Override
    public void updateGoldDeck(String name, LinkedList<GoldCard> deck) throws RemoteException {
        this.view.updateGoldDeck(deck, name);
    }

    @Override
    public void update() throws RemoteException {

    }

    @Override
    public void showOtherField(String player) throws RemoteException {

    }


}
