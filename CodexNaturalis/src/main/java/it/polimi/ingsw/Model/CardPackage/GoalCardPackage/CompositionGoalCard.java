package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import  it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.ArrayList;
import java.util.Comparator;
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

    public CardColor getColor(){
        return color;
    }

    /**
     * Get the specific Composition
     * @return The specific Composition
     */
    public Composition getComp() {
        return comp;
    }


    /**
     * For every player, Calculates the points related to the GoalCards' achievement (both the individual one and the common one)
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
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        pos.sort(Comparator.comparing(Position :: getY));
        for(int i=0; i < pos.size(); i++){
            int x = pos.get(i).getX();
            int y = pos.get(i).getY();
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            if(!card.getColor().equals(CardColor.RED) || ((ResourceCard) field.getField().get(pos.get(i))).getCheck())
                continue;
            else{//aggiungi check
                Position under = new Position(x, y-2);
                if(!pos.contains(under))
                    continue;
                else{
                    if(!((ResourceCard) field.getField().get(under)).getColor().equals(CardColor.RED) || ((ResourceCard) field.getField().get(under)).getCheck())
                        continue;
                    else{
                        Position undright = new Position(x+1, y-1);
                        if(!pos.contains(undright))
                            continue;
                        else{
                            if(!((ResourceCard) field.getField().get(undright)).getColor().equals(CardColor.GREEN) || ((ResourceCard) field.getField().get(undright)).getCheck())
                                continue;
                            else{
                                Points += 3;
                                ((ResourceCard) field.getField().get(pos.get(i))).setCheck();
                                ((ResourceCard) field.getField().get(under)).setCheck();
                                ((ResourceCard) field.getField().get(undright)).setCheck();
                            }
                        }
                    }
                }
            }
        }
       return Points;
    }

    public int pointsRL(Player p){
        int Points = 0;
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        pos.sort(Comparator.comparing(Position :: getY));
        for(int i=0; i < pos.size(); i++){
            int x = pos.get(i).getX();
            int y = pos.get(i).getY();
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            if(!card.getColor().equals(CardColor.GREEN) || ((ResourceCard) field.getField().get(pos.get(i))).getCheck())
                break;
            else{//aggiungi check
                Position under = new Position(x, y-1);
                if(!pos.contains(under))
                    break;
                else{
                    if(!((ResourceCard) field.getField().get(under)).getColor().equals(CardColor.GREEN) || ((ResourceCard) field.getField().get(under)).getCheck())
                        break;
                    else{
                        Position undleft = new Position(x-1, y-2);
                        if(!pos.contains(undleft))
                            break;
                        else{
                            if(!((ResourceCard) field.getField().get(undleft)).getColor().equals(CardColor.PURPLE) || ((ResourceCard) field.getField().get(undleft)).getCheck())
                                break;
                            else{
                                Points += 3;
                                ((ResourceCard) field.getField().get(pos.get(i))).setCheck();
                                ((ResourceCard) field.getField().get(under)).setCheck();
                                ((ResourceCard) field.getField().get(undleft)).setCheck();
                            }
                        }
                    }
                }
            }
        }
        return Points;
    }

    public int pointsT(Player p){
        int Points = 0;
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        pos.sort(Comparator.comparing(Position :: getY));
        for(int i=0; i < pos.size(); i++){
            int x = pos.get(i).getX();
            int y = pos.get(i).getY();
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            if(!card.getColor().equals(CardColor.BLUE) || ((ResourceCard) field.getField().get(pos.get(i))).getCheck())
                break;
            else{//aggiungi check
                Position undleft = new Position(x-1, y-1);
                if(!pos.contains(undleft))
                    break;
                else{
                    if(!((ResourceCard) field.getField().get(undleft)).getColor().equals(CardColor.BLUE) || ((ResourceCard) field.getField().get(undleft)).getCheck())
                        break;
                    else{
                        Position under = new Position(x-1, y-2);
                        if(!pos.contains(under))
                            break;
                        else{
                            if(!((ResourceCard) field.getField().get(under)).getColor().equals(CardColor.BLUE) || ((ResourceCard) field.getField().get(under)).getCheck())
                                break;
                            else{
                                Points += 3;
                                ((ResourceCard) field.getField().get(pos.get(i))).setCheck();
                                ((ResourceCard) field.getField().get(undleft)).setCheck();
                                ((ResourceCard) field.getField().get(under)).setCheck();
                            }
                        }
                    }
                }
            }
        }
        return Points;
    }

    public int pointsRT(Player p){
        int Points = 0;
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        pos.sort(Comparator.comparing(Position :: getY));
        for(int i=0; i < pos.size(); i++){
            int x = pos.get(i).getX();
            int y = pos.get(i).getY();
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            if(!card.getColor().equals(CardColor.BLUE) || ((ResourceCard) field.getField().get(pos.get(i))).getCheck())
                break;
            else{//aggiungi check
                Position undright = new Position(x+1, y-1);
                if(!pos.contains(undright))
                    break;
                else{
                    if(!((ResourceCard) field.getField().get(undright)).getColor().equals(CardColor.PURPLE) || ((ResourceCard) field.getField().get(undright)).getCheck())
                        break;
                    else{
                        Position under = new Position(x+1, y-2);
                        if(!pos.contains(under))
                            break;
                        else{
                            if(!((ResourceCard) field.getField().get(under)).getColor().equals(CardColor.PURPLE  ) || ((ResourceCard) field.getField().get(under)).getCheck())
                                break;
                            else{
                                Points += 3;
                                ((ResourceCard) field.getField().get(pos.get(i))).setCheck();
                                ((ResourceCard) field.getField().get(undright)).setCheck();
                                ((ResourceCard) field.getField().get(under)).setCheck();
                            }
                        }
                    }
                }
            }
        }
        return Points;
    }

    public int pointsDU(Player p, CardColor color){
        int Points = 0;
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        pos.sort(Comparator.comparing(Position :: getY));
        for(int i=0; i < pos.size(); i++){
            int x = pos.get(i).getX();
            int y = pos.get(i).getY();
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            if(!card.getColor().equals(color) || ((ResourceCard) field.getField().get(pos.get(i))).getCheck())
                break;
            else{//aggiungi check
                Position topright = new Position(x+1, y+1);
                if(!pos.contains(topright))
                    break;
                else{
                    if(!((ResourceCard) field.getField().get(topright)).getColor().equals(color) || ((ResourceCard) field.getField().get(topright)).getCheck())
                        break;
                    else{
                        Position toprightright = new Position(x+2, y+2);
                        if(!pos.contains(toprightright))
                            break;
                        else{
                            if(!((ResourceCard) field.getField().get(toprightright)).getColor().equals(color) || ((ResourceCard) field.getField().get(toprightright)).getCheck())
                                break;
                            else{
                                Points += 3;
                                ((ResourceCard) field.getField().get(pos.get(i))).setCheck();
                                ((ResourceCard) field.getField().get(topright)).setCheck();
                                ((ResourceCard) field.getField().get(toprightright)).setCheck();
                            }
                        }
                    }
                }
            }
        }
        return Points;
    }

    public int pointsDD(Player p, CardColor color){
        int Points = 0;
        PlayingField field = p.getPlayerField();
        List<Position> pos = new ArrayList<>(field.getField().keySet().stream().toList());
        pos.remove(new Position(0, 0));
        pos.sort(Comparator.comparing(Position :: getY));
        for(int i=0; i < pos.size(); i++){
            int x = pos.get(i).getX();
            int y = pos.get(i).getY();
            ResourceCard card = (ResourceCard) field.getField().get(pos.get(i));
            if(!card.getColor().equals(color) || ((ResourceCard) field.getField().get(pos.get(i))).getCheck())
                break;
            else{//aggiungi check
                Position undright = new Position(x-1, y-1);
                if(!pos.contains(undright))
                    break;
                else{
                    if(!((ResourceCard) field.getField().get(undright)).getColor().equals(color) || ((ResourceCard) field.getField().get(undright)).getCheck())
                        break;
                    else{
                        Position undrightright = new Position(x-2, y-2);
                        if(!pos.contains(undrightright))
                            break;
                        else{
                            if(!((ResourceCard) field.getField().get(undrightright)).getColor().equals(color) || ((ResourceCard) field.getField().get(undrightright)).getCheck())
                                break;
                            else{
                                Points += 3;
                                ((ResourceCard) field.getField().get(pos.get(i))).setCheck();
                                ((ResourceCard) field.getField().get(undright)).setCheck();
                                ((ResourceCard) field.getField().get(undrightright)).setCheck();
                            }
                        }
                    }
                }
            }
        }
        return Points;
    }

}