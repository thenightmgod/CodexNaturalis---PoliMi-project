package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
/**
 * The EndColorAction class extends Actions and represents an action where the color chosen by the player is set.
 */
public class EndColorAction extends Actions{
    /**
     * The ID of the room where this action is executed.
     */
    private int roomId;
    /**
     * The color chosen by the player.
     */
    private PlayerColor color;
    /**
     * Constructs a new EndColorAction with the specified view, manager, priority, color, and room ID.
     *
     * @param client The VirtualView associated with this EndColorAction.
     * @param manager The MainController managing this EndColorAction.
     * @param priority The priority of this EndColorAction.
     * @param color The color chosen by the player.
     * @param roomId The ID of the room where this action is executed.
     */
    public EndColorAction(VirtualView client, MainController manager, int priority, PlayerColor color, int roomId){
        super(client, manager, priority);
        this.color = color;
        this.roomId = roomId;
    }
    /**
     * Executes this EndColorAction.
     * It calls the endColor method of the GameController referred to this room.
     *
     * @throws RemoteException if there is an error executing the action remotely.
     */
    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.endColor(color);
    }
}
