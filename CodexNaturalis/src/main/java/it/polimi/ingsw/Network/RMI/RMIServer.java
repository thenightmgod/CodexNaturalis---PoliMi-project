package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class RMIServer extends UnicastRemoteObject implements VirtualServer{

    final GameController controller;
    LinkedList<VirtualView> clients = new LinkedList<>();

    public RMIServer(GameController controller) throws RemoteException{
        this.controller = controller;
    }

    public static void main(String[] args) throws RemoteException{ //in realtà le eccezioni dovremmo gestirle decentemente
        final String serverName = "CodexServer";

        VirtualServer server = new RMIServer(new GameController(7)); //ci serve un main controller che gestisce la creazione dei giochi
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, 666);
        Registry registry = LocateRegistry.createRegistry(666);
        registry.rebind(serverName, stub);
        System.out.println("Server buond.");
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        this.clients.add(client);
    }
}
