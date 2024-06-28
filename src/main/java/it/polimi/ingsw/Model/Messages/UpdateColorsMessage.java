package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.util.LinkedList;
/**
 * The UpdateColorsMessage class extends the Message class and represents a message that contains the colors that a player can choose.
 */
public class UpdateColorsMessage extends Message{
    /**
     * The player whose turn is.
     */
    public Player turn;
    /**
     * The colors that a player can choose.
     */
    public LinkedList<PlayerColor> colors;
    /**
     * Constructs a new UpdateColorsMessage with the specified player and colors.
     *
     * @param turn The player whose turn is.
     * @param colors The colors that a player can choose.
     */
    public UpdateColorsMessage(Player turn, LinkedList<PlayerColor> colors){
        super("UpdateColorsMessage");
        this.turn = turn;
        this.colors = colors;
    }

    /**
     * Returns the player whose turn is.
     *
     * @return The player whose turn is.
     */
    public Player getTurn(){
        return turn;
    }
    /**
     * Returns the colors that a player can choose.
     *
     * @return The colors that a player can choose.
     */
    public LinkedList<PlayerColor> getColors(){
        return colors;
    }
}
