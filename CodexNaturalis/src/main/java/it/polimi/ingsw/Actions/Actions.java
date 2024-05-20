package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

public abstract class Actions {

    private VirtualView view;
    private MainController manager;

    public Actions(VirtualView view, MainController manager) {
        this.view = view;
        this.manager = manager;
    }

    public MainController getManager(){
        return manager;
    }

    public VirtualView getView() {
        return view;
    }

    public void executor() throws WrongIndexException, RemoteException, WrongPlayersNumberException, RoomFullException, RoomNotExistsException, RequirementsNotSatisfied, NameAlreadyTakenException, InvalidOperationException, WrongPositionException {}
}