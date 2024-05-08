package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class RMIServer extends UnicastRemoteObject implements VirtualServer{

    final MainController controller;
    LinkedList<VirtualView> clients = new LinkedList<>();

    public RMIServer(MainController controller) throws RemoteException{
        this.controller = controller;
    }

    public void startServer() throws RemoteException{ //in realtà le eccezioni dovremmo gestirle decentemente
        final String serverName = "CodexServer";

        VirtualServer server = new RMIServer(new MainController()); //ci serve un main controller che gestisce la creazione dei giochi
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, 666);
        Registry registry = LocateRegistry.createRegistry(666);
        registry.rebind(serverName, stub);
        System.out.println("Server buond.");
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        this.clients.add(client);
    }

    @Override
    public void joinGame(String Name, PlayerColor color) {

    }

    @Override
    public void createGame(String Name, PlayerColor color, int numPlayers) {

    }

    @Override
    public void leaveGame(String name, VirtualView client) {

    }

    @Override
    public void placeCard(ResourceCard card, int x, int y, FB face) {

    }

    @Override
    public void setStartCardFace(FB face) {

    }
}
