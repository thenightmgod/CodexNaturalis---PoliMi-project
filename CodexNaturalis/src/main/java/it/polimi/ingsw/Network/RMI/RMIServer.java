package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Actions.*;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.VirtualView;

import java.util.LinkedList;
import java.util.Map.Entry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.*;

public class RMIServer implements VirtualServer {

    final MainController controller;

    //come perbacco aggiungo un client alla mappa clients se non riesco a tenere conto
    //del numero della room?
    int port;
    ExecutorService executorService;
    final ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame;
    PriorityBlockingQueue<Actions> joins = new PriorityBlockingQueue<>();

    public RMIServer(MainController controller) throws RemoteException {
        this.controller = controller;
        port =  49666;
        actionsPerGame = new ConcurrentHashMap<>();
        executeGame();
        executeStart();
    }

    public void startServer() throws RemoteException { //in realtà le eccezioni dovremmo gestirle decentemente

        final String serverName = "CodexServer";
        Registry registry = null;
        VirtualServer stub = null;

        stub = (VirtualServer) UnicastRemoteObject.exportObject(this, port);
        registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName, stub);
        System.out.println("Server buond.");
    }


    @Override
    public void joinGame(String Name, VirtualView client) throws RemoteException {
        synchronized (actionsPerGame) {
            int roomId = controller.getControllers().size() - 1;
            if (roomId < 0) {
                actionsPerGame.put(0, new MultipleFlow());
                Actions jGame = new JoinAction(client, controller, Name, this, 1, 0);
                joins.add(jGame);
            } else {
                Actions jGame = new JoinAction(client, controller, Name, this, 1, roomId);
                joins.add(jGame);

            }
        }
    }

    @Override
    public void createGame(String Name, int numPlayers, VirtualView client) throws RemoteException {
        synchronized (actionsPerGame) {
            int roomId = controller.getControllers().size();
            if (roomId > 0) {
                actionsPerGame.put(roomId, new MultipleFlow());
                Actions cGame = new CreateAction(numPlayers, client, controller, Name, this, 0, roomId);
                joins.add(cGame);
            } else {
                Actions cGame = new CreateAction(numPlayers, client, controller, Name, this, 0, 0);
                joins.add(cGame);
            }
        }
    }

    @Override
    public void leaveGame(VirtualView client) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getName());
        Actions lAction = new LeaveAction(client, controller, this, 2, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(lAction);
    }

    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getName());
        Actions pAction = new PlaceCardAction(controller, client, whichInHand, x, y, face, this, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(pAction);
    }

    @Override
    public void setStartCardFace(boolean face, VirtualView client) throws RemoteException { //ordine initialize game tutto gestito nella view
        int roomId = controller.getYourRoomId(client.getName());
        Actions ssAction = new SetStartCardFaceAction(face, client, controller, this, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(ssAction);
    }

    @Override
    public void chooseGoalCard(int i, VirtualView client) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getName());
        Actions cgAction = new ChooseGoalCardAction(i, client, controller, this, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(cgAction);
    }

    @Override
    public void drawCard(int i, int whichOne, VirtualView client) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getName());
        Actions dAction = new DrawCardAction(i, whichOne, client, controller, this, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(dAction);
    }

    @Override
    public void endTurn(VirtualView client, String mex) throws RemoteException {
        int roomId = controller.getYourRoomId(client.getName());
        Actions eAction = new EndTurnAction(client, controller, this, mex, 0, roomId);
        actionsPerGame.get(roomId).getActionsQueue().add(eAction);
    }

    public void executeGame() {
        new Thread(() -> {
            while (true) {
                synchronized (actionsPerGame) {
                    for (Integer room : actionsPerGame.keySet()) {
                        Actions now = actionsPerGame.get(room).getActionsQueue().poll();
                        if (now != null) {
                            actionsPerGame.get(room).getExecutorService().submit(() -> {
                                try {
                                    now.executor();
                                } catch (RemoteException | NotBoundException e) {
                                    System.out.println("there has been a problem in the execution of actions");
                                }
                            });
                        }
                    }
                }
            }
        }).start();
    }


    public void executeStart() {
        new Thread(() -> {
            while (true) {
                Actions now = joins.poll();
                if (now != null) {
                    try {
                        now.executor();
                    } catch (RemoteException | NotBoundException e) {
                        System.out.println("there has been a problem in the execution of actions in join");
                    }
                }
            }
        }).start();
    }

}
