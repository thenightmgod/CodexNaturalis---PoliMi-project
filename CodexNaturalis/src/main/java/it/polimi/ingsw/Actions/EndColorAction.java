package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

public class EndColorAction extends Actions{

    private int roomId;
    private PlayerColor color;

    public EndColorAction(VirtualView client, MainController manager, int priority, PlayerColor color, int roomId){
        super(client, manager, priority);
        this.color = color;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.endColor(color);
    }
}
