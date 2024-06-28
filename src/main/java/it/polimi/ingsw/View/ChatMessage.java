package it.polimi.ingsw.View;

import java.io.Serializable;
/**
 * Represents a chat message in the application. This class is used to store the details of a chat message, including the sender, recipient, and the message itself.
 * This class implements Serializable, allowing instances of this class to be written to streams.
 */
public class ChatMessage implements Serializable {
    /**
     * The message content.
     */
    private String message;
    /**
     * The sender of the message.
     */
    private String sender;
    /**
     * The recipient of the message.
     */
    private String recipient;
    /**
     * Constructs a new ChatMessage with the specified message, sender, and recipient.
     *
     * @param message The message content.
     * @param sender The sender of the message.
     * @param recipient The recipient of the message.
     */
    public ChatMessage(String message, String sender, String recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
    /**
     * Returns the message content.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }
    /**
     * Sets the message content.
     *
     * @param message The new message content.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public String getSender() {
        return sender;
    }
    /**
     * Sets the sender of the message.
     *
     * @param sender The new sender of the message.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    /**
     * Returns the recipient of the message.
     *
     * @return The recipient of the message.
     */
    public String getRecipient() {
        return recipient;
    }
    /**
     * Sets the recipient of the message.
     *
     * @param receiver The new recipient of the message.
     */
    public void setRecipient(String receiver) {
        this.recipient = receiver;
    }
    /**
     * Checks if the message is visible to a specific client. The message is visible if the recipient is either the client or everyone.
     *
     * @param clientName The name of the client.
     * @return True if the message is visible to the client, false otherwise.
     */
    public boolean isVisibleTo(String clientName) {
        return recipient.equals("everyone") || recipient.equals(clientName);
    }
}
