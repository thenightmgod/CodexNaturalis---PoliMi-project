package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Represents an action where a player draws a card.
 * This action is a subclass of the Actions class.
 */
public class DrawCardAction extends Actions {

    int i;
    int whichOne;
    int roomId;

    /**
     * Constructs a new DrawCardAction with the specified index, view, manager, server, priority, and room ID.
     *
     * @param i The index of the deck from which to draw a card.
     * @param whichOne The index of the card to be drawn.
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public DrawCardAction(int i, int whichOne, VirtualView view, MainController manager, int priority, int roomId){
        super(view, manager, priority);
        this.i = i;
        this.whichOne = whichOne;
        this.roomId = roomId;
    }

    /**
     * Executes this action.
     * The player draws a card from the specified deck.
     *
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    @Override
    public void executor() throws RemoteException, NotBoundException {

        GameController controller = getManager().getControllers().get(roomId);
        controller.drawCard(i, whichOne);
    }

}