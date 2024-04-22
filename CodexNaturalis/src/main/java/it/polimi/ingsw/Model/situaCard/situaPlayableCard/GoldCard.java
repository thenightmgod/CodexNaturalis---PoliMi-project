package it.polimi.ingsw.Model.situaCard.situaPlayableCard;

import it.polimi.ingsw.Model.situaCorner.Corner;
import it.polimi.ingsw.Model.situaCorner.Objects;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaPlayer.Player;

import java.util.LinkedList;

public class GoldCard extends ResourceCard {
    private final int[] requirements;
    //requirements è un array da 4 posizioni,in posizione 0 PLANT, posizione 1 ANIMAL, posizione 2 FUNGI, pos.3 INSECT
    //se per posizionare la carta mi servono 3 funghi e una farfalla, avrò un 3 in pos.2 ed un 1 in posizione 3
    private final PointsCondition PointsC;

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
}


