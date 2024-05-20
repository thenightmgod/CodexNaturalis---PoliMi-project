package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

    //OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());
    //PrintWriter writer = new PrintWriter(socketTx, true);
   // SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(socketRx), new ClientProxy(writer));

    public void startServer() {
        Socket clientSocket = null;
        try {
            while ((clientSocket = this.listenSocket.accept()) != null){
                //quando creo il socketclienthandler devo mettergli come input l'ouput del ServerProxy e devo mettergli
                //come output il client Proxy che smista il mex verso il client
                InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
                OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());
                SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(socketRx), new PrintWriter(socketTx));
                System.out.println("Connection established");
                synchronized (this.clients) {
                    clients.add(handler);
                }
                new Thread(() -> {
                    try{
                        handler.runVirtualView();
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    } catch (RoomFullException e) {
                        throw new RuntimeException(e);
                    } catch (RoomNotExistsException e) {
                        throw new RuntimeException(e);
                    } catch (RequirementsNotSatisfied e) {
                        throw new RuntimeException(e);
                    } catch (NameAlreadyTakenException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidOperationException e) {
                        throw new RuntimeException(e);
                    } catch (WrongIndexException e) {
                        throw new RuntimeException(e);
                    } catch (WrongPlayersNumberException e) {
                        throw new RuntimeException(e);
                    } catch (WrongPositionException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                //socketRx in un BufferReader per leggerlo piu easy e passarlo al costruttore di clienthandler
            }
        }
        catch (IOException e){
            System.err.println("Connection failed");
            System.exit(-1);
        }
    }
}
