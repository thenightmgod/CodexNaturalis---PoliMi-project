package it.polimi.ingsw.Model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
class RoomTest {
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

        r1.createDecks();
    }
}