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
        GameController beppe = new GameController(5);

        beppe.addPlayer("prend", PlayerColor.YELLOW);
        beppe.addPlayer("mirko", PlayerColor.GREEN);
        beppe.addPlayer("nina", PlayerColor.RED);
        beppe.addPlayer("lollo", PlayerColor.BLUE);

        beppe.initializeRoom();

        beppe.initializeGame();
    }
}