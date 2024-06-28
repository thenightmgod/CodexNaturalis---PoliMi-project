package it.polimi.ingsw.Model.Messages;
/**
 * The TwentyMessage class extends the Message class and represents a message that contains the name of the player who has reached twenty points.
 */
public class TwentyMessage extends Message {
    /**
     * The name of the player who has reached twenty points.
     */
    public String name;
    /**
     * Constructs a new TwentyMessage with the specified name.
     *
     * @param name The name of the player who has reached twenty points.
     */
    public TwentyMessage(String name){
        super("TwentyMessage");
        this.name = name;
    }
    /**
     * Returns the name of the player who has reached twenty points.
     *
     * @return The name of the player who has reached twenty points.
     */
    public String getName() {
        return name;
    }
}
