package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.GameController;

public interface GameView {
    void update();

    void update(GameController controller);
}
