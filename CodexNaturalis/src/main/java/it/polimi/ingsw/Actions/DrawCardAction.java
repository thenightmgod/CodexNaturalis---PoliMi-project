package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class DrawCardAction extends Actions {

    int i;
    int whichOne;
    int roomId;

    public DrawCardAction(int i, int whichOne, VirtualView view, MainController manager, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.i = i;
        this.whichOne = whichOne;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException, NotBoundException {

        GameController controller = getManager().getControllers().get(roomId);
        controller.drawCard(i, whichOne);
    }

}