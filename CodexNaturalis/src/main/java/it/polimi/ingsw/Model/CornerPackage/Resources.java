package it.polimi.ingsw.Model.CornerPackage;

import java.io.Serializable;

/**
 * Represents the different types of resources that can be possessed by a corner on a card.
 * This enum implements the abstract interface {@code CardRes}.
 */
public enum Resources implements CardRes, Serializable {
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

    private final String shortName;

    public String getShortName() {
        return shortName;
    }

    Resources(String shortName) {
        this.shortName = shortName;
    }

    public Resources getName(){
        return this;
    }

    }
