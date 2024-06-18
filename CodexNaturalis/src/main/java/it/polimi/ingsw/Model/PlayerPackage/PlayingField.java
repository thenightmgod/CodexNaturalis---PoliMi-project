package it.polimi.ingsw.Model.PlayerPackage;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CornerPackage.Orientation;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
/**
 * Represents the playing field where cards are placed during the game.
 */
public class PlayingField implements Serializable {

    private HashMap<Position, PlayableCard> Field;
    private LinkedList<Position> FreePositions;


    /**
     * Constructs a new playing field with an empty map of positions and cards, and an empty list of free positions.
     */

    public PlayingField(){
        Field = new HashMap<>();
        FreePositions = new LinkedList<>();
    }

    public boolean containsFreePosition(Position p){
        for(Position pos : FreePositions){
            if(pos.equals(p)){
                return true;
            }
        }
        return false;
    }

    public PlayableCard getCardFromPos(Position p){
        Set<Position> positions = Field.keySet();
        for(Position pos : positions){
            if(pos.equals(p)){
                return Field.get(pos);
            }
        }
        return null;
    }

    public boolean containsInField(Position p){
        Set<Position> set = Field.keySet();
        for(Position p1 : set){
            if(p.equals(p1)){
                return true;
            }
        }
        return false;
    }

    public Position getPosition(int x, int y){
        Position position = new Position(x, y);
        List<Position> k = Field.keySet().stream().filter(s -> s.equals(position)).toList();
        return k.getFirst();
    }

    /**
     * Retrieves the list of positions on the playing field that are currently free.
     * @return The list of free positions.
     */
    public LinkedList<Position> getFreePositions(){
        return FreePositions;
    }

    /**
     * Retrieves the position adjacent to a given position in a specified orientation.
     * @param p The starting position.
     * @param Orient The orientation of the adjacent position.
     * @return The position adjacent to the starting position in the specified orientation.
     */
    public Position getPosFromCorner(Position p, Orientation Orient){
        Position newPosition;
        return switch (Orient) {
            case HR -> (newPosition = new Position( p.getX()+1, p.getY()+1));
            case HL -> (newPosition = new Position( p.getX()-1, p.getY()+1));
            case LR -> (newPosition = new Position(p.getX()+1, p.getY()-1));
            case LL -> (newPosition = new Position( p.getX()-1, p.getY()-1));
        };
    }

    /**
     * Retrieves the map containing the positions and corresponding cards on the playing field.
     * @return The map of positions and cards.
     */
    public HashMap<Position, PlayableCard> getField() {
        return Field;
    }

    /**
     * Retrieves the opposite orientation of a given orientation.
     * @param Orient The starting orientation.
     * @return The opposite orientation.
     */
    public Orientation getOppFromCorner(Orientation Orient){
        return switch (Orient) {
            case HR -> Orientation.LL;
            case HL -> Orientation.LR;
            case LR -> Orientation.HL;
            case LL -> Orientation.HR;
        };
    }

    /**
     * Updates the list of free positions on the playing field after placing a card.
     * @param p The position where the card was placed.
     */
    public void updateFreePositions(Position p){
        FreePositions.remove(p);
        for(Orientation o: Orientation.values()){
            checkCorners(p, o);
        }
    }

    /**
     * Checks the adjacent corners of a specified position and updates the list of free positions accordingly.
     * @param p The position to check.
     * @param o The orientation of the position.
     */
    public void checkCorners(Position p, Orientation o) {
        if (Field.get(p).getCorner(o).getRes().equals(ABSENT)){
            Position x = getPosFromCorner(p, o);
            FreePositions.remove(x);
        }
        else {
            Position front = getPosFromCorner(p, o);
            if(!Field.containsKey(front)){
                //Orientation Lazzaro = getOppFromCorner(o);  angolo da non controllare ma sti cazzi
                for(Orientation Orien : Orientation.values()) {//per tutti gli altri
                    Position toCheck = getPosFromCorner(front, Orien);  // posizione da checkare
                    if (Field.containsKey(toCheck)) {   //se c'è carta alla posizione di fronte all'angolo
                        if ((Field.get(toCheck).getCorner(getOppFromCorner(Orien))).getRes().equals(ABSENT)) //vediamo se l'angolo opposto a quello che stiamo controllando, nella carta in pos toCheck ha effettivamente l'angolo
                            return;
                    }
                }
                FreePositions.add(front);
            }
        }
    }
}
