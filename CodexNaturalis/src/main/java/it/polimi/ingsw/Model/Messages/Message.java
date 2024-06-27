package it.polimi.ingsw.Model.Messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.Gson;


import java.io.Serializable;

//import static com.sun.management.HotSpotDiagnosticMXBean.ThreadDumpFormat.JSON;

/**
 * The Message class  is an abstract class that represents a generic message.
 * It is used as a base interface for all types of messages in the application.
 * It implements Serializable.
 */

public class Message implements Serializable {
    /**
     * The type of the message.
     */
    public String type;
    /**
     * Constructs a new Message with the specified type.
     *
     * @param type The type of the message.
     */
    public Message(String type) {
        this.type=type;
    }
    /**
     * Returns the type of the message.
     *
     * @return The type of the message.
     */
    public String getType() {
        return type;
    }
    /**
     * Converts the message to a JSON string.
     *
     * @return The JSON string representing the message.
     */
    public String MessageToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}