package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Model.PlayerPackage.PlayingField;

//MEX server-->client
public class UpdateFieldMessage extends Message{
    public String name;
    public PlayingField pf;

    public UpdateFieldMessage(String name, PlayingField pf) {
        super("UpdateFieldMessage");
        this.name= name;
        this.pf=pf;
    }
    public String getName() {
        return name;
    }
    public PlayingField getPlayingField() {
        return pf;
    }
}