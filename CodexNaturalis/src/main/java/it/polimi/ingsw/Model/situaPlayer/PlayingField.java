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

    public Orientation getOppFromCorner(Orientation Orient){
        return switch (Orient) {
            case HR -> Orientation.LL;
            case HL -> Orientation.LR;
            case LR -> Orientation.HL;
            case LL -> Orientation.HR;
        };
    }

    public void updateFreePositions(Position p){
        FreePositions.remove(p);
        for(Orientation o: Orientation.values()){
            checkCorners(p, o);
        }
        return;
    }

    public void checkCorners(Position p, Orientation o) {
        if (Field.get(p).getCorner(o).getRes().equals(ABSENT)) {
            Position x = getPosFromCorner(p, o);
            Field.remove(x);
        }
        else {
            Position front = getPosFromCorner(p, o);
            if(Field.containsKey(front)){
                return;
            }
            else{
                //Orientation lazz = getOppFromCorner(o);  angolo da non controllare ma sti cazzi
                for(Orientation Orien : Orientation.values()) {           //per tutti gli altri
                    if (Field.containsKey(getPosFromCorner(front, Orien))) {   //se c'è carta alla posizione di fronte all'angolo
                        Position toCheck = getPosFromCorner(front, Orien);    // posizione da checkare
                        if ((Field.get(toCheck).getCorner(getOppFromCorner(Orien))).getCardRes().equals(ABSENT)) //vediamo se l'angolo opposto a quello che stiamo controllando, nella carta in pos toCheck ha effettivamente l'angolo
                            return;
                    }
                }
                FreePositions.add(front);
            }
        }
        return;
    }


}
