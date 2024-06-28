package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;
/**
 * The UpdateTurnMessage class extends the Message class and represents a message that contains the player that has the turn and a message.
 */
public class UpdateTurnMessage extends Message{
    /**
     * The player that has the turn.
     */
    public Player player;
    /**
     * The message (StartCard if the player has to choose the face of the start card, GoalCard if the player has to choose his goal card, NormalTurn if the player has the turn in the game).
     */
    public String mex;
    /**
     * Constructs a new UpdateTurnMessage with the specified player and message.
     *
     * @param p The player that has the turn.
     * @param mex The message (StartCard if the player has to choose the face of the start card, GoalCard if the player has to choose his goal card, NormalTurn if the player has the turn in the game).
     */
    public UpdateTurnMessage(Player p, String mex){
        super("UpdateTurnMessage");
        this.player = p;
        this.mex = mex;
    }
    /**
     * Returns the player that has the turn.
     *
     * @return The player that has the turn.
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Returns the message.
     *
     * @return The message (StartCard if the player has to choose the face of the start card, GoalCard if the player has to choose his goal card, NormalTurn if the player has the turn in the game).
     */
    public String getMex() {
        return mex;
    }
}
