package it.polimi.ingsw.Actions;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
/**
 * The ChatMessageAction class extends Actions and represents a chat message action.
 */
public class ChatMessageAction extends Actions{
    /**
     * The ChatMessage associated with this ChatMessageAction.
     */
    ChatMessage message;
    /**
     * The ID of the room associated with this ChatMessageAction.
     */
    int roomId;
    /**
     * Constructs a new ChatMessageAction with the specified view, manager, priority, message, and room ID.
     *
     * @param view The VirtualView associated with this ChatMessageAction.
     * @param manager The MainController managing this ChatMessageAction.
     * @param priority The priority of this ChatMessageAction.
     * @param message The ChatMessage associated with this ChatMessageAction.
     * @param roomId The ID of the room associated with this ChatMessageAction.
     */
    public ChatMessageAction(VirtualView view, MainController manager, int priority, ChatMessage message, int roomId){
        super(view, manager, priority);
        this.message = message;
        this.roomId = roomId;
    }
    /**
     * Executes this ChatMessageAction.
     * It calls the sendChatMessage method of the GameController referred to this room.
     *
     * @throws RemoteException if there is an error executing the action remotely.
     */
    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.sendChatMessage(message);
    }
}
