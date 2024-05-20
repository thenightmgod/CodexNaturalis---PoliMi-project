package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.PlayingField;

//MEX server-->client
public class UpdateFieldMessage extends Message{
    private String name;
    private PlayingField pf;

    public UpdateFieldMessage(String name, PlayingField pf) {
        super("UpdateFieldMessage");
        this.name= name;
        this.pf=pf;
    }
    public String getName() {
        return name;
    }
    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public String MessageToJson() {
        return super.MessageToJson();
    }
    public PlayingField getPlayingField() {
        return pf;
    }
}