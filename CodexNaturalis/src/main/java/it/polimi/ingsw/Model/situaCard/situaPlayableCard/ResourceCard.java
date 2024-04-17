package it.polimi.ingsw.Model.situaCard.situaPlayableCard;


import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;

public class ResourceCard extends PlayableCard {
    private final CardColor Color;
    private final int Points;
    public ResourceCard(int id,int tiporesource,CardColor c,int p){
        super(id,tiporesource);
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
