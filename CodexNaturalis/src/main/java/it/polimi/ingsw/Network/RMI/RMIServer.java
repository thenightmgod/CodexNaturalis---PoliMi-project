package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Actions.*;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class RMIServer extends UnicastRemoteObject implements VirtualServer{

    final MainController controller;

    //come perbacco aggiungo un client alla mappa clients se non riesco a tenere conto
    //del numero della room?
    HashMap<Integer, VirtualView> clients = new HashMap<>(); //aggiungere roba qui dentro
    int port;

    BlockingQueue<Actions> actions;

    public RMIServer(MainController controller) throws RemoteException{
        this.controller = controller;
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
    public void joinGame(String Name, VirtualView client) throws RoomFullException, RoomNotExistsException, RemoteException, NameAlreadyTakenException, NotBoundException {
        Actions jGame = new JoinAction(client, controller, Name);
        actions.add(jGame);
        //creare room
        //addare poi il lazzaro nel coso
        //mettere eccezione del nome
    }

    @Override
    public void createGame(String Name, int numPlayers, VirtualView client) throws WrongPlayersNumberException, RemoteException, NotBoundException {
        Actions cGame = new CreateAction(numPlayers, client, controller, Name);
        actions.add(cGame);
        //eccezione del nome
    }

    @Override
    public void leaveGame(String name, VirtualView client) {
        Actions lAction = new LeaveAction(name, client, controller);
        actions.add(lAction);
    }

    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws WrongIndexException {
        Actions pAction = new PlaceCardAction(controller, client, whichInHand, x, y, face);
        actions.add(pAction);
    }

    @Override
    public void setStartCardFace(boolean face, VirtualView client) { //ordine initialize game tutto gestito nella view
        Actions ssAction = new SetStartCardFaceAction(face, client, controller);
        actions.add(ssAction);
    }

    @Override
    public void chooseGoalCard(int i, VirtualView client) throws WrongIndexException {
        Actions cgAction = new ChooseGoalCardAction(i, client, controller);
        actions.add(cgAction);
    }

    @Override
    public void drawCard(int i, int whichOne, VirtualView client) throws WrongIndexException, RemoteException {
        Actions dAction = new DrawCardAction(i, whichOne, client, controller);
    }

    public void execute() throws WrongIndexException, RemoteException, NameAlreadyTakenException, NotBoundException {
        new Thread(()->{
            Actions now = null;
            try {
                now = actions.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                now.executor();
            } catch (WrongIndexException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (WrongPlayersNumberException e) {
                throw new RuntimeException(e);
            } catch (RoomFullException e) {
                throw new RuntimeException(e);
            } catch (RoomNotExistsException e) {
                throw new RuntimeException(e);
            } catch (RequirementsNotSatisfied e) {
                throw new RuntimeException(e);
            } catch (NameAlreadyTakenException e) {
                throw new RuntimeException(e);
            } catch (InvalidOperationException e) {
                throw new RuntimeException(e);
            } catch (WrongPositionException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
