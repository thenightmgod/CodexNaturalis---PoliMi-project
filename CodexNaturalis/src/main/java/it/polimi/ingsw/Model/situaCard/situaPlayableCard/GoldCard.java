package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Objects;
import it.polimi.ingsw.Model.situaCorner.Orientation;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaPlayer.Player;
import java.util.Arrays;

import java.util.LinkedList;

/**
 * Represents the ResourceCard that, to be deployed on the playing field,
 * need the player to meet the resource requirements.
 * In addition to standard ResourceCard, a GoldCard can give points based on
 * certain positions in which they are fielded or based on the number of Objects the player has.
 *
 */
public class GoldCard extends ResourceCard {
    private final int[] requirements;
    //requirements è un array da 4 posizioni,in posizione 0 PLANT, posizione 1 ANIMAL, posizione 2 FUNGI, pos.3 INSECT
    //se per posizionare la carta mi servono 3 funghi e una farfalla, avrò un 3 in pos.2 ed un 1 in posizione 3
    private final PointsCondition PointsC;

    /**
     *Constructs a new GoldCard
     *
     * @param id the specific GoldCard's identifier
     * @param R the array of 4 positions, each rappresentative of a resource (in the same order of the enum "Resources"),
     *          containing in each position the number of that resource that the player must have
     * @param Corners
     * @param c
     * @param p
     * @param requirementsInput
     * @param pointsC
     */
    public GoldCard(int id, boolean[] R, LinkedList<Corner> Corners, CardColor c, int p, int[] requirementsInput, PointsCondition pointsC) {
        super(id, R, c, p, Corners);
        if (requirementsInput.length != 3) {
            throw new IllegalArgumentException("L'array di input deve essere di dimensione 3");
        }
        requirements = new int[3];
        // Copia gli elementi dall'array di requirementsinput a requirements
        for (int i = 0; i < 3; i++) {
            requirements[i] = requirementsInput[i];
        }
        this.PointsC = pointsC;
    }

    public int[] getRequirements() {
        return requirements;
    }
    public PointsCondition getPointsCondition() {
        return PointsC;
    }


    public boolean RequirementsOk(Player p) {
        boolean ok=true;
        for (int i=0; i<4; i++) {
            if (requirements[i]!=0) {
                if(i==0) {
                    if(requirements[0] < p.getResourceCounter(Resources.PLANT_KINGDOM))
                        ok=false;
                }
                else if (i==1) {
                    if(requirements[1] < p.getResourceCounter(Resources.ANIMAL_KINGDOM))
                        ok=false;

                }
                else if(i==2) {
                    if (requirements[2]< p.getResourceCounter(Resources.FUNGI_KINGDOM))
                        ok=false;
                }else
                if (requirements[3]< p.getResourceCounter(Resources.INSECT_KINGDOM))
                    ok=false;
            }
        }
        return ok;
    }

    //TODO PointsCalc() SE IL POINTSCONDITION è CORNERS
    //IO CONSIDERO CHE QUESTO POINTSCALC VIENE CHIAMATO QUANDO LA CARTA IN QUESTIONE
    //E' GIA' STATA POSIZIONATA E PERCIO' CONTO COME PUNTI ANCHE GLI OGGETTI CONTENUTI IN LEI
    public int PointsCalc(Player p) {
        int PointsTot=0;
        if (this.PointsC==PointsCondition.FREE){
            PointsTot= this.getPoints();
        }else if (this.PointsC==PointsCondition.OBJECTS_QUILL) {
            PointsTot= (this.getPoints() * p.getObjectCounter(Objects.QUILL));
        }else if (this.PointsC==PointsCondition.OBJECTS_INKWELL) {
            PointsTot= (this.getPoints() * p.getObjectCounter(Objects.INKWELL));
        }else if (this.PointsC==PointsCondition.OBJECTS_MANUSCRIPT) {
            PointsTot= (this.getPoints() * p.getObjectCounter(Objects.MANUSCRIPT));
        }
        return PointsTot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceCard ID: ").append(getId()).append("\n");
        sb.append("Color: ").append(getColor()).append("\n");
        sb.append("Points: ").append(getPoints()).append("\n");
        sb.append("BackRes: ").append((getBackRes())).append("\n");
        sb.append("Corners:").append("\n");

        // Itera su ogni angolo
        for (Orientation Orien : Orientation.values()) {
            Corner corner = getCorner(Orien);
            sb.append(Orien).append(": ").append(corner.getRes()).append("\n");
        }

        return sb.toString();
    }

}

