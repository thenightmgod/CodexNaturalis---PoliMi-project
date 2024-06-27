package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
/**
 * The EndColorMessage class extends the Message class and represents a message that is sent when a player chooses a color.
 * Each EndColorMessage has a name and the color chosen by the player.
 */
public class EndColorMessage extends Message{
    /**
     * The name of the player who chose the color.
     */
    public String name;
    /**
     * The color chosen by the player.
     */
    public PlayerColor color;
    /**
     * Constructs a new EndColorMessage with the specified name and color chosen by the player.
     *
     * @param name The name of the player who chose the color.
     * @param color The color chosen by the player.
     */
    public EndColorMessage(String name, PlayerColor color){
        super("EndColorMessage");
        this.name = name;
        this.color = color;
    }
    /**
     * Returns the name of the player who chose the color.
     *
     * @return The name of the player who chose the color.
     */
    public String getName(){
        return name;
    }
    /**
     * Returns the color chosen by the player.
     *
     * @return The color chosen by the player.
     */
    public PlayerColor getColor(){
        return color;
    }
}
