package it.polimi.ingsw.Model.situaPlayer;

import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaCorner.CardRes;
import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;

import java.util.*;

import static it.polimi.ingsw.Model.situaCorner.CornerState.ABSENT;
public class PlayingField {

    private HashMap<Position, PlayableCard> Field;
    private LinkedList<Position> FreePositions;

    public PlayingField(){
        Field = new HashMap<>();
        FreePositions = new LinkedList<Position>();
    }

    public LinkedList<Position> getFreePositions(){
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

    public HashMap<Position, PlayableCard> getField() {
        return Field;
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
    }

    public void checkCorners(Position p, Orientation o) {
        if (Field.get(p).getCorner(o).getRes().equals(ABSENT)) {
            Position x = getPosFromCorner(p, o);
            FreePositions.remove(x);
        }
        else {
            Position front = getPosFromCorner(p, o);
            if(!Field.containsKey(front)){
                //Orientation Lazzaro = getOppFromCorner(o);  angolo da non controllare ma sti cazzi
                for(Orientation Orien : Orientation.values()) {//per tutti gli altri
                    Position toCheck = getPosFromCorner(front, Orien);  // una delle 4 posizioni che circondano la potenziale freeposition
                    if (Field.containsKey(toCheck)) {   //se c'è carta alla posizione di fronte all'angolo
                        Orientation opposto = getOppFromCorner(Orien);
                        Corner diFronte = Field.get(toCheck).getCorner(opposto); //corner di fronte
                        if (diFronte.getRes().equals(ABSENT))  //vediamo se l'angolo opposto a quello che stiamo controllando, nella carta in pos toCheck ha effettivamente l'angolo
                            return;
                    }
                }
                FreePositions.add(front);
            }
        }
    }


}
