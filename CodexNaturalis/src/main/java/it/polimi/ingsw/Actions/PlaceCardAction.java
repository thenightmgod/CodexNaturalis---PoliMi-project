package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The PlaceCardAction class extends Actions and represents an action where a player places a card on the board.
 */
public class PlaceCardAction extends Actions{
    /**
     * The index of the card in the player's hand.
     */
    int whichInHand;
    /**
     * The x-coordinate of the location where the card is to be placed.
     */
    int x;
    /**
     * The y-coordinate of the location where the card is to be placed.
     */
    int y;
    /**
     * The face of the card to be placed.
     */
    FB face;
    /**
     * The ID of the room where this action is executed.
     */
    int roomId;

    /**
     * Constructs a new PlaceCardAction with the specified manager, view, card index, x-coordinate, y-coordinate, face,  priority, and room ID.
     *
     * @param manager The MainController managing this action.
     * @param view The VirtualView associated with this action.
     * @param whichInHand The index of the card in the player's hand.
     * @param x The x-coordinate of the location where the card is to be placed.
     * @param y The y-coordinate of the location where the card is to be placed.
     * @param face The face of the card to be placed.

     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public PlaceCardAction(MainController manager, VirtualView view, int whichInHand, int x, int y, FB face, int priority, int roomId){
        super(view, manager, priority);
        this.whichInHand = whichInHand;
        this.x = x;
        this.y = y;
        this.face = face;
        this.roomId = roomId;
    }

    /**
     * Executes this PlaceCardAction.
     * It calls the placeCard method of the GameController referred to this room.
     *
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    @Override
    public void executor() throws RemoteException, NotBoundException {

        GameController controller = getManager().getControllers().get(roomId);
        controller.placeCard(whichInHand, x, y, face);

    }
}