package it.polimi.ingsw.Model.CornerPackage;

/**
 * Represents the different types of resources that can be possessed by a corner on a card.
 * This enum implements the abstract interface {@code CardRes}.
 */
public enum Resources implements CardRes {
    /**
     * Represents the resource type for plants.
     */
    PLANT_KINGDOM,

    /**
     * Represents the resource type for animals.
     */
    ANIMAL_KINGDOM,

    /**
     * Represents the resource type for fungi.
     */
    FUNGI_KINGDOM,

    /**
     * Represents the resource type for insects.
     */
    INSECT_KINGDOM
}
