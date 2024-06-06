package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

/**
 * Enumerates the six possible compositions of cards that appear in the CompositionGoalCard
 * */

public enum Composition {
    /**
     * Represents the composition with these coordinates(starting from the card on top)
     * X, Y
     * X, Y-2
     * X+1, Y-3
     */
    L,
    /**
     * Represents the composition with these coordinates(starting from the card on top)
     * X, Y
     * X, Y-2
     * X-1, Y-3
     */
    REVERSE_L,
    /**
     * Represents the composition with these coordinates(starting from the card on top)
     * X, Y
     * X-1, Y-1
     * X-1, Y-3
     */
    T,
    /**
     * Represents the composition with these coordinates(starting from the card on top)
     * X, Y
     * X+1, Y-1
     * X+1, Y-3
     */
    REVERSE_T,
    /**
     * Represents the composition with these coordinates(starting from the card on the bottom)
     * X, Y
     * X+1, Y+1
     * X+2, Y+2
     */
    DIAGONAL_UP,
    /**
     * Represents the composition with these coordinates(starting from the card on top)
     * X, Y
     * X+1, Y-1
     * X+2, Y-2
     */
    DIAGONAL_DOWN
}
