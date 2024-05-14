package it.polimi.ingsw.Model.Messages;

import java.io.Serializable;
/**
 * The Message class  is an abstract class that represents a generic message.
 * It is used as a base interface for all types of messages in the application.
 * It implements Serializable.
 */

public abstract class Message implements Serializable {
    public String type;
    public String name;

    public Message(String type, String nickname) {
        this.type=type;
        this.name= nickname;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
}