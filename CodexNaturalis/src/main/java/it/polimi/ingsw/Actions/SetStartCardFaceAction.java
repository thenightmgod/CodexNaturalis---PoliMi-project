package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

/**
 * Represents an action where a player sets the face of the start card.
 * This action is a subclass of the Actions class.
 */
public class SetStartCardFaceAction extends Actions {

    boolean face;
    int roomId;

    /**
     * Constructs a new SetStartCardFaceAction with the specified face, view, manager, server, priority, and room ID.
     *
     * @param face The face of the start card.
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param server The RMIServer where this action is executed.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public SetStartCardFaceAction(boolean face, VirtualView view, MainController manager, RMIServer server, int priority, int roomId){
        super(view, manager, server, priority);
        this.face = face;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * The player sets the face of the start card.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void executor() throws RemoteException {

            GameController controller = getManager().getControllers().get(roomId);
            FB f = FB.FRONT;
            if(!face) f = FB.BACK;
            controller.placeStartCard(getView().getName(), f);
    }

}