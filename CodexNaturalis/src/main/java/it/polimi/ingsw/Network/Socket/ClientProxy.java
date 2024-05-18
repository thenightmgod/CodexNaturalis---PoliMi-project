package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.RMI.VirtualView;


import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.LinkedList;


public class ClientProxy implements VirtualView {
    final PrintWriter output;
    public ClientProxy(PrintWriter output) {
        this.output = new PrintWriter(output);
    }

    @Override
    public void showException(String details) throws RemoteException {

    }
    @Override
    public void updatePoints(int points, String name) throws RemoteException {
    }

    @Override
    public void showGoals(LinkedList<GoalCard> goals) throws RemoteException {

    }

    @Override
    public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

    }

    @Override
    public void updateField(String name, PlayingField field) throws RemoteException {

    }

    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

    }

    @Override
    public void update() throws RemoteException {

    }

    @Override
    public void showOtherField(String player) throws RemoteException {

    }
    public void sendMessage(String stringMessage){
        output.write(stringMessage);
        output.flush();
    }
}
