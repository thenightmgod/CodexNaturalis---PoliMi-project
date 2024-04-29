package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions.*;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CornerPackage.Resources;

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

        LinkedList<Corner> Corners1 = new LinkedList<>();
        LinkedList<Corner> Corners2 = new LinkedList<>();
        LinkedList<Corner> Corners3 = new LinkedList<>();
        LinkedList<Corner> Corners4 = new LinkedList<>();

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

        boolean Res1[] = new boolean[4];
        boolean Res2[] = new boolean[4];
        boolean Res3[] = new boolean[4];
        boolean Res4[] = new boolean[4];

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

        assertEquals(carta1.PointsCalc(player), 6);
        assertEquals(carta2.PointsCalc(player), 2);
        assertEquals(carta3.PointsCalc(player), 0);
        assertEquals(carta4.PointsCalc(player), 5);
    }
}