package it.polimi.ingsw.Model.situaPlayer;

import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;

import java.util.HashMap;
import java.util.LinkedList;

public class PlayingField {

    private HashMap<Position, PlayableCard> Field;
    public PlayingField(){
        Field = new HashMap<>();
    }

    public LinkedList<Position> showFreePositions(){
        LinkedList<Position> p = new LinkedList<Position>();

        //todo
        //facciamo che ritorna un array di positions
        return p;
    }
}
