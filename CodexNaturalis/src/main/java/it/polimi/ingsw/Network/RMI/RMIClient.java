package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.CommonClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    String name;
    final VirtualServer server; //in modo da chiamare tutti i metodi del server

    public RMIClient(VirtualServer server, String name) throws RemoteException{
        this.name = name;
        this.server = server;
        this.runClient();
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

    public void createClient(String name) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        Registry registry = LocateRegistry.getRegistry(666);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RMIClient(server, name);
    }

    public void placeCard(int whichInHand, int x, int y, FB face) throws WrongIndexException{
        server.placeCard(this, whichInHand, x, y, face);
    };

    @Override
    public void setStartCardFace(boolean face, VirtualView client){
        server.setStartCardFace(face, this);
    }

    @Override
    public void leaveGame(String name, VirtualView client){
        server.leaveGame(name, this);
    }

    @Override
    public void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws WrongIndexException {

    }

    @Override
    public void chooseGoalCard(int i, VirtualView client) throws WrongIndexException{
        server.chooseGoalCard(i, this);
    };

    @Override
    public void createGame(String Name, PlayerColor color, int numPlayers) throws WrongPlayersNumberException{
        server.createGame(Name, color, numPlayers);
    };

    @Override
    public void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException{
        server.joinGame(Name, color);
    }


    @Override
    public void reportError(String details) throws RemoteException {

    }

    public void showUpdate() throws RemoteException {

    }
}
