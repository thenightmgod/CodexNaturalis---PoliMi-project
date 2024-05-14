package it.polimi.ingsw.Model.Messages;
import it.polimi.ingsw.Model.PlayerPackage.*;

public class JoinExistingGameMessage extends Message{
    public PlayerColor c;
    public int RoomId;

    public JoinExistingGameMessage(String nickname, PlayerColor c, int RoomId) {
        super("JoinExistingGameMessage", nickname);
        this.c=c;
        this.RoomId=RoomId;
    }

    public int getRoomId() {
        return RoomId;
    }
    public PlayerColor getPlayerColor() {
        return c;
    }
}
