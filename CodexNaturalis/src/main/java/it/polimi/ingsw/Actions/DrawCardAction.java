package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class DrawCardAction extends Actions {
    int i;
    int whichOne;

    public DrawCardAction(int i, int whichOne, VirtualView view, MainController manager){
        super(view, manager);
        this.i = i;
        this.whichOne = whichOne;
    }

    public void executor() throws WrongIndexException, RemoteException {
        int k = getManager().getControllersPerGame().entrySet().stream().filter(entry -> getManager().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = this.getManager().getControllersPerGame().get(k);
        controller.drawCard(i, whichOne);
    }

}