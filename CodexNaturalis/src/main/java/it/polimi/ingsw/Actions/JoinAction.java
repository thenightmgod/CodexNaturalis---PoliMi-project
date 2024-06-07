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

public class JoinAction extends Actions {
    String name;
    public JoinAction(VirtualView view, MainController manager, String name, RMIServer server){
        super( view, manager, server);
        this.name = name;
    }

    public void executor() throws RemoteException, NotBoundException {
        getManager().joinGame(name, getView());
    }
}
