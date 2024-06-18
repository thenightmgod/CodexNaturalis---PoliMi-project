package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

/**
 * Represents an action where a player creates a game.
 * This action is a subclass of the Actions class.
 */
public class CreateAction extends Actions{

    int i;
    int roomId;
    String name;

    /**
     * Constructs a new CreateAction with the specified number of players, view, manager, game name, server, priority, and room ID.
     *
     * @param i The number of players in the game to be created.
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param name The name of the game to be created.
     * @param server The RMIServer where this action is executed.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public CreateAction(int i, VirtualView view, MainController manager, String name, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.i = i;
        this.name = name;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * The player creates a game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {
        getManager().createGame(name, i, getView(), roomId);
    }
}

