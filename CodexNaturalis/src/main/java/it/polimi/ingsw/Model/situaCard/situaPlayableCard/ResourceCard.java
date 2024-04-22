package it.polimi.ingsw.Model.situaCard.situaPlayableCard;


import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.Resources;

import java.util.LinkedList;
import java.util.Arrays;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceCard ID: ").append(getId()).append("\n");
        sb.append("Color: ").append(Color).append("\n");
        sb.append("Points: ").append(Points).append("\n");
        sb.append("BackRes: ").append(getBackRes()).append("\n");
        sb.append("Corners:").append("\n");

        // Itera su ogni valore di Orientation
        for (Orientation orientation : Orientation.values()) {
            // Ottieni l'angolo per l'orientamento corrente
            Corner corner = getCorner(orientation);

            // Aggiungi le informazioni sull'angolo alla stringa di output
            sb.append(orientation).append(": ").append(corner.getRes()).append("\n");
        }
        return sb.toString();
    }

}
