package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

/**
 * This CreateActions extends Actions and represents an action where a player creates a game.
 */
public class CreateAction extends Actions{
    /**
     * The number of players in the game.
     */
    int i;
    /**
     * The ID of the room where this action is executed.
     */
    int roomId;
    /**
     * The name of the player that wants to create a game.
     */
    String name;

    /**
     * Constructs a new CreateAction with the specified number of players, view, manager, player name,  priority, and room ID.
     *
     * @param i The number of players in the game to be created.
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param name The name of player that wants to create a game.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public CreateAction(int i, VirtualView view, MainController manager, String name, int priority, int roomId){
        super(view, manager, priority);
        this.i = i;
        this.name = name;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * It calls the createGame method of the MainController.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {
        getManager().createGame(name, i, getView(), roomId);
    }
}

