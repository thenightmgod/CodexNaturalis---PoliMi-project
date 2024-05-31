package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public abstract class Actions {

    private VirtualView view;
    private MainController manager;
    private RMIServer server;

    public Actions(VirtualView view, MainController manager, RMIServer server) {
        this.view = view;
        this.manager = manager;
        this.server = server;
    }

    public RMIServer getServer() {return server;}

    public MainController getManager(){
        return manager;
    }

    public VirtualView getView() {
        return view;
    }

    public void executor() throws RemoteException, NotBoundException {}
}