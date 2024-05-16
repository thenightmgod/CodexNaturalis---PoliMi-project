package it.polimi.ingsw.Network.Socket;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

import static java.lang.System.exit;

//il client deve
public class SocketClient {
    final VirtualSocketServer server;
    final BufferedReader input;
    public SocketClient(BufferedReader input, BufferedWriter output){ //input e output sono rispettivamente il socket.getinputstream e socket.outputstream
        this.input = input;
        this.server = new VirtualSocketServer(output);

    }
    public void initializeClient(String hostname, int ServerPort){
        Socket socket;
        OutputStreamWriter socketTx;
        InputStreamReader socketRx;
        try{
            socket = new Socket(hostname, ServerPort);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
        try{
            socketRx = new InputStreamReader(socket.getInputStream());
        }
        catch(IOException e){
            System.out.println(e.toString());
            exit(1);
        }
        try{
            socketTx = new OutputStreamWriter(socket.getOutputStream());
        }
        catch(IOException e){
            System.out.println(e.toString());
            exit(1);
        }
        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
    }

    public void run() {
        try {
            new Thread(() -> {
                try {
                    runVirtualServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
            this.runClient();
        }
        catch(RemoteException e){
            System.out.println("RemoteException");
        }
    }

    private void runClient(){

    }

    public void runVirtualServer(){

    }

    //il client deve avere un main che
    //si connette al socket del server
    //passandogli negli argomenti del main al posto 0 l'host,
    //al posto 1 la porta

    //lo stato della partita è sul model
    //

}
