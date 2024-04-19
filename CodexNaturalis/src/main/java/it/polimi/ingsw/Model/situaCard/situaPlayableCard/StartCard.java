package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Resources;

import java.util.LinkedList;

public class StartCard extends PlayableCard {
    public StartCard(int id, Resources R, LinkedList<Corner> Corners) {
        super(id, R, Corners);
    }
}
