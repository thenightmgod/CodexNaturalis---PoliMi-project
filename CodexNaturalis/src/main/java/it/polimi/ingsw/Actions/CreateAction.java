package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

public class CreateAction extends Actions{
    int i;
    String name;
    public CreateAction(int i, VirtualView view, MainController manager, String name){
        super(view, manager);
        this.i = i;
        this.name = name;
    }

    @Override
    public void executor() throws RemoteException {
        GameController c = this.getManager().createGame(name, i, getView());
        getManager().getControllersPerGame().put(c.getRoomId(), c);
    }
}
