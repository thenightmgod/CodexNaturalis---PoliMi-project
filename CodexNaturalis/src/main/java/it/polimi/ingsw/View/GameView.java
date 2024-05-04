package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.GameController;

public interface GameView {
    public void update(); //cambia se stessa

    public void update(GameController controller); //vuole cambiare il model
}
