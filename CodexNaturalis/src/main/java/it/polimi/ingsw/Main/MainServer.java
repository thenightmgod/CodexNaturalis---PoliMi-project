package it.polimi.ingsw.Main;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.Socket.SocketServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainServer {
    public static void main(String[] args) throws RemoteException {
        String input;
        do{
            System.out.println("Insert remote ip (leave empty for localhost)");
            input = new Scanner(System.in).nextLine();
        } while(!input.equals(""));

        if (input.equals("")) {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        }
        else{
            System.setProperty("java.rmi.server.hostname", input);
        }

        MainController mc = new MainController();

        RMIServer rmi = new RMIServer(mc);
        rmi.startServer();
        SocketServer server = SocketServer.createServer(mc);
        server.startServer();
    }
}
