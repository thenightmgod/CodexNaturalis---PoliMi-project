package it.polimi.ingsw.Model.Messages;

public class ShowOtherFieldMessage extends Message {
    private String player;

    public ShowOtherFieldMessage(String name) {
        super("ShowOtherFieldMessage");
        this.player=name;
    }

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public String getPlayer() {
        return player;
    }
}
