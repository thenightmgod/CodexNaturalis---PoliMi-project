package it.polimi.ingsw.Model.CornerPackage;

/**
 * Enumeration representing the state of a corner on a card.
 * This enum implements the {@link CardRes} interface, indicating that it represents what an angle possesses or not.
 * An angle can be either EMPTY or ABSENT.
 */
public enum CornerState implements CardRes {
    /**
     * Empty is when the angle does not contain any resource or object.
     */
    EMPTY, // Represents an empty corner.
    /**
     * Absent is when the angle is not present.
     */
    ABSENT // Represents a corner that is absent.
}