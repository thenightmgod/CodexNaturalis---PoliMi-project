package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Controller.MainController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SocketClientHandler implements VirtualView {
    final MainController controller;
    final SocketServer server;
    final BufferedReader input;
    final PrintWriter output;

    public SocketClientHandler(MainController controller, SocketServer server, BufferedReader input, PrintWriter output){
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
    }

    public void runVirtualView() throws IOException{
        //gestisco i messaggi che ricevo da client, per ogni messaggio eseguiro diverse azioni e faccio un updatebroadcast
    }

    @Override
    public void reportError(String details) {

    }

    @Override
    public void showUpdate() {

    }
}
