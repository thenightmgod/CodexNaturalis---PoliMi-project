package it.polimi.ingsw.Main;

import it.polimi.ingsw.Actions.Actions;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.MultipleFlow;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.Socket.SocketServer;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class MainServer {

    final static ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame = new ConcurrentHashMap<>();
    final static PriorityBlockingQueue<Actions> joins = new PriorityBlockingQueue<>();

    public static void main(String[] args) throws RemoteException {
        String input;
//        do{
            System.out.println("Insert remote ip (leave empty for localhost)");
            input = new Scanner(System.in).nextLine();
 //       } while(!input.equals("") || );

        if (input.equals("")) {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        }
        else{
            System.setProperty("java.rmi.server.hostname", input);
        }

        MainController mc = new MainController();

        RMIServer rmi = new RMIServer(mc, actionsPerGame, joins);
        rmi.startServer();
        SocketServer socket = SocketServer.createServer(mc, actionsPerGame, joins);
        new Thread(socket::startServer).start();

    }


}
