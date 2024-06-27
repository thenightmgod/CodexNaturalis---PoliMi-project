package it.polimi.ingsw.Model.Messages;
/**
 * The EndTurnMessage class extends the Message class and represents a message that is sent when a player ends their turn.
 * Each EndTurnMessage has a name and a message.
 */
public class EndTurnMessage extends Message{
    /**
     * The name of the player who ended their turn.
     */
    public String name;
    /**
     * The message associated with this EndTurnMessage.
     */
    public String mex;
    /**
     * Constructs a new EndTurnMessage with the specified name and message.
     *
     * @param name The name of the player who ended their turn.
     * @param mex The message associated with this EndTurnMessage.
     */
    public EndTurnMessage(String name, String mex){
        super("EndTurnMessage");
        this.name = name;
        this.mex = mex;
    }
    /**
     * Returns the name of the player who ended their turn.
     *
     * @return The name of the player who ended their turn.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the message associated with this EndTurnMessage.
     *
     * @return The message associated with this EndTurnMessage.
     */
    public String getMex() {
        return mex;
    }
}
