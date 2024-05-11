package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Model.Room;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RMIServer extends UnicastRemoteObject implements VirtualServer{

    final MainController controller;

    //come perbacco aggiungo un client alla mappa clients se non riesco a tenere conto
    //del numero della room?
    HashMap<Integer, VirtualView> clients = new HashMap<>();

    public RMIServer(MainController controller) throws RemoteException{
        this.controller = controller;
    }

    public void startRMI() throws RemoteException{ //in realtà le eccezioni dovremmo gestirle decentemente
        final String serverName = "CodexServer";

        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(this, 666);
        Registry registry = LocateRegistry.createRegistry(666);
        registry.rebind(serverName, stub);
        System.out.println("Server buond.");
    }

    @Override
    public void joinGame(String Name, PlayerColor color) throws RoomFullException, RoomNotExistsException {
        this.controller.joinGame(Name, color);
        //addare nella mappa
    }

    @Override
    public void createGame(String Name, PlayerColor color, int numPlayers) throws WrongPlayersNumberException {
        this.controller.createGame(Name, color, numPlayers);
        //addare nella mappa
    }

    @Override
    public void leaveGame(String name, VirtualView client) {
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        this.controller.leaveGame(name, k);
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

    public void chooseGoalCard(int i, VirtualView client) throws WrongIndexException {
        int k = clients.entrySet().stream().filter(entry -> client.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = this.controller.getControllers().get(k);
        controller.chooseGoalCard(controller.getGame().getTurn(), i);
    }

}
