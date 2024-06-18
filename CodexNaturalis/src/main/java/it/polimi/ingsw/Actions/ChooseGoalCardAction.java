package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;


/**
 * Represents an action where a player chooses a goal card.
 * This action is a subclass of the Actions class.
 */
public class ChooseGoalCardAction extends Actions{

    int i;
    int roomId;

    /**
     * Constructs a new ChooseGoalCardAction with the specified index, view, manager, server, priority, and room ID.
     *
     * @param i The index of the goal card to be chosen.
     * @param roomId The ID of the room where this action is executed.
     * @see Actions
     */
    public ChooseGoalCardAction(int i, VirtualView view, MainController manager, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.i = i;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * The player chooses a goal card.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.chooseGoalCard(getView().getName(), i);
    }
}