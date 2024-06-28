package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.View.ChatMessage;

import java.util.LinkedList;
/**
 * The UpdateChatMessage class extends the Message class and represents a message that contains the updated chat of the player.
 */
public class UpdateChatMessage extends Message{
    /**
     * The name of the player.
     */
    public String name;
    /**
     * The updated chat of the player.
     */
    public LinkedList<ChatMessage> chat;
    /**
     * Constructs a new UpdateChatMessage with the specified name and chat.
     *
     * @param name The name of the player.
     * @param chat The updated chat of the player.
     */
    public UpdateChatMessage(String name, LinkedList<ChatMessage> chat){
        super("UpdateChatMessage");
        this.name = name;
        this.chat = chat;
    }
    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName(){
        return this.name;
    }
    /**
     * Returns the updated chat of the player.
     *
     * @return The updated chat of the player.
     */
    public LinkedList<ChatMessage> getChat(){
        return this.chat;
    }

}
