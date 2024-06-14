package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class EndTurnAction extends Actions{

    String mex;
    int roomId;

    public EndTurnAction(VirtualView view, MainController manager, RMIServer server, String mex, int priority, int roomId){
        super(view, manager, server, priority);
        this.mex = mex;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {

        GameController controller = getManager().getControllers().get(roomId);
        controller.changeTurns(mex);

    }


}
