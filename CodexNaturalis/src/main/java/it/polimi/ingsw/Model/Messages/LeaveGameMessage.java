package it.polimi.ingsw.Model.Messages;

public class LeaveGameMessage extends Message {
    //va messa la virtualView?
    public int RoomId;

    public LeaveGameMessage(String name, int RoomId) {
        super("LeaveGameMessage", name);
        this.RoomId=RoomId;
    }
    @Override
    public String getName() {
        return super.getName();
    }
    public int getRoomId() {
        return RoomId;
    }
}
