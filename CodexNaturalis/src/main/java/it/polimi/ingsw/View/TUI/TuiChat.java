package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Chat.ChatMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TuiChat {
    private LinkedList<ChatMessage> messages;

    public TuiChat() {
        this.messages = new LinkedList<>();
    }

    public void setMessages(LinkedList<ChatMessage> messages){
        this.messages = messages;
    }


    public void printChat(String myUsername) {
        String border = new String(new char[50]).replace('\0', '-');
        String resetColor = "\u001B[0m";
        String senderColor = "\u001B[31m";
        String recipientColor = "\u001B[32m";
        String messageColor = "\u001B[31m";

        List<ChatMessage> privateMessages = new ArrayList<>();
        List<ChatMessage> publicMessages = new ArrayList<>();

        for (ChatMessage message : messages) {
            if (message.getRecipient().equals("everyone")) {
                publicMessages.add(message);
            } else if (message.getRecipient().equals(myUsername) || message.getSender().equals(myUsername)) {
                privateMessages.add(message);
            }
        }

        System.out.println("Public chat:");
        printMessagesPu(publicMessages, border, resetColor, senderColor, recipientColor, messageColor);

        System.out.println("Private chat:");
        printMessagesPp(privateMessages, border, resetColor, senderColor, recipientColor, messageColor, myUsername);
    }

    private void printMessagesPp(List<ChatMessage> messages, String border, String resetColor, String senderColor, String recipientColor, String messageColor, String myUsername) {
        if (messages.isEmpty()) {
            System.out.println("No messages to show");
            return;
        }

        System.out.println(border);
        for (ChatMessage message : messages) {
            if(message.getRecipient().equals(myUsername)) {
                System.out.println(senderColor + "[From " + message.getSender() + " to you]" + resetColor + ":" + message.getMessage());
            } else {
                System.out.println(senderColor + "[From you to " + message.getRecipient() + "]" + resetColor + ":" + message.getMessage());
            }
        }
        System.out.println(border);
        System.out.println("\n");
    }

    private void printMessagesPu(List<ChatMessage> messages, String border, String resetColor, String senderColor, String recipientColor, String messageColor) {
        if (messages.isEmpty()) {
            System.out.println("No messages to show");
            return;
        }
        System.out.println(border);
        for(ChatMessage message : messages) {
            System.out.println(senderColor + "[" + message.getSender() + "]" + resetColor + ":" + message.getMessage() + resetColor);
           }
        System.out.println(border);
        System.out.println("\n");

    }

    /*public void printChat() {
        String border = new String(new char[50]).replace('\0', '-');
        String resetColor = "\u001B[0m";
        String senderColor = "\u001B[31m";
        String recipientColor = "\u001B[32m";
        String messageColor = "\u001B[34m";

        for (int i = 0; i < messages.size(); i++) {
            if(messages.size() == 0){
                System.out.println("No messages to show");
                return;
            }
            System.out.println(border);
            System.out.println(senderColor + "From: " + messages.get(i).getSender() + resetColor);
            System.out.println(recipientColor + "To: " + messages.get(i).getRecipient() + resetColor);
            System.out.println("\n" + messageColor + "Message: ");
            System.out.println(messages.get(i).getMessage() + resetColor);
            System.out.println(border);
            System.out.println("\n");
        }
    }  */

}