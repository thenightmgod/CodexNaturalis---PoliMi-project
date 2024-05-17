package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Exceptions.NameAlreadyTakenException;
import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class JoinAction extends Actions {
    public JoinAction(){
        super();
    }

    @Override
    public void executor() throws RemoteException {
        try {
            GameController c = this.getManager().joinGame(getName(), getView());
            getManager().getControllersPerGame().put(c.getRoomId(), c);

        } catch (NameAlreadyTakenException | RoomFullException | RoomNotExistsException e) {
            e.printStackTrace();
        }
    }

}
