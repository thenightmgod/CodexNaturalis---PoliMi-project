package it.polimi.ingsw.Chat;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private String message;
    private String sender;
    private String recipient;

    public ChatMessage(String message, String sender, String recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String receiver) {
        this.recipient = receiver;
    }
}
