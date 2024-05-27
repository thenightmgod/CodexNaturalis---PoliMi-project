package it.polimi.ingsw.Model.Messages;

public class ShowOtherFieldMessage extends Message {
    private String player;

    public ShowOtherFieldMessage(String name) {
        super("ShowOtherFieldMessage");
        this.player=name;
    }
    public String getPlayer() {
        return player;
    }
}
