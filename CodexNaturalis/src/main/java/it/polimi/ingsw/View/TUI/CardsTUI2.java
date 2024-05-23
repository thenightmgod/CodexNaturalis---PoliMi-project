package it.polimi.ingsw.View.TUI;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CornerPackage.*;
import it.polimi.ingsw.Model.CardPackage.*;

import java.security.interfaces.RSAKey;

public class CardsTUI2 {


    public void printCard(PlayableCard card) {
        int altezza = 4;
        int larghezza = 8;
        for(int i = 0; i<altezza; i++) {
            for (int j = 0; j < larghezza; j++) {
                if (i == 0 || i == altezza - 1) {
                    System.out.print("-");
                } else if (j == 0 || j == larghezza - 1) {
                    System.out.print("|");
                } else if ((i == 1) && (j == 1)) {
                    StringBuilder sb = new StringBuilder();
                    Corner c = card.getCorner(Orientation.HL);
                    printCorner(c, sb);
                } else if ((i == 3) && (j == 1)) {
                    StringBuilder sb = new StringBuilder();
                    Corner c2 = card.getCorner(Orientation.LL);
                    printCorner(c2, sb);
                } else if ((i == 1) && (j == 7)) {
                    StringBuilder sb = new StringBuilder();
                    Corner c3 = card.getCorner(Orientation.HR);
                    printCorner(c3, sb);
                } else if ((i == 3) && (j == 7)) {
                    StringBuilder sb = new StringBuilder();
                    Corner c4 = card.getCorner(Orientation.LR);
                    printCorner(c4, sb);
                }
            }
        }
    }

    public void printCorner(Corner c, StringBuilder sb) {
        if(c.getCovered()){
            sb.append("X");
        }
        else {
            if (c.getRes().equals(Resources.PLANT_KINGDOM)) {
                sb.append(Resources.PLANT_KINGDOM.getShortName());
            } else if (c.getRes().equals(Resources.ANIMAL_KINGDOM)) {
                sb.append(Resources.ANIMAL_KINGDOM.getShortName());
            } else if (c.getRes().equals(Resources.FUNGI_KINGDOM)) {
                sb.append(Resources.FUNGI_KINGDOM.getShortName());
            } else if (c.getRes().equals(Resources.INSECT_KINGDOM)) {
                sb.append(Resources.INSECT_KINGDOM.getShortName());
            } else if (c.getRes().equals(Objects.QUILL)) {
                sb.append(Objects.QUILL.getShortName());
            } else if (c.getRes().equals(Objects.MANUSCRIPT)) {
                sb.append(Objects.MANUSCRIPT.getShortName());
            } else if (c.getRes().equals(Objects.INKWELL)) {
                sb.append(Objects.INKWELL.getShortName());
            } else if (c.getRes().equals(CornerState.ABSENT)) {
                sb.append(CornerState.ABSENT.getShortName());
            } else if (c.getRes().equals(CornerState.EMPTY)) {
                sb.append(CornerState.EMPTY.getShortName());
            }
        }
    }




}
