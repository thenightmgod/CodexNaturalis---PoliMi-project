package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void Controller() {
        GameController beppe = new GameController(5, 4);

        beppe.addPlayer("prend", PlayerColor.YELLOW);
        beppe.addPlayer("mirko", PlayerColor.GREEN);
        beppe.addPlayer("nina", PlayerColor.RED);
        beppe.addPlayer("lollo", PlayerColor.BLUE);

        beppe.initializeRoom();

        beppe.initializeGame();

        beppe.changeTurns();
        assert(beppe.getGame().getTurn().getName().equals("mirko"));
        beppe.changeTurns();
        assert(beppe.getGame().getTurn().getName().equals("nina"));

        GameController beppe2 = new GameController(7, 3);


        beppe2.addPlayer("mirko", PlayerColor.GREEN);
        beppe2.addPlayer("nina", PlayerColor.RED);
        beppe2.addPlayer("lollo", PlayerColor.BLUE);

        beppe2.initializeRoom();

        beppe2.initializeGame();

        beppe2.changeTurns();
        assert(beppe2.getGame().getTurn().getName().equals("nina"));
        beppe2.changeTurns();
        assert(beppe2.getGame().getTurn().getName().equals("lollo"));

        GameController beppe3 = new GameController(10, 2);


        beppe3.addPlayer("nina", PlayerColor.RED);
        beppe3.addPlayer("lollo", PlayerColor.BLUE);

        beppe3.initializeRoom();

        beppe3.initializeGame();

        beppe3.changeTurns();
        assert(beppe3.getGame().getTurn().getName().equals("lollo"));

        beppe3.pickResCard(2);
        beppe3.pickGoldCard(2);
    }
}