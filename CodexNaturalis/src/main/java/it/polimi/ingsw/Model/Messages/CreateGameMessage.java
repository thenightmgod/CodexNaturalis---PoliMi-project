package it.polimi.ingsw.Model.Messages;
import com.google.gson.Gson;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;


public class CreateGameMessage extends Message{
    public int RoomId;
    public int NumPlayers;
    public PlayerColor pc;

    public CreateGameMessage(String nickname, int NumPlayers, PlayerColor pc) {
        super("CreateGameMessage", nickname);
        this.RoomId= RoomId;
        this.NumPlayers=NumPlayers;
        this.pc = pc;
    }

    public int getNumPlayers(){
        return NumPlayers;
    }
    public int getRoomId() {
        return RoomId;
    }

    public String MessagetoJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
