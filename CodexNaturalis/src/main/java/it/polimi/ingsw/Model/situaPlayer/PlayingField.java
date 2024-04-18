package it.polimi.ingsw.Model.situaPlayer;

import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaCorner.*;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.CornerState;

import java.util.*;

import static it.polimi.ingsw.Model.situaCorner.CornerState.ABSENT;


public class PlayingField {

    private HashMap<Position, PlayableCard> Field;
    private Set<Position> FreePositions;

    public PlayingField(){
        Field = new HashMap<>();
        FreePositions = new LinkedHashSet<Position>();
    }

    public Set<Position> getFreePositions(){
        return FreePositions;
    }

    public Position getPosFromCorner(Position p, Orientation Orient){
        Position newPosition;
        return switch (Orient) {
            case HR -> (newPosition = new Position( p.getX()+1, p.getY()+1));
            case HL -> (newPosition = new Position( p.getX()-1, p.getY()+1));
            case LR -> (newPosition = new Position(p.getX()+1, p.getY()-1));
            case LL -> (newPosition = new Position( p.getX()-1, p.getY()-1));
        };
    }

    public Orientation getOppFromCorner(Position p, Orientation Orient){
        Position newPosition;
        return switch (Orient) {
            case HR -> Orientation.LL;
            case HL -> Orientation.LR;
            case LR -> Orientation.HL;
            case LL -> Orientation.HR;
        };
    }

    public void updateFreePositions(Position p){
        FreePositions.remove(p);
        return;
    }

    public void CheckCorners(Position p, Corner c){
        if(c.getRes().equals(ABSENT)){
            Position x;
            x=getPosFromCorner(p,c.getOrientation());
            Field.remove(x);
        }

}
