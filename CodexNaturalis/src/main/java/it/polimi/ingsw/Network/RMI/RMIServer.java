package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Actions.Actions;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Model.Room;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
    public void joinGame(String Name) throws RoomFullException, RoomNotExistsException, RemoteException, NameAlreadyTakenException, NotBoundException {
        RMIClient client = new RMIClient(Name);
        try {
            GameController c = this.controller.joinGame(Name, client);
            clients.put(c.getRoomId(), client);

        } catch(NameAlreadyTakenException e){
            e.printStackTrace();
        }
        //creare room
        //addare poi il lazzaro nel coso
        //mettere eccezione del nome
    }

    @Override
    public void createGame(String Name, int numPlayers, VirtualView client) throws WrongPlayersNumberException, RemoteException, NotBoundException {
        GameController c = this.controller.createGame(Name, numPlayers, client);
        clients.put(c.getRoomId(), client);
        //eccezione del nome
    }

    @Override
    public void leaveGame(String name, VirtualView client) {
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController c = this.controller.leaveGame(name, k);
        clients.remove(c);
    }

    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws WrongIndexException {
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = this.controller.getControllers().get(k);
        //cambiare dinamica di place card con l'int della mano
        controller.placeCard(whichInHand, x, y, face);
    }

    @Override
    public void setStartCardFace(boolean face, VirtualView client) { //ordine initialize game tutto gestito nella view
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = this.controller.getControllers().get(k);
        FB f = FB.FRONT;
        if(!face) f = FB.BACK;
        controller.giveStartCard(f);
    }

    @Override
    public void chooseGoalCard(int i, VirtualView client) throws WrongIndexException {
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = this.controller.getControllers().get(k);
        controller.chooseGoalCard(controller.getGame().getTurn(), i);
    }

    @Override
    public void drawCard(int i, int whichOne, VirtualView client) throws WrongIndexException, RemoteException {
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = this.controller.getControllers().get(k);
        controller.drawCard(i, whichOne);
    }

    public void execute() throws InterruptedException, WrongIndexException {
        new Thread(()->{

        }).start();
    }

}
