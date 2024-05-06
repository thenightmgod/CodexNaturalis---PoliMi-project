package it.polimi.ingsw.Network.RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView {

    final VirtualServer server;

    public RMIClient(VirtualServer server) throws RemoteException{
        this.server = server;
    }

    private void run() throws RemoteException {
        this.server.connect(this);
    }

    private void runClient() throws RemoteException{
        Scanner scan = new Scanner(System.in);
        int command
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        Registry registry = LocateRegistry.getRegistry(args[0], 666);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RMIClient(server);
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public void showUpdate(Integer number) throws RemoteException {

    }
}
