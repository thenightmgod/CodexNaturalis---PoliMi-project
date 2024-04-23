package it.polimi.ingsw.Model.situaCorner;

/**
 * Enumeration representing the state of a corner on a card.
 * This enum implements the {@link CardRes} interface, indicating that it represents what an angle possesses or not.
 * An angle can be either EMPTY or ABSENT.
 */
public enum CornerState implements CardRes {
    EMPTY, // Represents an empty corner.
    ABSENT // Represents a corner that is absent.
}