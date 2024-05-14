package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.LinkedList;

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
    public void showException(String details) throws RemoteException{

    }

    public void updatePoints(int points, String name) throws RemoteException{

    }

    public void showGoals(LinkedList<GoalCard> goals) throws RemoteException{

    }

    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException{

    }

    public void updateField(String name, PlayingField field) throws RemoteException{

    }

    public void showFreePositions(String name) throws RemoteException{

    }

    public void update() throws RemoteException{

    } //update il clientModel

    public void showOtherField(String player) throws RemoteException{

    }
}
