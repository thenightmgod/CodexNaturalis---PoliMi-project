package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public abstract class Actions implements Comparable<Actions>{

    private VirtualView view;
    private MainController manager;
    private RMIServer server;
    private int priority;

    public Actions(VirtualView view, MainController manager, RMIServer server, int priority) {
        this.view = view;
        this.manager = manager;
        this.server = server;
        this.priority = priority;
    }

    public RMIServer getServer() {return server;}

    public MainController getManager(){
        return manager;
    }

    public VirtualView getView() {
        return view;
    }

    public int getPriority() {
        return priority;
    }

    public void executor() throws RemoteException, NotBoundException {}

    @Override
    public int compareTo(Actions other) {
        // Higher priority actions come first
        return other.priority - this.priority;
    }
}