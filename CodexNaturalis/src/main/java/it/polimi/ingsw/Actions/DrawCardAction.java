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

    public DrawCardAction(int i, int whichOne, VirtualView view, MainController manager, RMIServer server){
        super(view, manager, server);
        this.i = i;
        this.whichOne = whichOne;
    }

    @Override
    public void executor() throws RemoteException, NotBoundException {
        int k = -1;
        for(Map.Entry<Integer, GameController> entry : getManager().getControllersPerGame().entrySet()){
            if(entry.getValue().getPlayers().stream().map(Player::getName).toList().contains(getView().getName())){
                k = entry.getKey();
            }
        }
        if(k != -1){
            GameController controller = getManager().getControllersPerGame().get(k);
            controller.drawCard(i, whichOne);}
    }
}