package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class EndTurnAction extends Actions{


    public EndTurnAction(VirtualView view, MainController manager, RMIServer server){
        super(view, manager, server);
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
            controller.changeTurns();
        }
    }


}
