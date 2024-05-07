package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.CommonClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    final VirtualServer server;

    public RMIClient(VirtualServer server) throws RemoteException{
        this.server = server;
        this.runClient();
    }

    private void run() throws RemoteException {
        this.server.connect(this);
    }

    private void runClient() throws RemoteException{
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("> ");
            int command = scan.nextInt();
            //switch(command){
              //  case
            //};
        }
    }

    public void createClient() throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        Registry registry = LocateRegistry.getRegistry(666);
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
