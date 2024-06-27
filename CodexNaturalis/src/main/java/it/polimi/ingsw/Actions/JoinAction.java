package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The JoinAction class extends Actions and represents an action where a player joins a game.
 */
public class JoinAction extends Actions {
    /**
     * The name of the player joining the game.
     */
    String name;
    /**
     * The ID of the room where this action is executed.
     */
    int roomId;

    /**
     * Constructs a new JoinAction with the specified name, view, manager, priority, and room ID.
     *
     * @param view The VirtualView associated with this action.
     * @param manager The MainController managing this action.
     * @param name The name of the player joining the game.
     * @param priority The priority of this action.
     * @param roomId The ID of the room where this action is executed.
     */
    public JoinAction(VirtualView view, MainController manager, String name, int priority, int roomId){
        super( view, manager,priority);
        this.roomId = roomId;
        this.name = name;
    }

    /**
     * Executes this JoinAction.
     * It calls the joinGame method of the MainController.
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
