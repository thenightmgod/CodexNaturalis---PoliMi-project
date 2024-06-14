package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.io.EOFException;
import java.rmi.RemoteException;
import java.util.Map;

public class LeaveAction extends Actions {

    String name;
    int roomId;

    public LeaveAction(VirtualView view, MainController manager, RMIServer server, int priority, int roomId) {
        super(view, manager, server, priority);
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {
        try {
            getManager().getViewPerGame().get(roomId).remove(getView());
            VirtualView client = getView();
            if(client != null)
                client.leaveGameMessage();
        } catch (RemoteException ignored) {
        }/*if (e.getCause() instanceof EOFException) {
                System.out.println("Client disconnected unexpectedly.");
            } else {
                e.printStackTrace();
            }*/
        }
    }
