package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;

import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;

import java.util.LinkedList;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static it.polimi.ingsw.Model.CornerPackage.Resources.*;
import static it.polimi.ingsw.Model.CornerPackage.Resources.INSECT_KINGDOM;

/**
 * Represents the starting card used in the game.
 * Each player receives a StartCard in their first turn, and it will be the first card placed in their playing field.
 */
public class StartCard extends PlayableCard {

    /**
     * Constructs a new StartCard.
     *
     * @param id          the specific identifier of the StartCard
     * @param R           the array of 4 positions, each representative of a resource (in the same order of the enum "Resources"),
     *                    containing in each position the number of that resource that the StartCard has in its back.
     * @param Corners the list of four corners that each StartCard has
     */
    public StartCard(int id, boolean[] R, LinkedList<Corner> Corners) {
        super(id, R, Corners);
    }
}