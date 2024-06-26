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
    /**
     * A map representing the playing field, where each position is mapped to the card placed there.
     */
    private LinkedHashMap<Position, PlayableCard> Field;
    /**
     * A list of positions on the playing field that are currently free.
     */
    private LinkedList<Position> FreePositions;


    /**
     * Constructs a new playing field with an empty map of positions and cards, and an empty list of free positions.
     */

    public PlayingField(){
        Field = new LinkedHashMap<>();
        FreePositions = new LinkedList<>();
    }
    /**
     * Sets the playing field with a given map of positions and corresponding cards.
     * @param field The map of positions and corresponding cards.
     */
    public void setField(LinkedHashMap<Position, PlayableCard> field) {
        Field = field;
    }
    /**
     * Sets the list of free positions on the playing field.
     * @param freePositions The list of free positions.
     */
    public void setFreePositions(LinkedList<Position> freePositions) {
        FreePositions = freePositions;
    }
    /**
     * Checks if a given position is free on the playing field.
     * @param p The position to check.
     * @return true if the position is free, false otherwise.
     */
    public boolean containsFreePosition(Position p){
        for(Position pos : FreePositions){
            if(pos.equals(p)){
                return true;
            }
        }
        return false;
    }
    /**
     * Retrieves the card at a given position on the playing field.
     * @param p The position of the card.
     * @return The card at the given position, or null if there is no card at that position.
     */
    public PlayableCard getCardFromPos(Position p){
        Set<Position> positions = Field.keySet();
        for(Position pos : positions){
            if(pos.equals(p)){
                return Field.get(pos);
            }
        }
        return null;
    }
    /**
     * Checks if a given position is occupied on the playing field.
     * @param p The position to check
     */
    public boolean containsInField(Position p){
        Set<Position> set = Field.keySet();
        for(Position p1 : set){
            if(p.equals(p1)){
                return true;
            }
        }
        return false;
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
            Boolean pinotto = true;
            for(Position free : FreePositions){
                if(free.equals(front)){
                    pinotto = false;
                    break;
                }
            }
            if(pinotto){
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
