package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class CreateAction extends Actions{

    int i;
    int roomId;
    String name;

    public CreateAction(int i, VirtualView view, MainController manager, String name, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.i = i;
        this.name = name;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {
        getManager().createGame(name, i, getView(), roomId);
    }
}

