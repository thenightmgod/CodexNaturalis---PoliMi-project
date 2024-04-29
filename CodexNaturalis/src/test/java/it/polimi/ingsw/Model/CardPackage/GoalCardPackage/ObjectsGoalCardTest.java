package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
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

import static org.junit.jupiter.api.Assertions.*;

class ObjectsGoalCardTest {

    @Test
    void pointsCalc() {
        Player player = new Player("lazzarone", PlayerColor.YELLOW);
        int[] rescounter = new int[4];
        rescounter[0] = 5; // Piante
        rescounter[1] = 0; // Animali
        rescounter[2] = 1; // Funghi
        rescounter[3] = 3; // Insetti
        player.setResourceCounter(rescounter);
        int[] objcounter = new int[3];
        objcounter[0] = 3;
        objcounter[1] = 6;
        objcounter[2] = 5;
        player.setObjectCounter(objcounter);

        int array1[] = new int[3];
        array1[0] = 1;
        array1[1] = 1;
        array1[2] = 1;

        int array2[] = new int[3];
        array2[0] = 2;
        array2[1] = 0;
        array2[2] = 0;

        int array3[] = new int[3];
        array3[0] = 0;
        array3[1] = 2;
        array3[2] = 0;

        int array4[] = new int[3];
        array4[0] = 0;
        array4[1] = 0;
        array4[2] = 2;

        ObjectsGoalCard carta1 = new ObjectsGoalCard(3, 3, array1);
        ObjectsGoalCard carta2 = new ObjectsGoalCard(4, 2, array2);
        ObjectsGoalCard carta3 = new ObjectsGoalCard(5, 2,array3);
        ObjectsGoalCard carta4 = new ObjectsGoalCard(6, 2, array4);

        assertEquals(carta1.pointsCalc(player), 9);
        assertEquals(carta2.pointsCalc(player), 2);
        assertEquals(carta3.pointsCalc(player), 6);
        assertEquals(carta4.pointsCalc(player), 4);
    }
}