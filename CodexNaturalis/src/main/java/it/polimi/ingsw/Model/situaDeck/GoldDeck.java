package it.polimi.ingsw.Model.situaDeck;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.GoldCard;

/**
 * Represents a deck of Gold Cards used in the game. Each Gold Card in the deck
 * corresponds to a JSON object with specific attributes:
 * - "Id": the unique identifier of the Gold Card.
 * - "BackRes": an array indicating the presence of resources on the back side of the card.
 *              Each position corresponds to a resource in the order of the enum "Resources".
 * - "PointsC": a condition determining how points are calculated when the card is played.
 * - "requirements": an array specifying the resource requirements to play the card.
 *                   Each position corresponds to a resource in the order of the enum "Resources".
 * - "Color": the color of the card.
 * - "Points": the number of points awarded when the card is played.
 * - "Corners": a list of four corners on the card, each represented by an object with the following attributes:
 *              - "Res": the resource or state present in the corner, based on the enum "CardRes".
 *              - "Orient": the orientation of the corner (HR, HL, LR, LL).
 *
 * @see Deck
 * @see GoldCard
 */
public class GoldDeck extends Deck{

    /**
     * Constructs a new GoldDeck.
     */
    public GoldDeck(){
        super("Gold");
    }
}
