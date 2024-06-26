package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Chat.ChatMessage;

import java.util.LinkedList;

public class UpdateChatMessage extends Message{

    public String name;
    public LinkedList<ChatMessage> chat;

    public UpdateChatMessage(String name, LinkedList<ChatMessage> chat){
        super("UpdateChatMessage");
        this.name = name;
        this.chat = chat;
    }

    public String getName(){
        return this.name;
    }

    public LinkedList<ChatMessage> getChat(){
        return this.chat;
    }

}
