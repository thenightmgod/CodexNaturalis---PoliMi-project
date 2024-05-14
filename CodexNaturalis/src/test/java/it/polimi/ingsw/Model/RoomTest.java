package it.polimi.ingsw.Model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.*;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
/*class RoomTest {
    @Test
    void testMetodiMinori(){
        LinkedList<Player> players = new LinkedList<>();
        Player p1 = new Player("LOLLO", PlayerColor.GREEN);
        Player p2 = new Player("PIE", PlayerColor.RED);
        Player p3 = new Player("NINA", PlayerColor.YELLOW);
        Player p4 = new Player("HAMIN", PlayerColor.BLUE);
        players.addLast(p1);
        players.addLast(p2);
        players.addLast(p3);
        players.addLast(p4);
        Room r1 = new Room(5, players);
        assert(r1.getFirstRound());
        assertFalse(r1.getLastRound());

        r1.setLastRound();
        assertFalse(r1.getLastRound());

        r1.setTwentyFlag();

        LinkedList<GoalCard> duecarteGoal = new LinkedList<>();

        r1.createDecks();   //creo i deck
        r1.giveHands();     // distribusico le mani a ogni giocatore
        r1.commonGoals();   // setto obbittivi comuni
        duecarteGoal = r1.show2GoalCards(p1);   //mostro due carte obbiettivo
        r1.pickGoalCard(p1, true);   //giocatore picka una carta (o la prima o la seconda della lista)
        //setup finito
    }
    @Test
    void CommonGoalsTest(){
        LinkedList<Player> players = new LinkedList<>();
        Player p1 = new Player("LOLLO", PlayerColor.GREEN);
        Player p2 = new Player("PIE", PlayerColor.RED);
        Player p3 = new Player("NINA", PlayerColor.YELLOW);
        Player p4 = new Player("HAMIN", PlayerColor.BLUE);
        players.addLast(p1);
        players.addLast(p2);
        players.addLast(p3);
        players.addLast(p4);
        Room r1 = new Room(5, players);

        LinkedList<GoalCard> commongoals = new LinkedList<>();

        int array1[] = new int[3];
        array1[0] = 0;
        array1[1] = 0;
        array1[2] = 2;

        CompositionGoalCard GoalRL = new CompositionGoalCard(88, 2, Composition.REVERSE_L, CardColor.GREEN);
        ResourceGoalCard GoalBlue = new ResourceGoalCard(97, 3, Resources.ANIMAL_KINGDOM);
        ObjectsGoalCard amidogol = new ObjectsGoalCard(100, 2, array1);

        commongoals.addLast(GoalRL);
        commongoals.addLast(GoalBlue);

        p4.setPlayerGoal(true);

        r1.checkGoals(p4, commongoals);
    }
}
*/