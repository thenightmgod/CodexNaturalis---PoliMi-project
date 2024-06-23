package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Actions.Actions;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.MultipleFlow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class SocketServer {
    final MainController controller;
    final ServerSocket listenSocket;
    final LinkedList<SocketClientHandler> clients = new LinkedList<>();
    ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame;
    PriorityBlockingQueue<Actions> joins;


    public SocketServer(MainController controller, ServerSocket listenSocket, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins){
        this.controller = controller;
        this.listenSocket = listenSocket;
        this.actionsPerGame = actionsPerGame;
        this.joins = joins;
    }

    public static SocketServer createServer(MainController mc, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins){
        SocketServer server = null;
        try{
            ServerSocket listenSocket = new ServerSocket(44458);
            server = new SocketServer(mc,listenSocket, actionsPerGame, joins);
            System.out.println("Server Socket ready");
            return server;
        }
        catch (IOException e){
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        return server;
    }

    //OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());
    //PrintWriter writer = new PrintWriter(socketTx, true);
   // SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(socketRx), new ClientProxy(writer));

    public void startServer() {
        Socket clientSocket = null;
        try {
            while ((clientSocket = this.listenSocket.accept()) != null){ //dubbio: questo clientSocket è univoco per ogni client che si collega?
                //quando creo il socketclienthandler devo mettergli come input l'ouput del ServerProxy e devo mettergli
                //come output il client Proxy che smista il mex verso il client

                SocketClientHandler handler = new SocketClientHandler(this.controller, this, clientSocket, this.actionsPerGame);
                handler.start();
                synchronized (this.clients) {
                    clients.add(handler);
                }
                //socketRx in un BufferReader per leggerlo piu easy e passarlo al costruttore di clienthandler
            }
        }
        catch (IOException e){
            System.err.println("Connection failed");
            System.exit(-1);
        }
    }

    public MainController getController() {
        return controller;
    }
}
