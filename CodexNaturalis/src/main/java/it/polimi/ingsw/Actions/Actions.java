package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.rmi.RemoteException;

public abstract class Actions {
    private String name;
    private VirtualView view;
    private MainController manager;
    private CommonClient client;

    public VirtualView getClient() {
        return view;
    }

    public MainController getManager(){
        return manager;
    }

    public String getName() {
        return name;
    }

    public VirtualView getView() {
        return view;
    }

    public void executor() throws WrongIndexException, RemoteException {}
}