package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.View.GameView;

public class SetViewMessage extends Message {

    private GameView view;

    public SetViewMessage(GameView view) {
        super("SetViewMessage");
        this.view = view;
    }
    public GameView getView() {
        return view;
    }
}
