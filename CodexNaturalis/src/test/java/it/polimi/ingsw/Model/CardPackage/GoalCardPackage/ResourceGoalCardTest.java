package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ResourceGoalCard;

import static org.junit.jupiter.api.Assertions.*;

class ResourceGoalCardTest {

    @Test
    void pointsCalc() {
        Player player = new Player("lazzarone", PlayerColor.YELLOW);
        //boh
        int[] rescounter = new int[4];
        rescounter[0] = 5; // Piante
        rescounter[1] = 0; // Animali
        rescounter[2] = 6; // Funghi
        rescounter[3] = 3; // Insetti
        player.setResourceCounter(rescounter);
        int[] objcounter = new int[3];
        objcounter[0] = 3;
        objcounter[1] = 6;
        objcounter[2] = 5;
        player.setObjectCounter(objcounter);

        ResourceGoalCard carta1 = new ResourceGoalCard(3, 2, Resources.FUNGI_KINGDOM);
        ResourceGoalCard carta2 = new ResourceGoalCard(4, 2, Resources.PLANT_KINGDOM);
        ResourceGoalCard carta3 = new ResourceGoalCard(5, 2, Resources.ANIMAL_KINGDOM);
        ResourceGoalCard carta4 = new ResourceGoalCard(6, 2, Resources.INSECT_KINGDOM);

        carta1.getRes();

        assertEquals(carta1.pointsCalc(player), 4);
        assertEquals(carta2.pointsCalc(player), 2);
        assertEquals(carta3.pointsCalc(player), 0);
        assertEquals(carta4.pointsCalc(player), 2);
    }
}