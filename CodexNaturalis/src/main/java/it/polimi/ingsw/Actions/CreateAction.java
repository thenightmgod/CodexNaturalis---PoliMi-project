package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Network.RMI.RMIClient;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CreateAction extends Actions{
    int i;
    public CreateAction(int i){
        super();
        this.i = i;
    }

    @Override
    public void executor() throws RemoteException, WrongPlayersNumberException {
        GameController c = this.getManager().createGame(getName(), i, getView());
        getManager().getControllersPerGame().put(c.getRoomId(), c);
    }
}
