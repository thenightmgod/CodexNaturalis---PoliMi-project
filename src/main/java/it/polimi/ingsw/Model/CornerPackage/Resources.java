package it.polimi.ingsw.Model.CornerPackage;

import java.io.Serializable;

/**
 * Represents the different types of resources that can be possessed by a corner on a card.
 * This enum implements the abstract interface {@code CardRes}.
 */
public enum Resources implements CardRes{
    /**
     * Represents the resource type for plants.
     */
    PLANT_KINGDOM("\uD83C\uDF3F"),

    /**
     * Represents the resource type for animals.
     */
    ANIMAL_KINGDOM("\uD83E\uDECE"),

    /**
     * Represents the resource type for fungi.
     */
    FUNGI_KINGDOM("\uD83C\uDF44"),

    /**
     * Represents the resource type for insects.
     */
    INSECT_KINGDOM("\uD83E\uDD8B");
    /**
     * The short name of the resource used for display purposes.
     */
    private final String shortName;
    /**
     * Returns the short name of the resource.
     * @return The short name of the resource.
     */
    public String getShortName() {
        return shortName;
    }
    /**
     * Constructs a new resource with the specified short name.
     * @param shortName The short name of the resource.
     */
    Resources(String shortName) {
        this.shortName = shortName;
    }

    public Resources getName(){
        return this;
    }

    }
