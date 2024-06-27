package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.View.ChatMessage;
/**
 * The ChatMessageMessage class extends the Message class and represents a chat message in the game.
 * Each ChatMessageMessage has a message and the name of the player who sent it.
 */
public class ChatMessageMessage extends Message{
    /**
     * The message associated with this ChatMessage.
     */
    public ChatMessage message;
    /**
     * The name of the player who sent the message.
     */
    public String name;
    /**
     * Constructs a new ChatMessageMessage with the specified message and the name of the player who sent it.
     *
     * @param message The message to be associated with this ChatMessageMessage.
     * @param name The name of the player who sent the message.
     */
    public ChatMessageMessage(ChatMessage message, String name){
        super("ChatMessageMessage");
        this.message = message;
        this.name = name;
    }
    /**
     * Returns the message associated with this ChatMessage.
     *
     * @return The message associated with this ChatMessage.
     */
    public ChatMessage getMessage(){
        return message;
    }
    /**
     * Returns the name of the player who sent the message.
     *
     * @return The name of the player who sent the message.
     */
    public String getName(){
        return name;
    }
}
