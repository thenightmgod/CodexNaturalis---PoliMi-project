package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class LeaveAction extends Actions{
    String name;
    public LeaveAction(String name, VirtualView view, MainController manager){
        super(view, manager);
        this.name = name;
    }

    @Override
    public void executor() throws RemoteException {
        int k = getManager().getControllersPerGame().entrySet().stream().filter(entry -> getManager().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController c = this.getManager().leaveGame(name, k);
        getManager().getControllersPerGame().remove(c);
    }
}
