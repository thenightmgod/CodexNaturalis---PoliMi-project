package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class LeaveAction extends Actions{
    String name;
    public LeaveAction(String name, VirtualView view, MainController manager, RMIServer server){
        super(view, manager, server);
        this.name = name;
    }

    @Override
    public void executor() throws RemoteException {
        int k = -1;
        for(Map.Entry<Integer, GameController> entry : getManager().getControllersPerGame().entrySet()){
            if(entry.getValue().getPlayers().stream().map(Player::getName).toList().contains(getView().getName())){
                k = entry.getKey();
            }
        }
        if(k != -1){
            GameController controller = getManager().getControllersPerGame().get(k);
            getManager().getControllersPerGame().remove(controller);
            getManager().getControllers().remove(controller);
        }
    }
}
