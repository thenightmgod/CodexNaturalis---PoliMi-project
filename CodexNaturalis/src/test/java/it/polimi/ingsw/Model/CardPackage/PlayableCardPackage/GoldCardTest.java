package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions.*;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CornerPackage.Resources;

import java.util.HashMap;
import java.util.LinkedList;

public class GoldCardTest {
    @Test
    void RequirementsOk() {
        Player player = new Player("lazzaro", PlayerColor.YELLOW);
        int[] rescounter = new int[4];
        rescounter[0] = 5; // Piante
        rescounter[1] = 0; // Animali
        rescounter[2] = 1; // Funghi
        rescounter[3] = 3; // Insetti
        player.setResourceCounter(rescounter);

        PlayingField campo = new PlayingField();
        campo = player.getPlayerField();

        LinkedList<Corner> Corners1 = new LinkedList<>();
        LinkedList<Corner> Corners2 = new LinkedList<>();
        LinkedList<Corner> Corners3 = new LinkedList<>();

        Corner  primoHR = new Corner(EMPTY, Orientation.HR);
        Corner  primoHL = new Corner(EMPTY, Orientation.HL);
        Corner  primoLR = new Corner(ABSENT, Orientation.LR);
        Corner  primoLL = new Corner(ABSENT, Orientation.LL);
        Corner  secondoHR = new Corner(EMPTY, Orientation.HR);
        Corner  secondoHL = new Corner(ABSENT, Orientation.HL);
        Corner  secondoLR = new Corner(EMPTY, Orientation.LR);
        Corner  secondoLL = new Corner(ABSENT, Orientation.LL);
        Corner  terzoHR = new Corner(EMPTY, Orientation.HR);
        Corner  terzoHL = new Corner(ABSENT, Orientation.HL);
        Corner  terzoLR = new Corner(EMPTY, Orientation.LR);
        Corner  terzoLL = new Corner(EMPTY, Orientation.LL);

        Corners1.addLast(primoHR);
        Corners1.addLast(primoHL);
        Corners1.addLast(primoLR);
        Corners1.addLast(primoLL);

        Corners2.addLast(secondoHR);
        Corners2.addLast(secondoHL);
        Corners2.addLast(secondoLR);
        Corners2.addLast(secondoLL);

        Corners3.addLast(terzoHR);
        Corners3.addLast(terzoHL);
        Corners3.addLast(terzoLR);
        Corners3.addLast(terzoLL);

        boolean Res1[] = new boolean[4];
        boolean Res2[] = new boolean[4];
        boolean Res3[] = new boolean[4];

        Res1[0] = true;
        Res1[1] = false;
        Res1[2] = false;
        Res1[3] = false;
        Res2[0] = true;
        Res2[1] = false;
        Res2[2] = false;
        Res2[3] = false;
        Res3[0] = true;
        Res3[1] = false;
        Res3[2] = false;
        Res3[3] = false;

        int[] requirements1 = new int[4];
        requirements1[0] = 0;
        requirements1[1] = 3;
        requirements1[2] = 0;
        requirements1[3] = 0;
        GoldCard carta1 = new GoldCard(1, Res1, Corners1, CardColor.BLUE, 5, requirements1, PointsCondition.FREE);

        int[] requirements2 = new int[4];
        requirements2[0] = 0;
        requirements2[1] = 0;
        requirements2[2] = 1;
        requirements2[3] = 3;
        GoldCard carta2 = new GoldCard(3, Res2, Corners2, CardColor.PURPLE, 5, requirements2, PointsCondition.FREE);

        int[] requirements3 = new int[4];
        requirements3[0] = 5;
        requirements3[1] = 0;
        requirements3[2] = 0;
        requirements3[3] = 0;
        GoldCard carta3 = new GoldCard(3, Res3, Corners3, CardColor.BLUE, 5, requirements3, PointsCondition.FREE);

        assertFalse(carta1.RequirementsOk(player));
        assertTrue(carta2.RequirementsOk(player));
        assertTrue(carta3.RequirementsOk(player));
    }
    @Test
    void PointsCalc(){
        Player player = new Player("lazzarone", PlayerColor.YELLOW);
        int[] rescounter = new int[4];
        rescounter[0] = 5; // Piante
        rescounter[1] = 0; // Animali
        rescounter[2] = 1; // Funghi
        rescounter[3] = 3; // Insetti
        player.setResourceCounter(rescounter);
        int[] objcounter = new int[3];
        objcounter[0] = 3;
        objcounter[1] = 2;
        objcounter[2] = 0;
        player.setObjectCounter(objcounter);

        PlayingField campo = new PlayingField();
        campo = player.getPlayerField();

        LinkedList<Corner> Corners1 = new LinkedList<>();
        LinkedList<Corner> Corners2 = new LinkedList<>();
        LinkedList<Corner> Corners3 = new LinkedList<>();
        LinkedList<Corner> Corners4 = new LinkedList<>();
        LinkedList<Corner> Corners5 = new LinkedList<>();
        LinkedList<Corner> Corners6 = new LinkedList<>();
        LinkedList<Corner> Corners7 = new LinkedList<>();

        Corner  primoHR = new Corner(EMPTY, Orientation.HR);
        Corner  primoHL = new Corner(EMPTY, Orientation.HL);
        Corner  primoLR = new Corner(ABSENT, Orientation.LR);
        Corner  primoLL = new Corner(ABSENT, Orientation.LL);
        Corner  secondoHR = new Corner(EMPTY, Orientation.HR);
        Corner  secondoHL = new Corner(ABSENT, Orientation.HL);
        Corner  secondoLR = new Corner(EMPTY, Orientation.LR);
        Corner  secondoLL = new Corner(ABSENT, Orientation.LL);
        Corner  terzoHR = new Corner(EMPTY, Orientation.HR);
        Corner  terzoHL = new Corner(ABSENT, Orientation.HL);
        Corner  terzoLR = new Corner(EMPTY, Orientation.LR);
        Corner  terzoLL = new Corner(EMPTY, Orientation.LL);
        Corner  quartoHR = new Corner(EMPTY, Orientation.HR);
        Corner  quartoHL = new Corner(ABSENT, Orientation.HL);
        Corner  quartoLR = new Corner(EMPTY, Orientation.LR);
        Corner  quartoLL = new Corner(EMPTY, Orientation.LL);
        Corner  quintoHR = new Corner(EMPTY, Orientation.HR);
        Corner  quintoHL = new Corner(ABSENT, Orientation.HL);
        Corner  quintoLR = new Corner(EMPTY, Orientation.LR);
        Corner  quintoLL = new Corner(EMPTY, Orientation.LL);
        Corner  sestoHR = new Corner(EMPTY, Orientation.HR);
        Corner  sestoHL = new Corner(ABSENT, Orientation.HL);
        Corner  sestoLR = new Corner(EMPTY, Orientation.LR);
        Corner  sestoLL = new Corner(EMPTY, Orientation.LL);
        Corner  settimoHR = new Corner(EMPTY, Orientation.HR);
        Corner  settimoHL = new Corner(ABSENT, Orientation.HL);
        Corner  settimoLR = new Corner(EMPTY, Orientation.LR);
        Corner  settimoLL = new Corner(EMPTY, Orientation.LL);

        Corners1.addLast(primoHR);
        Corners1.addLast(primoHL);
        Corners1.addLast(primoLR);
        Corners1.addLast(primoLL);

        Corners2.addLast(secondoHR);
        Corners2.addLast(secondoHL);
        Corners2.addLast(secondoLR);
        Corners2.addLast(secondoLL);

        Corners3.addLast(terzoHR);
        Corners3.addLast(terzoHL);
        Corners3.addLast(terzoLR);
        Corners3.addLast(terzoLL);

        Corners4.addLast(quartoHR);
        Corners4.addLast(quartoHL);
        Corners4.addLast(quartoLR);
        Corners4.addLast(quartoLL);

        Corners5.addLast(quintoHR);
        Corners5.addLast(quintoHL);
        Corners5.addLast(quintoLR);
        Corners5.addLast(quintoLL);

        Corners6.addLast(sestoHR);
        Corners6.addLast(sestoHL);
        Corners6.addLast(sestoLR);
        Corners6.addLast(sestoLL);

        Corners7.addLast(settimoHR);
        Corners7.addLast(settimoHL);
        Corners7.addLast(settimoLR);
        Corners7.addLast(settimoLL);

        boolean Res1[] = new boolean[4];
        boolean Res2[] = new boolean[4];
        boolean Res3[] = new boolean[4];
        boolean Res4[] = new boolean[4];
        boolean Res5[] = new boolean[4];
        boolean Res6[] = new boolean[4];
        boolean Res7[] = new boolean[4];

        Res1[0] = true;
        Res1[1] = false;
        Res1[2] = false;
        Res1[3] = false;
        Res2[0] = true;
        Res2[1] = false;
        Res2[2] = false;
        Res2[3] = false;
        Res3[0] = true;
        Res3[1] = false;
        Res3[2] = false;
        Res3[3] = false;
        Res4[0] = true;
        Res4[1] = false;
        Res4[2] = false;
        Res4[3] = false;
        Res5[0] = true;
        Res5[1] = false;
        Res5[2] = false;
        Res5[3] = false;
        Res6[0] = true;
        Res6[1] = false;
        Res6[2] = false;
        Res6[3] = false;
        Res7[0] = true;
        Res7[1] = false;
        Res7[2] = false;
        Res7[3] = false;

        int[] requirements1 = new int[4];
        requirements1[0] = 0;
        requirements1[1] = 3;
        requirements1[2] = 0;
        requirements1[3] = 0;
        GoldCard carta1 = new GoldCard(1, Res1, Corners1, CardColor.BLUE, 2, requirements1, PointsCondition.OBJECTS_QUILL);

        int[] requirements2 = new int[4];
        requirements2[0] = 0;
        requirements2[1] = 0;
        requirements2[2] = 1;
        requirements2[3] = 3;
        GoldCard carta2 = new GoldCard(3, Res2, Corners2, CardColor.PURPLE, 1, requirements2, PointsCondition.OBJECTS_INKWELL);

        int[] requirements3 = new int[4];
        requirements3[0] = 5;
        requirements3[1] = 0;
        requirements3[2] = 0;
        requirements3[3] = 0;
        GoldCard carta3 = new GoldCard(3, Res3, Corners3, CardColor.BLUE, 3, requirements3, PointsCondition.OBJECTS_MANUSCRIPT);

        int[] requirements4 = new int[4];
        requirements3[0] = 3;
        requirements3[1] = 0;
        requirements3[2] = 0;
        requirements3[3] = 0;
        GoldCard carta4 = new GoldCard(3, Res4, Corners4, CardColor.BLUE, 5, requirements4, PointsCondition.FREE);

        int[] requirements5 = new int[4];
        requirements3[0] = 3;
        requirements3[1] = 0;
        requirements3[2] = 0;
        requirements3[3] = 0;
        GoldCard carta5 = new GoldCard(3, Res5, Corners5, CardColor.BLUE, 2, requirements5, PointsCondition.CORNERS);

        int[] requirements6 = new int[4];
        requirements3[0] = 3;
        requirements3[1] = 0;
        requirements3[2] = 0;
        requirements3[3] = 0;
        GoldCard carta6 = new GoldCard(3, Res6, Corners6, CardColor.BLUE, 5, requirements6, PointsCondition.FREE);

        int[] requirements7 = new int[4];
        requirements3[0] = 0;
        requirements3[1] = 0;
        requirements3[2] = 0;
        requirements3[3] = 0;
        GoldCard carta7 = new GoldCard(3, Res7, Corners7, CardColor.BLUE, 2, requirements7, PointsCondition.FREE);

        Position pos50 = new Position(0, 0);
        Position pos51 = new Position(2, 0);
        Position pos5 = new Position(1, 1);
        Position pos1 = new Position(5, 5);
        Position pos2 = new Position(6, 6);
        Position pos3 = new Position(7, 7);
        Position pos4 = new Position(8, 8);

        HashMap<Position, PlayableCard> mappa = campo.getField();

        mappa.put(pos50, carta6);
        mappa.put(pos51, carta7);

        assertEquals(carta1.PointsCalc(player, pos1), 6);
        assertEquals(carta2.PointsCalc(player, pos2), 2);
        assertEquals(carta3.PointsCalc(player, pos3), 0);
        assertEquals(carta4.PointsCalc(player, pos4), 5);
        assertEquals(4, carta5.PointsCalc(player, pos5));
    }
}