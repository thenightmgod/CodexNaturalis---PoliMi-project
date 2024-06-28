package it.polimi.ingsw.Model.Messages;
import com.google.gson.Gson;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

/**
 * The CreateGameMessage class extends the Message class and represents a message that is sent when a player creates a new game.
 * Each CreateGameMessage has a name and the number of players that will play the game.
 */
public class CreateGameMessage extends Message{
    /**
     * The name of the player who created the game.
     */
    public String name;
    /**
     * The number of players that will play the game.
     */
    public int NumPlayers;
    /**
     * Constructs a new CreateGameMessage with the specified name and number of players.
     *
     * @param nickname The name of the player who created the game.
     * @param NumPlayers The number of players that will play the game.
     */
    public CreateGameMessage(String nickname, int NumPlayers) {
        super("CreateGameMessage");
        this.NumPlayers=NumPlayers;
        this.name=nickname;
    }
    /**
     * Returns the name of the player who created the game.
     *
     * @return The name of the player who created the game.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the number of players that will play the game.
     *
     * @return The number of players that will play the game.
     */
    public int getNumPlayers(){
        return NumPlayers;
    }

}
