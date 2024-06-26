package it.polimi.ingsw.Actions;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;

public class ChatMessageAction extends Actions{

    ChatMessage message;
    int roomId;

    public ChatMessageAction(VirtualView view, MainController manager, int priority, ChatMessage message, int roomId){
        super(view, manager, priority);
        this.message = message;
        this.roomId = roomId;
    }

    @Override
    public void executor() throws RemoteException {
        GameController controller = getManager().getControllers().get(roomId);
        controller.sendChatMessage(message);
    }
}
