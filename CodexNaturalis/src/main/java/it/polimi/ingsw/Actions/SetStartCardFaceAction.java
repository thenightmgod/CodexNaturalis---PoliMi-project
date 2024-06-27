package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

/**
 * The SetStartCardFaceAction class extends Actions and represents an action where the player sets the face of the start card.
 */
public class SetStartCardFaceAction extends Actions {
    /**
     * The face of the start card.
     */
    boolean face;
    /**
     * The ID of the room where this action is executed.
     */
    int roomId;

    /**
     * Constructs a new SetStartCardFaceAction with the specified face, view, manager, priority, and room ID.
     *
     * @param face The face of the start card.
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public SetStartCardFaceAction(boolean face, VirtualView view, MainController manager, int priority, int roomId){
        super(view, manager, priority);
        this.face = face;
        this.roomId = roomId;
    }

    /**
     * Executes this SetStartCardFaceAction.
     * It calls the placeStartCard method of the GameController referred to this room.
     *
     * @throws RemoteException if there is an error executing the action remotely.
     */
    @Override
    public void executor() throws RemoteException {

            GameController controller = getManager().getControllers().get(roomId);
            FB f = FB.FRONT;
            if(!face) f = FB.BACK;
            controller.placeStartCard(getView().getNames(), f);
    }

}