package it.polimi.ingsw.Model.PlayerPackage;

import java.util.*;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.*;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;

import static it.polimi.ingsw.Model.CornerPackage.Objects.*;
import static it.polimi.ingsw.Model.CornerPackage.Resources.ANIMAL_KINGDOM;
import static it.polimi.ingsw.Model.CornerPackage.Resources.PLANT_KINGDOM;
import static it.polimi.ingsw.Model.CornerPackage.Resources.FUNGI_KINGDOM;
import static it.polimi.ingsw.Model.CornerPackage.Resources.INSECT_KINGDOM;


/**
 * Represents a player in the game. Each player has a name, a color, and keeps track of their points, resources, objects, hand, player goal, and playing field.
 */
public class Player {
    //bisogna fare override della equals
    private final String Name;
    private final PlayerColor Color;
    private int PointsCounter;
    private int[] ResourceCounter;
    private int[] ObjectCounter;
    private LinkedList<PlayableCard> Hand;
    private GoalCard PlayerGoal;
    private PlayingField PlayerField;

    /**
     * Constructs a player with a given name and color.
     *
     * @param name  The name of the player.
     * @param color The color assigned to the player.
     */

    public Player(String name, PlayerColor color) {
        Name = name;
        Color = color;
        PointsCounter = 0;
        ResourceCounter = new int[4];
        Hand = new LinkedList<>();
        for(int i=0; i<4; i++){
            ResourceCounter[i]=0;
        }
        ObjectCounter = new int[3];
        for(int i=0; i<3; i++){
            ObjectCounter[i]=0;
        }
        PlayerField = new PlayingField();
    }


    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return Name;
    }


    /**
     * Retrieves the color assigned to the player.
     *
     * @return The color of the player.
     */
    public PlayerColor getColor() {
        return Color;
    }

    /**
     * Retrieves the total points accumulated by the player.
     *
     * @return The total points accumulated by the player.
     */
    public int getPointsCounter() {
        return PointsCounter;
    }

    /**
     * Sets the goal card for the player.
     *
     * @param card The goal card to be set for the player.
     */
    public void setPlayerGoal(GoalCard card){
        PlayerGoal = card;
    }

    /**
     * Retrieves the counter for a specific type of resource.
     *
     * @param R The resource for which the counter is to be retrieved.
     * @return The counter for the specified resource.
     */
    public int getResourceCounter(Resources R) {
        if (R == PLANT_KINGDOM)
            return ResourceCounter[0];
        else if (R == ANIMAL_KINGDOM)
            return ResourceCounter[1];
        else if (R == Resources.FUNGI_KINGDOM)
            return ResourceCounter[2];
        else
            return ResourceCounter[3];
    }
    /**
     * Retrieves the counter for a specific type of object.
     *
     * @param O The object for which the counter is to be retrieved.
     * @return The counter for the specified object.
     */
    public int getObjectCounter(Objects O) {
        if (O == QUILL)
            return ObjectCounter[0];
        else if (O == INKWELL)
            return ObjectCounter[1];
        else
            return ObjectCounter[2];
    }

    /**
     * Places the start card on the playing field.
     *
     * @param c    The start card to be placed.
     * @param face The face of the card to be placed.
     */

    public void placeStartCard(StartCard c, FB face){
        HashMap<Position, PlayableCard> x = this.PlayerField.getField();
        this.PlayerField.getField().put(new Position(face, 0, 0), c);
        if(face.equals(FB.FRONT)){
            LinkedList<Resources> Res = c.getBackRes();
            int size = c.getBackRes().size();
            for(Resources elemento : Res){
                if(elemento.equals(PLANT_KINGDOM))
                    this.ResourceCounter[0]++;
                if(elemento.equals(ANIMAL_KINGDOM))
                    this.ResourceCounter[1]++;
                if(elemento.equals(FUNGI_KINGDOM))
                    this.ResourceCounter[2]++;
                if(elemento.equals(INSECT_KINGDOM))
                    this.ResourceCounter[3]++;
            }

            if(size==3){
                c.getCorner(Orientation.HR).setRes(EMPTY);
                c.getCorner(Orientation.HL).setRes(EMPTY);
                c.getCorner(Orientation.LR).setRes(ABSENT);
                c.getCorner(Orientation.LL).setRes(ABSENT);
            }

            if(size==2){
                for(Orientation Orien : Orientation.values()){
                    c.getCorner(Orien).setRes(EMPTY);
                }
            }

            if(size==1 && c.getBackRes().contains(FUNGI_KINGDOM)){
                c.getCorner(Orientation.HR).setRes(EMPTY);
                c.getCorner(Orientation.HL).setRes(ANIMAL_KINGDOM);
                c.getCorner(Orientation.LR).setRes(FUNGI_KINGDOM);
                c.getCorner(Orientation.LL).setRes(EMPTY);
            }

            if(size==1 && c.getBackRes().contains(INSECT_KINGDOM)){
                c.getCorner(Orientation.HR).setRes(PLANT_KINGDOM);
                c.getCorner(Orientation.HL).setRes(EMPTY);
                c.getCorner(Orientation.LR).setRes(EMPTY);
                c.getCorner(Orientation.LL).setRes(INSECT_KINGDOM);
            }

        }
        else{
            for(Orientation Orien : Orientation.values()){
                switch (c.getCorner(Orien).getRes()){
                    case PLANT_KINGDOM -> this.ResourceCounter[0]++;
                    case ANIMAL_KINGDOM -> this.ResourceCounter[1]++;
                    case FUNGI_KINGDOM -> this.ResourceCounter[2]++;
                    case INSECT_KINGDOM -> this.ResourceCounter[3]++;
                    default ->{
                        break;
                    }
                }
            }
        }
        this.PlayerField.updateFreePositions(new Position(0, 0));
        this.Hand.remove(c);
    }


    /**
     * Places a card on the playing field at the specified position.
     *
     * @param c The card to be placed.
     * @param p The position at which the card is to be placed.
     */
    public void placeCard(ResourceCard c, Position p) {
        HashMap<Position, PlayableCard> x = this.PlayerField.getField();
        this.PlayerField.getField().put(p, c);
        if(p.getFace().equals(FB.BACK)){
            LinkedList<Resources> Res = c.getBackRes();
            for(Resources elemento : Res){
                switch(elemento){
                    case PLANT_KINGDOM -> this.ResourceCounter[0]++;
                    case ANIMAL_KINGDOM -> this.ResourceCounter[1]++;
                    case FUNGI_KINGDOM -> this.ResourceCounter[2]++;
                    case INSECT_KINGDOM -> this.ResourceCounter[3]++;
                }
            }
            for(Orientation Orien : Orientation.values()){
                c.getCorner(Orien).setRes(EMPTY);
            }
        }
        else{
            for(Orientation Orien : Orientation.values()){
                switch (c.getCorner(Orien).getRes()){
                    case PLANT_KINGDOM -> this.ResourceCounter[0]++;
                    case ANIMAL_KINGDOM -> this.ResourceCounter[1]++;
                    case FUNGI_KINGDOM -> this.ResourceCounter[2]++;
                    case INSECT_KINGDOM -> this.ResourceCounter[3]++;
                    case QUILL -> this.ObjectCounter[0]++;
                    case INKWELL -> this.ObjectCounter[1]++;
                    case MANUSCRIPT -> this.ObjectCounter[2]++;
                    default ->{
                        break;
                    }
                }
            }
            this.PointsCounter += c.getPoints();
        }
        for(Orientation Orien : Orientation.values()){
            Position front = PlayerField.getPosFromCorner(p, Orien); //posizioni davanti agli angoli
            if(x.containsKey(front)){ //se c'è carta in quelle posizioni
                Corner toCover = x.get(front).getCorner(PlayerField.getOppFromCorner(Orien));
                toCover.setCovered(true); //si covera l'angolo
                switch (toCover.getRes()){  //accedo a risorsa dell'angolo coperto e diminuisco il counter
                    case PLANT_KINGDOM -> this.ResourceCounter[0]--;
                    case ANIMAL_KINGDOM -> this.ResourceCounter[1]--;
                    case FUNGI_KINGDOM -> this.ResourceCounter[2]--;
                    case INSECT_KINGDOM -> this.ResourceCounter[3]--;
                    case QUILL -> this.ObjectCounter[0]--;
                    case INKWELL -> this.ObjectCounter[1]--;
                    case MANUSCRIPT -> this.ObjectCounter[2]--;
                    default ->{
                        break;
                    }
                }
            }
        }
        this.PlayerField.updateFreePositions(p); //updato poi le posizioni libere
        this.Hand.remove(c); //tolgo la carta dalla mano
        return;
    }

    /**
     * Retrieves the hand of the player.
     *
     * @return The hand of the player.
     */
    public LinkedList<PlayableCard> getHand(){
        return Hand;
    }


    /**
     * Picks a goal card for the player.
     *
     * @param A      The first goal card option.
     * @param B      The second goal card option.
     * @param choice The choice made by the player (true for option A, false for option B).
     */
    public void pickGoalCard(GoalCard A, GoalCard B, boolean choice) { //tanto la scelta avviene nel controller
        if (choice)
            this.PlayerGoal = A;
        else this.PlayerGoal = B;
    }

   //set messi per il test di goldcard
    public void setResourceCounter(int[] resourceCounter) {
        ResourceCounter = resourceCounter;
    }

    public void setObjectCounter(int[] objectCounter) {
        ObjectCounter = objectCounter;
    }
}