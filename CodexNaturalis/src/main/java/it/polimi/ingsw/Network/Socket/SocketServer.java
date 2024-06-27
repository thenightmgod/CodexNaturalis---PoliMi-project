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
/**
 * The SocketServer class is used to create a server socket and accept connections from clients.
 * It contains methods to start the server and to get the controller.
 */
public class SocketServer {
    /**
     * The main controller of the game.
     */
    final MainController controller;
    /**
     * The server socket.
     */
    final ServerSocket listenSocket;
    /**
     * The list of clients connected to the server.
     */
    final LinkedList<SocketClientHandler> clients = new LinkedList<>();
    /**
     * Map of multiple flows of actions in the game.
     */
    ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame;
    /**
     * The queue of actions to be executed (only for CreateGameAction and JoinGameAction).
     */
    PriorityBlockingQueue<Actions> joins;

    /**
     * Constructor for the SocketServer class.
     *
     * @param controller The main controller of the game.
     * @param listenSocket The server socket.
     * @param actionsPerGame Map of multiple flows of actions in the game.
     * @param joins The queue of actions to be executed (only for CreateGameAction and JoinGameAction).
     */
    public SocketServer(MainController controller, ServerSocket listenSocket, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins){
        this.controller = controller;
        this.listenSocket = listenSocket;
        this.actionsPerGame = actionsPerGame;
        this.joins = joins;
    }

    /**
     * Creates a server socket and returns a new SocketServer object.
     *
     * @param mc The main controller of the game.
     * @param actionsPerGame Map of multiple flows of actions in the game.
     * @param joins The queue of actions to be executed (only for CreateGameAction and JoinGameAction).
     * @return The new SocketServer object.
     */
    public static SocketServer createServer(MainController mc, ConcurrentHashMap<Integer, MultipleFlow> actionsPerGame, PriorityBlockingQueue<Actions> joins){
        SocketServer server = null;
        try{
            ServerSocket listenSocket = new ServerSocket(44458);
            server = new SocketServer(mc,listenSocket, actionsPerGame, joins);
            System.out.println("Server Socket bound.");
            return server;
        }
        catch (IOException e){
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        return server;
    }

    /**
     * Starts the server and accepts connections from clients.
     */
    public void startServer() {
        Socket clientSocket = null;
        try {
            while ((clientSocket = this.listenSocket.accept()) != null){ //dubbio: questo clientSocket è univoco per ogni client che si collega?
                //quando creo il socketclienthandler devo mettergli come input l'ouput del ServerProxy e devo mettergli
                //come output il client Proxy che smista il mex verso il client

                SocketClientHandler handler = new SocketClientHandler(this.controller, this, clientSocket, this.actionsPerGame, this.joins);
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
    /**
     * Returns the main controller of the game.
     *
     * @return The main controller of the game.
     */
    public MainController getController() {
        return controller;
    }
}
