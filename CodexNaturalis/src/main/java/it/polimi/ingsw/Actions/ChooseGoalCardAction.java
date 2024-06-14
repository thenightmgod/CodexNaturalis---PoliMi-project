package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.stream.Collectors;

public class ChooseGoalCardAction extends Actions{

    int i;
    int roomId;

    public ChooseGoalCardAction(int i, VirtualView view, MainController manager, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.i = i;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.chooseGoalCard(getView().getName(), i);
    }
}