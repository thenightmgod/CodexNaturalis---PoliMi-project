package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

public class JoinAction extends Actions {
    String name;
    public JoinAction( VirtualView view, MainController manager, String name){
        super( view, manager);
        this.name = name;
    }

    @Override
    public void executor()  {
        GameController c = this.getManager().joinGame(name, getView());
        getManager().getControllersPerGame().put(c.getRoomId(), c);
    }

}
