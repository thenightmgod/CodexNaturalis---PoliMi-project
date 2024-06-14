package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class SetStartCardFaceAction extends Actions {

    boolean face;
    int roomId;

    public SetStartCardFaceAction(boolean face, VirtualView view, MainController manager, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.face = face;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {

            GameController controller = getManager().getControllers().get(roomId);
            FB f = FB.FRONT;
            if(!face) f = FB.BACK;
            controller.placeStartCard(getView().getName(), f);
    }

}