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
        new Thread(MainServer::executeGame).start();
        new Thread(MainServer::executeStart).start();
    }

    /**
     * Executes the game logic.
     */
    public static void executeGame() {
        new Thread(() -> {
            while (true) {
                //synchronized (actionsPerGame) {
                    for (Integer room : actionsPerGame.keySet()) {
                        Actions now = actionsPerGame.get(room).getActionsQueue().poll();
                        if (now != null) {
                            actionsPerGame.get(room).getExecutorService().submit(() -> {
                                try {
                                    now.executor();
                                } catch (RemoteException | NotBoundException e) {
                                    System.out.println("there has been a problem in the execution of actions");
                                }
                            });
                        }
                    }
                //}
            }
        }).start();
    }

    /**
     * Executes the start of the game.
     */
    public static void executeStart() {
        new Thread(() -> {
            while (true) {

                Actions now = joins.poll();
                if (now != null) {
                    try {
                        now.executor();
                    } catch (RemoteException | NotBoundException e) {
                        System.out.println("there has been a problem in the execution of actions in join");
                    }
                }
            }
        }).start();
    }



}
