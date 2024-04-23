package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

/**
 * Enumerates the conditions associated with GoldCards.
 *
 * GoldCards can provide points to the player based on certain conditions. The points
 * are awarded when the player meets these conditions upon placing the GoldCard on their
 * playing field.
 */
public enum PointsCondition {
    /**
     * The GoldCard provides points freely, without any specific condition.
     */
    FREE,

    /**
     * The GoldCard provides points based on the positions of its corners.
     */
    CORNERS,

    /**
     * The GoldCard provides points based on the number of quills the player possesses.
     */
    OBJECTS_QUILL,

    /**
     * The GoldCard provides points based on the number of inkwells the player possesses.
     */
    OBJECTS_INKWELL,

    /**
     * The GoldCard provides points based on the number of manuscripts the player possesses.
     */
    OBJECTS_MANUSCRIPT
}