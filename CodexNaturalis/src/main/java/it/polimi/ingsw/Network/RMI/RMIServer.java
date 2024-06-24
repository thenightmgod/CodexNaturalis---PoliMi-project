package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Actions.*;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.VirtualView;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

/**
 * The RMIServer class implements the VirtualServer interface and handles the server-side logic of the game.
 */
public class RMIServer implements VirtualServer {

    final MainController controller;
    int port;
    ExecutorService executorService;
    ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame;
    PriorityBlockingQueue<Actions> joins;

    /**
     * Constructor for the RMIServer class.
     *
     * @param controller The main controller of the game.
     * @throws RemoteException If a remote access error occurs.
     */
    public RMIServer(MainController controller, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins) throws RemoteException {
        this.controller = controller;
        this.joins = joins;
        port =  49666;
        this.actionsPerGame = actionsPerGame;

    }

    /**
     * Starts the server and binds it to a registry.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    public void startServer() throws RemoteException { //in realtà le eccezioni dovremmo gestirle decentemente

        final String serverName = "CodexServer";
        Registry registry = null;
        VirtualServer stub = null;

        stub = (VirtualServer) UnicastRemoteObject.exportObject(this, port);
        registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName, stub);
        System.out.println("Server bound.");
    }

    /**
     * Allows a player to join a game.
     *
     * @param Name The name of the player.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void joinGame(String Name, VirtualView client) throws RemoteException {
        System.err.println("joinGame");
        synchronized (actionsPerGame) {
            int roomId = controller.getControllers().size() - 1;
            if (roomId < 0) {
                actionsPerGame.put(0, new MultipleFlow());
                Actions jGame = new JoinAction(client, controller, Name, 1, 0);
                joins.add(jGame);
            } else {
                Actions jGame = new JoinAction(client, controller, Name, 1, roomId);
                joins.add(jGame);

            }
        }
    }

    /**
     * Allows a player to create a game.
     *
     * @param Name The name of the player.
     * @param numPlayers The number of players in the game.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void createGame(String Name, int numPlayers, VirtualView client) throws RemoteException {
        synchronized (actionsPerGame) {
            int roomId = controller.getControllers().size();
            if (roomId > 0) {
                actionsPerGame.put(roomId, new MultipleFlow());
                Actions cGame = new CreateAction(numPlayers, client, controller, Name, 0, roomId);
                joins.add(cGame);
            } else {
                Actions cGame = new CreateAction(numPlayers, client, controller, Name, 0, 0);
                joins.add(cGame);
            }
        }
    }

    /**
     * Allows a player to leave a game.
     *
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void leaveGame(VirtualView client) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getNames());
        Actions lAction = new LeaveAction(client, controller, 2, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(lAction);
    }

    /**
     * Allows a player to place a card.
     *
     * @param client The client that represents the player.
     * @param whichInHand The index of the card in hand.
     * @param x The x-coordinate of the placement.
     * @param y The y-coordinate of the placement.
     * @param face The face of the card.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getNames());
        Actions pAction = new PlaceCardAction(controller, client, whichInHand, x, y, face, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(pAction);
    }

    /**
     * Allows a player to set the face of the start card.
     *
     * @param face The face of the start card.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setStartCardFace(boolean face, VirtualView client) throws RemoteException { //ordine initialize game tutto gestito nella view
        int roomId = controller.getYourRoomId(client.getNames());
        Actions ssAction = new SetStartCardFaceAction(face, client, controller, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(ssAction);
    }

    /**
     * Allows a player to choose a goal card.
     *
     * @param i The index of the goal card.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void chooseGoalCard(int i, VirtualView client) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getNames());
        Actions cgAction = new ChooseGoalCardAction(i, client, controller, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(cgAction);
    }

    /**
     * Allows a player to draw a card.
     *
     * @param i The index of the deck.
     * @param whichOne The index of the card in the deck.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void drawCard(int i, int whichOne, VirtualView client) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getNames());
        Actions dAction = new DrawCardAction(i, whichOne, client, controller, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(dAction);
    }

    /**
     * Allows a player to end their turn.
     *
     * @param client The client that represents the player.
     * @param mex The message to be displayed when the turn ends.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void endTurn(VirtualView client, String mex) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getNames());
        Actions eAction = new EndTurnAction(client, controller, mex, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(eAction);
    }


}
