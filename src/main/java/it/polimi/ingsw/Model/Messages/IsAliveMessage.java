package it.polimi.ingsw.Model.Messages;
/**
 * The IsAliveMessage class extends the Message class and represents a message that is sent to notify that a player is still in the game.
 * Each IsAliveMessage has a name and a message.
 */
public class IsAliveMessage extends Message {
    /**
     * The name of the player who is still in the game.
     */
    public String name;
    /**
     * The message associated with this IsAliveMessage.
     */
    public String message;
    /**
     * Constructs a new IsAliveMessage with the specified name and message.
     *
     * @param name The name of the player who is still in the game.
     * @param message The message associated with this IsAliveMessage.
     */
    public IsAliveMessage(String name, String message) {
        super("IsAliveMessage");
        this.name = name;
        this.message = message;
    }
    /**
     * Returns the name of the player who is still in the game.
     *
     * @return The name of the player who is still in the game.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the message associated with this IsAliveMessage.
     *
     * @return The message associated with this IsAliveMessage.
     */
    public String getMessage() {
        return message;
    }
}
