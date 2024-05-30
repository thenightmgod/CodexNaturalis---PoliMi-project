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
import java.util.concurrent.BlockingQueue;

public class RMIServer implements VirtualServer{

    final MainController controller;

    //come perbacco aggiungo un client alla mappa clients se non riesco a tenere conto
    //del numero della room?
    int port;

    BlockingQueue<Actions> actions;

    public RMIServer(MainController controller) throws RemoteException{
        this.controller = controller;
        port = 4445;
    }

    public void startServer() throws RemoteException{ //in realtà le eccezioni dovremmo gestirle decentemente

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
        Actions jGame = new JoinAction(client, controller, Name, this);
        actions.add(jGame);
        //creare room
        //addare poi il lazzaro nel coso
        //mettere eccezione del nome
    }

    @Override
    public void createGame(String Name, int numPlayers, VirtualView client) throws RemoteException {
        Actions cGame = new CreateAction(numPlayers, client, controller, Name, this);
        actions.add(cGame);
        //eccezione del nome
    }

    @Override
    public void leaveGame(String name, VirtualView client) throws RemoteException {
        Actions lAction = new LeaveAction(name, client, controller, this);
        actions.add(lAction);
    }

    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws RemoteException {
        Actions pAction = new PlaceCardAction(controller, client, whichInHand, x, y, face, this);
        actions.add(pAction);
    }

    @Override
    public void setStartCardFace(boolean face, VirtualView client) throws RemoteException{ //ordine initialize game tutto gestito nella view
        Actions ssAction = new SetStartCardFaceAction(face, client, controller, this);
        actions.add(ssAction);
    }

    @Override
    public void chooseGoalCard(int i, VirtualView client) throws RemoteException {
        Actions cgAction = new ChooseGoalCardAction(i, client, controller, this);
        actions.add(cgAction);
    }

    @Override
    public void drawCard(int i, int whichOne, VirtualView client) throws RemoteException {
        Actions dAction = new DrawCardAction(i, whichOne, client, controller, this);
        actions.add(dAction);
    }

    @Override
    public void endTurn(VirtualView client) throws RemoteException {
        Actions eAction = new EndTurnAction(client, controller, this);
        actions.add(eAction);
    }

    public void execute() {
        new Thread(()->{
            Actions now = null;
            try {
                now = actions.take();
                now.executor();
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
