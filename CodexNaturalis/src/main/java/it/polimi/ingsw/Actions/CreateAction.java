package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Network.RMI.RMIClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CreateAction extends Actions{
        int i;
        public CreateAction(int i){
            super();
            this.i = i;
        }
 /*   @Override
    public void executor() throws WrongPlayersNumberException, RemoteException, NotBoundException {
        GameController c = this.getManager().getControllersPerGame().get().createGame(this.getName(), i, this.getView());
        getManager().getControllersPerGame().put(c.getRoomId(), client);
    }*/
}
