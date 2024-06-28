package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;


/**
 * This ChooseGoalCardAction class extends Actions and represents an action where a player chooses a goal card.
 * This action is a subclass of the Actions class.
 */
public class ChooseGoalCardAction extends Actions{
    /**
     * The index of the goal card chosen.
     */
    int i;
    /**
     * The ID of the room where this action is executed.
     */
    int roomId;

    /**
     * Constructs a new ChooseGoalCardAction with the specified index, view, manager, priority, and room ID.
     *
     * @param i The index of the goal card chosen.
     * @param roomId The ID of the room where this action is executed.
     * @see Actions
     */
    public ChooseGoalCardAction(int i, VirtualView view, MainController manager, int priority, int roomId){
        super(view, manager, priority);
        this.i = i;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * It calls the chooseGoalCard method of the GameController referred to this room.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.chooseGoalCard(getView().getNames(), i);
    }
}