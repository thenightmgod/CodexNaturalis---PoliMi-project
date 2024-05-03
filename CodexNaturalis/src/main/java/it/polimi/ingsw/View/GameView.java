package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.GameController;

public interface GameView {
    public void update();

    public void update(GameController controller);
}
