package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This test is composed of an initialization of all the elements needed to call the PointsCalc function on the ObjectsGoalCard
 * and an assertion of what the results are expected to be.
 */
class ObjectsGoalCardTest {
    @Test
    void pointsCalc() {
        Player player = new Player("lazzarone", PlayerColor.YELLOW);
        //boh
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

        carta1.getId();

        int t1 = carta1.pointsCalc(player);
        int t2 = carta2.pointsCalc(player);
        int t3 = carta3.pointsCalc(player);
        int t4 = carta4.pointsCalc(player);

        assertEquals(t1, 9);
        assertEquals(t2, 2);
        assertEquals(t3, 6);
        assertEquals(t4, 4);
    }
}