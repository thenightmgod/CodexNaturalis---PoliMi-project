package it.polimi.ingsw.Model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
class RoomTest {
    @Test
    void testMetodiMinori(){
        LinkedList<Player> p1 = new LinkedList<>();
        Room r1 = new Room(5, p1);
        assert(r1.getFirstRound());
    }
}