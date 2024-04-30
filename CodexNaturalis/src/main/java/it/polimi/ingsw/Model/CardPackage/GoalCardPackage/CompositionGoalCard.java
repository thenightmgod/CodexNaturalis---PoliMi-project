package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import  it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.ingsw.Model.CardPackage.GoalCardPackage.Composition.T;

/**
 * Represents the GoalCard whose goal requires a specific configuration
 * of cards' positions on the playing field.
 */
public class CompositionGoalCard extends GoalCard {

    private final Composition comp;
    private final CardColor color;

    /**
     * Constructs a new CompositionGoalCard, with its id, points and Composition.
     * @param id
     * @param points
     * @param c The specific composition between the three possible
     * @param color the specific color of the CompositionGoalCard
     */
    public CompositionGoalCard(int id, int points, Composition c, CardColor color) {
        super(id,points);
        this.comp=c;
        this.color=color;
    }

    /**
     * Get the specific Composition
     * @return The specific Composition
     */
    public Composition getComp() {
        return comp;
    }


    /**
     * For every player, Calculate the points related to the GoalCards' achievement (both the individual one and the common one)
     *
     *
     * @param p The player whose Points
     *         related to GoalCards' achievement
     *          we need to calculate
     * @return the total Goalpoints of the Player
     */
    //non posso realizzarla fino a quando non so come funziona precisamente player e il suo campo da gioco
    public int pointsCalc(Player p, CardColor c){
        return switch (comp){
            case L -> pointsL(p);
            case REVERSE_L -> pointsRL(p);
            case T -> pointsT(p);
            case REVERSE_T -> pointsRT(p);
            case DIAGONAL_UP -> pointsDU(p, c);
            case DIAGONAL_DOWN -> pointsDD(p, c);
        };
    }

    public int pointsL(Player p){
        int Points = 0;
        int quanti = 0;
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        for(int i=0; i < pos.size(); i++){
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            /*if(!card.getCheck()){
                if(card.
            } */
        }
        return Points;
    }

    public int pointsRL(Player p){
        int Points = 0;
        return Points;
    }

    public int pointsT(Player p){
        int Points = 0;
        return Points;
    }

    public int pointsRT(Player p){
        int Points = 0;
        return Points;
    }

    public int pointsDU(Player p, CardColor c){
        int Points = 0;
        return Points;
    }

    public int pointsDD(Player p, CardColor c){
        int Points = 0;
        return Points;
    }
}