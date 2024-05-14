package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.VirtualView;

import javax.imageio.IIOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SocketServer {
    final MainController controller;
    final ServerSocket listenSocket;
    final LinkedList<SocketClientHandler> clients = new LinkedList<>();


    public SocketServer(MainController controller, ServerSocket listenSocket){
        this.controller = controller;
        this.listenSocket = listenSocket;
    }
    public void createServer(){
        try{
            ServerSocket listenSocket = new ServerSocket(4444);
            new SocketServer(new MainController(),listenSocket);
        }
        catch (IOException e){
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
    }
    public void startServer(){
        Socket clientSocket = null;
        try {
            while ((clientSocket = this.listenSocket.accept()) != null){
                InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
                OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

                SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(socketRx), new PrintWriter(socketTx));
                System.out.println("Connection established");

                clients.add(handler);
                new Thread(() -> {
                    try{
                        handler.runVirtualView();
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                //socketRx in un BufferReader per leggerlo piu easy e passarlo al costruttore di clienthandler
                //fare thread che fa partire la VirtualView di questo handler
            }
        }
        catch (IOException e){
            System.err.println("Connection failed");
            System.exit(-1);
        }
    }
}
