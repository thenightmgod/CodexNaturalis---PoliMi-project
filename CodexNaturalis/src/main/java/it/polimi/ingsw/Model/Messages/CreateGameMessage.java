package it.polimi.ingsw.Model.Messages;
import com.google.gson.Gson;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;


public class CreateGameMessage extends Message{
    public String name;
    public int NumPlayers;

    public CreateGameMessage(String nickname, int NumPlayers) {
        super("CreateGameMessage");
        this.NumPlayers=NumPlayers;
        this.name=nickname;
    }

    public String getName() {
        return name;
    }

    public int getNumPlayers(){
        return NumPlayers;
    }

}
