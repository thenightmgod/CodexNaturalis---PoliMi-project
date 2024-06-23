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
    public String type;

    public Message(String type) {
        this.type=type;
    }
    public String getType() {
        return type;
    }

    public String MessageToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}