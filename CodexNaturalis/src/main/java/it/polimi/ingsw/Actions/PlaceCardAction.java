package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class PlaceCardAction extends Actions{

    int whichInHand;
    int x;
    int y;
    FB face;
    int roomId;

    public PlaceCardAction(MainController manager, VirtualView view, int whichInHand, int x, int y, FB face, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.whichInHand = whichInHand;
        this.x = x;
        this.y = y;
        this.face = face;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException, NotBoundException {

        GameController controller = getManager().getControllers().get(roomId);
        controller.placeCard(whichInHand, x, y, face);

    }
}