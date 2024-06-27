package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Abstract class representing an action in the game.
 * Each action has a priority, with higher priority actions executed first.
 */
public abstract class Actions implements Comparable<Actions>{
    /**
     * The VirtualView associated with this action.
     */
    private VirtualView view;
    /**
     * The MainController managing this action.
     */
    private MainController manager;
    /**
     * The priority of this action.
     */
    private int priority;

    /**
     * Constructs a new Action with the specified view, manager, server, and priority.
     *
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param priority The priority of this action.
     */
    public Actions(VirtualView view, MainController manager,int priority) {
        this.view = view;
        this.manager = manager;
        this.priority = priority;
    }

    /**
     * Retrieves the manager of this action.
     *
     * @return The manager of this action.
     */
    public MainController getManager(){
        return manager;
    }

    /**
     * Retrieves the view of this action.
     *
     * @return The view of this action.
     */
    public VirtualView getView() {
        return view;
    }

    /**
     * Executes this action.
     * This method should be overridden by subclasses.
     *
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    public void executor() throws RemoteException, NotBoundException {}

    /**
     * Compares this action with another action for order.
     * Returns a negative integer, zero, or a positive integer as this action is less than, equal to, or greater than the specified action.
     *
     * @param other The action to be compared.
     * @return A negative integer, zero, or a positive integer as this action is less than, equal to, or greater than the specified action.
     */
    @Override
    public int compareTo(Actions other) {
        return other.priority - this.priority;
    }
}