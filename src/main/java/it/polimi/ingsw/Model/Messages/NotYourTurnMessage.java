package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;
/**
 * The NotYourTurnMessage class extends the Message class and represents a message that is sent to notify that is someone else's turn.
 * Each NotYourTurnMessage has a player and a message.
 */
public class NotYourTurnMessage extends Message{
    /**
     * The player who has the turn.
     */
    public Player p;
    /**
     * The message associated with this NotYourTurnMessage.
     */
    public String mex;
    /**
     * Constructs a new NotYourTurnMessage with the specified player and message.
     *
     * @param p The player who has the turn.
     * @param mess The message associated with this NotYourTurnMessage.
     */
    public NotYourTurnMessage(Player p, String mess){
        super("NotYourTurnMessage");
        this.p = p;
        this.mex = mess;
    }
    /**
     * Returns the message associated with this NotYourTurnMessage.
     *
     * @return The message associated with this NotYourTurnMessage.
     */
    public String getMex() {
        return mex;
    }
    /**
     * Returns the player who has the turn.
     *
     * @return The player who has the turn.
     */
    public Player getPlayer() {
        return p;
    }
}
