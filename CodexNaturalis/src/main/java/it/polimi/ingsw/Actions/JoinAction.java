package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Represents an action where a player joins a game.
 * This action is a subclass of the Actions class.
 */
public class JoinAction extends Actions {

    String name;
    int roomId;

    /**
     * Constructs a new JoinAction with the specified name, view, manager, server, priority, and room ID.
     *
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param name The name of the player joining the game.
     * @param server The RMIServer where this action is executed.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public JoinAction(VirtualView view, MainController manager, String name, RMIServer server, int priority, int roomId){
        super( view, manager, server, priority);
        this.roomId = roomId;
        this.name = name;
    }

    /**
     * Executes this action.
     * The player joins the game.
     */
    public void executor() {
        new Thread(() -> {
            try {
                getManager().joinGame(name, getView(), roomId);
            } catch (RemoteException | NotBoundException e) {
                System.out.println("There has been a problem in the execution of join game action");
            }
        }).start();
    }
}
