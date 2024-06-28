package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

/**
 * The LeaveAction class extends Actions and represents an action where a player leaves a game.
 */
public class LeaveAction extends Actions {
    /**
     * The ID of the room from which the player is leaving.
     */
    int roomId;

    /**
     * Constructs a new LeaveAction with the specified view, manager, priority, and room ID.
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
     * Executes this LeaveAction.
     * It removes the player from the game and sends a message to the player.
     *
     * @throws RemoteException if there is an error executing the action remotely.
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
