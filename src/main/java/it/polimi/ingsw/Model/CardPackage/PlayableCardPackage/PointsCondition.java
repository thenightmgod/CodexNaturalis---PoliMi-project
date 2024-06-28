package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;

/**
 * Enumerates the conditions associated with GoldCards.
 * GoldCards can provide points to the player based on certain conditions. The points
 * are awarded when the player meets these conditions upon placing the GoldCard on their
 * playing field.
 */
public enum PointsCondition {
    /**
     * The GoldCard provides points freely, without any specific condition.
     */
    FREE("   "),

    /**
     * The GoldCard provides points based on the positions of its corners.
     */
    CORNERS("\uD83D\uDD33"),

    /**
     * The GoldCard provides points based on the number of quills the player possesses.
     */
    OBJECTS_QUILL("\uD83E\uDEB6"),

    /**
     * The GoldCard provides points based on the number of inkwells the player possesses.
     */
    OBJECTS_INKWELL("\uD83E\uDED9"),

    /**
     * The GoldCard provides points based on the number of manuscripts the player possesses.
     */
    OBJECTS_MANUSCRIPT("\uD83D\uDCDC");
    /**
     * The short name of the PointsCondition used for display purposes.
     */
    private final String shortName;
    /**
     * Returns the short name of the PointsCondition.
     * @return The short name of the PointsCondition.
     */
    public String getShortName() {
        return shortName;
    }
    /**
     * Constructs a new PointsCondition with the specified short name.
     * @param shortName The short name of the PointsCondition.
     */
    PointsCondition(String shortName) {
        this.shortName = shortName;
    }

}