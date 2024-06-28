package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.PlayingField;

/**
 * The UpdateFieldMessage class extends the Message class and represents a message that contains the updated playing field of the player.
 */
public class UpdateFieldMessage extends Message{
    /**
     * The name of the player.
     */
    public String name;
    /**
     * The updated playing field of the player.
     */
    public PlayingField pf;
    /**
     * Constructs a new UpdateFieldMessage with the specified name and playing field.
     *
     * @param name The name of the player.
     * @param pf The updated playing field of the player.
     */
    public UpdateFieldMessage(String name, PlayingField pf) {
        super("UpdateFieldMessage");
        this.name= name;
        this.pf=pf;
    }
    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the updated playing field of the player.
     *
     * @return The updated playing field of the player.
     */
    public PlayingField getPlayingField() {
        return pf;
    }
}