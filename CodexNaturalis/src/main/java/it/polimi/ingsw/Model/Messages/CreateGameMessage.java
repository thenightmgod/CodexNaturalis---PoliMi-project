package it.polimi.ingsw.Model.Messages;

public class CreateGameMessage extends Message{
    public int RoomId;
    public int NumPlayers;

    public CreateGameMessage(String nickname,int RoomId, int NumPlayers) {
        super("CreateGameMessage", nickname);
        this.RoomId= RoomId;
        this.NumPlayers=NumPlayers;
    }

    public int getNumPlayers(){
        return NumPlayers;
    }
    public int getRoomId() {
        return RoomId;
    }
}
