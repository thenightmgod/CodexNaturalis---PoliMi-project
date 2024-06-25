package it.polimi.ingsw.Model.Messages;

import it.polimi.ingsw.Chat.ChatMessage;

public class ChatMessageMessage extends Message{

    public ChatMessage message;
    public String name;

    public ChatMessageMessage(ChatMessage message, String name){
        super("ChatMessageMessage");
        this.message = message;
        this.name = name;
    }

    public ChatMessage getMessage(){
        return message;
    }

    public String getName(){
        return name;
    }
}
