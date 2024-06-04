package it.polimi.ingsw.Model.CornerPackage;

import java.io.Serializable;

/**
 * Enumeration representing the state of a corner on a card.
 * This enum implements the {@link CardRes} interface, indicating that it represents what an angle possesses or not.
 * An angle can be either EMPTY or ABSENT.
 */
public enum CornerState implements CardRes {
    /**
     * Empty is when the angle does not contain any resource or object.
     */
    EMPTY("\uD83C\uDD93"),
    /**
     * Absent is when the angle is not present.
     */
    ABSENT("⬛");

    private final String shortName;

    public String getShortName() {
        return shortName;
    }

    CornerState(String shortName) {
        this.shortName = shortName;
    }

    public CornerState getName() {
        return this;
    }
}