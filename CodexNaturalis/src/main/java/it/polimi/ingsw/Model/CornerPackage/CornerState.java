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
    /**
     * The short name of the corner state used for display purposes.
     */
    private final String shortName;
    /**
     * Returns the short name of the corner state.
     * @return The short name of the corner state.
     */
    public String getShortName() {
        return shortName;
    }
    /**
     * Constructs a new corner state with the specified short name.
     * @param shortName The short name of the corner state.
     */
    CornerState(String shortName) {
        this.shortName = shortName;
    }
    /**
     * Returns the name of the corner state.
     * @return The name of the corner state.
     */
    public CornerState getName() {
        return this;
    }
}