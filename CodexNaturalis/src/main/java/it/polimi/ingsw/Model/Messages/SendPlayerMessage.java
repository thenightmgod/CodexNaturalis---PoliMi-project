package it.polimi.ingsw.Model.Messages;

import java.util.LinkedList;

public class SendPlayerMessage extends Message{

    public LinkedList<String> players;

    public SendPlayerMessage(LinkedList<String> players) {
        super("SendPlayerMessage");
        this.players = players;
    }

    public LinkedList<String> getPlayers() {
        return players;
    }
}
