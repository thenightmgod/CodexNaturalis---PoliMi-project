package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class TuiChat {
    private List<ChatMessage> messages;

    public TuiChat() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(ChatMessage message) {
        this.messages.add(message);
    }

    public void printChat() {
        String border = new String(new char[50]).replace('\0', '-');
        String resetColor = "\u001B[0m";
        String senderColor = "\u001B[31m";
        String recipientColor = "\u001B[32m";
        String messageColor = "\u001B[34m";

        for (int i = 0; i < messages.size(); i++) {
            System.out.println(border);
            System.out.println(senderColor + "From: " + messages.get(i).getSender() + resetColor);
            System.out.println(recipientColor + "To: " + messages.get(i).getRecipient() + resetColor);
            System.out.println("\n" + messageColor + "Message: ");
            System.out.println(messages.get(i).getMessage() + resetColor);
            System.out.println(border);
            System.out.println("\n");
        }
    }
}