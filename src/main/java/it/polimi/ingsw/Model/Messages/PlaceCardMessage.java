package it.polimi.ingsw.Model.Messages;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.CommonClient;

/**
 * The PlaceCardMessage class extends the Message class and represents a message that is sent when a player places a card on the board.
 * Each PlaceCardMessage has a name, the card to place, the x and y coordinates of the cell where the card is placed and the face of the card.
 */
public class PlaceCardMessage extends Message{
    /**
     * The card to place.
     */
   public int whichInHand;
   /**
    * The x coordinate of the cell where the card is placed.
    */
   public int x;
   /**
    * The y coordinate of the cell where the card is placed.
    */
   public int y;
   /**
    * The face of the card.
    */
   public FB face;
   /**
    * The name of the player who placed the card.
    */
   public String name;
   /**
    * Constructs a new PlaceCardMessage with the specified name, card to place, x and y coordinates and face.
    *
    * @param name The name of the player who placed the card.
    * @param whichInHand The card to place.
    * @param x The x coordinate of the cell where the card is placed.
    * @param y The y coordinate of the cell where the card is placed.
    * @param face The face of the card.
    */
   public PlaceCardMessage(String name, int whichInHand, int x, int y, FB face) {
       super("PlaceCardMessage");
       this.whichInHand=whichInHand;
       this.x=x;
       this.y=y;
       this.face=face;
       this.name = name;
   }
    /**
     * Returns the face of the card to place.
     *
     * @return The face of the card to place.
     */
   public FB getFace() {
        return face;
    }
    /**
     * Returns the index of the card to place.
     *
     * @return The index of the card to place.
     */
    public int getWhichInHand() {
        return whichInHand;
    }
    /**
     * Returns the x coordinate of the cell where the card is placed.
     *
     * @return The x coordinate of the cell where the card is placed.
     */
    public int getX() {
        return x;
    }
    /**
     * Returns the y coordinate of the cell where the card is placed.
     *
     * @return The y coordinate of the cell where the card is placed.
     */
    public int getY() {
        return y;
    }
    /**
     * Returns the name of the player who placed the card.
     *
     * @return The name of the player who placed the card.
     */
    public String getName() {
        return name;
    }
}
