package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.ClientModel;
import it.polimi.ingsw.Network.CommonClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    String name;
    private VirtualServer server; //in modo da chiamare tutti i metodi del server
    private Registry registry;
    private CommonClient commonClient; //magari sta roba va messa al client generico se lo facciamo
    private ClientModel model;


    public RMIClient(VirtualServer server, String name) throws RemoteException{
        this.name = name;
        this.server = server;
        //andrà runnato this.runClient();
    }


    public void initializeClient(String name) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        Registry registry = LocateRegistry.getRegistry(666);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);
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
    public void createGame(String Name, PlayerColor color, int numPlayers) throws WrongPlayersNumberException, RemoteException {
        server.createGame(Name, color, numPlayers);
    };

    @Override
    public void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException, RemoteException {
        server.joinGame(Name, color);
    }


    @Override
    public void reportError(String details) throws RemoteException {

    }

    public void showUpdate() throws RemoteException {

    }
}
