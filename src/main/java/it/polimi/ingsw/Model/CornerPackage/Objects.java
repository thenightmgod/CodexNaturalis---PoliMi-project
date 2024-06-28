package it.polimi.ingsw.Model.CornerPackage;

import java.io.Serializable;

/**
 * Represents specific objects that can be possessed by a corner.
 * These objects are implementations of the abstract interface CardRes.
 */
public enum Objects implements CardRes {
    /**
     * Represents a quill object.
     */
    QUILL("\uD83E\uDEB6"),
    /**
     * Represents an inkwell object.
     */
    INKWELL("\uD83E\uDED9"),
    /**
     * Represents a manuscript object.
     */
    MANUSCRIPT("\uD83D\uDCDC");
    /**
     * The short name of the object used for display purposes.
     */
    private final String shortName;
    /**
     * Returns the short name of the object
     * @return The short name of the object.
     */
    public String getShortName() {
        return shortName;
    }
    /**
     * Constructs a new object with the specified short name.
     * @param shortName The short name of the object.
     */
    Objects(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Compares two CardRes objects for equality.
     *
     * @param res The first CardRes object to be compared.
     * @param res1 The second CardRes object to be compared.
     * @return {@code true} if the two objects are equal, {@code false} otherwise.
     */
    public static boolean equals(CardRes res, CardRes res1) {
        return res1.equals(res);
    }

    /**
     * Generates a hash code value for a CardRes object.
     *
     * @param orient The orientation of the corner.
     * @param covered A boolean indicating whether the corner is covered.
     * @param res The CardRes object for which to generate the hash code.
     * @return The hash code value for the specified CardRes object.
     */
    public static int hash(Orientation orient, boolean covered, CardRes res) {
        return Objects.hash(orient, covered, res);
    }

    public Objects getName(){
        return this;
    }
}
