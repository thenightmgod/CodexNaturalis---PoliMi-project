package it.polimi.ingsw.Model.Messages;
import com.google.gson.Gson;
import it.polimi.ingsw.Model.PlayerPackage.*;
/**
 * The JoinExistingGameMessage class extends the Message class and represents a message that is sent when a player wants to join an existing game.
 * Each JoinExistingGameMessage has the name of the player who wants to join.
 */
public class JoinExistingGameMessage extends Message{
    /**
     * The name of the player who wants to join.
     */
    public String name;

    /**
     * Constructs a new JoinExistingGameMessage with the specified name.
     *
     * @param nickname The name of the player who wants to join.
     */
    public JoinExistingGameMessage(String nickname) {
        super("JoinExistingGameMessage");
        this.name=nickname;
    }
    /**
     * Returns the name of the player who wants to join.
     *
     * @return The name of the player who wants to join.
     */
    public String getName() {
        return name;
    }
}
