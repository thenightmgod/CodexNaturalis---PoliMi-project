package it.polimi.ingsw.Model.situaPlayer;

import java.util.*;

import it.polimi.ingsw.Model.situaCard.situaPlayableCard.*;
import it.polimi.ingsw.Model.situaCorner.*;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaCorner.Objects;
import it.polimi.ingsw.Model.situaDeck.Deck;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import static it.polimi.ingsw.Model.situaCorner.CornerState.EMPTY;

import static it.polimi.ingsw.Model.situaCorner.Objects.*;
import static it.polimi.ingsw.Model.situaCorner.Resources.ANIMAL_KINGDOM;
import static it.polimi.ingsw.Model.situaCorner.Resources.PLANT_KINGDOM;
import static it.polimi.ingsw.Model.situaCorner.Resources.FUNGI_KINGDOM;
import static it.polimi.ingsw.Model.situaCorner.Resources.INSECT_KINGDOM;

public class Player {
    private final String Name;
    private final PlayerColor Color;
    private int PointsCounter;
    private int[] ResourceCounter;
    private int[] ObjectCounter;
    private LinkedList<PlayableCard> Hand;
    private GoalCard PlayerGoal;
    private PlayingField PlayerField;

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

    public String getName() {
        return Name;
    }

    public PlayerColor getColor() {
        return Color;
    }

    public int getPointsCounter() {
        return PointsCounter;
    }

    public void setPlayerGoal(GoalCard card){
        PlayerGoal = card;
    }

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

    public int getObjectCounter(Objects O) {
        if (O == QUILL)
            return ObjectCounter[0];
        else if (O == INKWELL)
            return ObjectCounter[1];
        else
            return ObjectCounter[2];
    }

    public void placeStartCard(StartCard c, FB face){
        HashMap<Position, PlayableCard> x = this.PlayerField.getField();
        this.PlayerField.getField().put(new Position(face, 0, 0), c);
        if(face.equals(FB.FRONT)){
            LinkedList<Resources> Res = c.getBackRes();
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
        }
        this.PlayerField.updateFreePositions(new Position(0, 0));
        this.Hand.remove(c);
    }

    public void placeCard(PlayableCard c, Position p) {
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
    public LinkedList<PlayableCard> getHand(){
        return Hand;
    }

    public void pickGoalCard(GoalCard A, GoalCard B, boolean choice) { //tanto la scelta avviene nel controller
        if (choice)
            this.PlayerGoal = A;
        else this.PlayerGoal = B;
    }

}