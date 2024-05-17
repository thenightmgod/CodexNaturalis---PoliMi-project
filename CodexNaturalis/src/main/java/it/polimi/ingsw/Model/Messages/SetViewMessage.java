package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.View.GameView;

public class SetViewMessage extends Message {

    public GameView view;

    public SetViewMessage(GameView view) {
        super("SetViewMessage");
        this.view=view;
    }



    @Override
    public String getType() {
        return super.getType();
    }

    public GameView getView() {
        return view;
    }
}
