package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;


/**
 * The EndTurnAction extends Actions and represents an action where a player ends their turn.
 */
public class EndTurnAction extends Actions{
    /**
     * The message associated with this EndTurnAction.
     */
    String mex;
    /**
     * The ID of the room where this action is executed.
     */
    int roomId;

    /**
     * Constructs a new EndTurnAction with the specified message, view, manager, priority, and room ID.
     *
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param mex The message associated with this EndTurnAction.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public EndTurnAction(VirtualView view, MainController manager, String mex, int priority, int roomId){
        super(view, manager, priority);
        this.mex = mex;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * It calls the changeTurns method of the GameController referred to this room.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.changeTurns(mex);
    }
}
