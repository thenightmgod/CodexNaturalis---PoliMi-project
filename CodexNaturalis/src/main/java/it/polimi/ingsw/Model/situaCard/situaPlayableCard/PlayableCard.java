package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCard.Card;
import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.LinkedList;
import java.util.SortedSet;


/**
 * Represents the generic Card that the player can draw and place in his playing field at each turn.
 * The PlayableCard includes ResourceCard and GoldCard.
 */
public abstract class PlayableCard extends Card{
    private final boolean[] BackRes;
    private LinkedList<Corner> Corners;

    /**
     * Constructs a new PlayableCard with the specified parameters.
     *
     * @param id The individual identifier of the PlayableCard.
     * @param backRes An array representing the resources present at the back of the PlayableCard.
     *                Each position in the array corresponds to a resource in the order they are declared
     *                in the "Resources" enum, and each value represents the number of that resource that
     *                the PlayableCard possesses at its back.
     * @param Corners The list of four corners at the extremities of the card.
     */
    public PlayableCard(int id, boolean[] backRes, LinkedList<Corner> Corners){

        super(id);
        this.Corners = new LinkedList<>();
        this.Corners.add(new Corner(Corners.get(0).getRes(), Corners.get(0).getOrientation()));
        this.Corners.add(new Corner(Corners.get(1).getRes(), Corners.get(1).getOrientation()));
        this.Corners.add(new Corner(Corners.get(2).getRes(), Corners.get(2).getOrientation()));
        this.Corners.add(new Corner(Corners.get(3).getRes(), Corners.get(3).getOrientation()));


        BackRes = new boolean[4];

        for(int i=0; i<4; i++){
            BackRes[i] = backRes[i];
        }
    }


    /**
     * Returns the list of resources present on the back of the card based on the BackRes array.
     *
     * @return A LinkedList containing the resources present on the back of the card.
     */
    public LinkedList<Resources> getBackRes(){
        LinkedList<Resources> Res = new LinkedList<Resources>();

        if(this.BackRes[0])
            Res.add(Resources.PLANT_KINGDOM);
        if(this.BackRes[1])
            Res.add(Resources.ANIMAL_KINGDOM);
        if(this.BackRes[2])
            Res.add(Resources.FUNGI_KINGDOM);
        if(this.BackRes[3])
            Res.add(Resources.INSECT_KINGDOM);

        return Res;

    }

    /**
     * Returns the corner located at the specified position.
     *
     * @param Orient The orientation indicating the position of the corner (HR, HL, LR, or LL).
     * @return The corner located at the specified position.
     */
    public Corner getCorner(Orientation Orient){
        return switch(Orient){
            case HR -> this.Corners.get(0);
            case HL -> this.Corners.get(1);
            case LR -> this.Corners.get(2);
            case LL -> this.Corners.get(3);
        };
    }



}

