package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

/**
 * Represents an action where a player leaves a game.
 * This action is a subclass of the Actions class.
 */
public class LeaveAction extends Actions {

    int roomId;

    /**
     * Constructs a new LeaveAction with the specified view, manager, server, priority, and room ID.
     *
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param priority The priority of this action.
     * @param roomId The ID of the room from which the player is leaving.
     */
    public LeaveAction(VirtualView view, MainController manager, int priority, int roomId) {
        super(view, manager, priority);
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * The player leaves the game and a leave game message is sent.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {
        try {
            getManager().getViewPerGame().get(roomId).remove(getView());
            VirtualView client = getView();
            if(client != null)
                client.leaveGameMessage();
            } catch (RemoteException ignored) {}
        }

    }
