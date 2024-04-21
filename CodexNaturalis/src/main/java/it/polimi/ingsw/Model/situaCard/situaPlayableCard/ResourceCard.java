package it.polimi.ingsw.Model.situaCard.situaPlayableCard;


import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.Resources;

import java.util.LinkedList;

public class ResourceCard extends PlayableCard {
    private final CardColor Color;
    private final int Points;
    public ResourceCard(int id, boolean[] R, CardColor c, int p, LinkedList<Corner> Corners){
        super(id, R, Corners);
        this.Color=c;
        this.Points=p;
    }


    public CardColor getColor() {
        return Color;
    }

    public int getPoints() {
        return Points;
    }
}
