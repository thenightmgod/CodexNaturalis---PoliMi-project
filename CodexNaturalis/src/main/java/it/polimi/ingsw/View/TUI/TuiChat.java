package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.View.ChatMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
/**
 * This class represents the chat feature of the application for the Text User Interface (TUI)
 */
public class TuiChat {
    /**
     * A list of chat messages.
     */
    private LinkedList<ChatMessage> messages;
    /**
     * Constructor for the TuiChat class.
     */
    public TuiChat() {
        this.messages = new LinkedList<>();
    }
    /**
     * Sets the list of chat messages.
     *
     * @param messages The list of chat messages.
     */
    public void setMessages(LinkedList<ChatMessage> messages){
        this.messages = messages;
    }

    /**
     * Prints the chat messages. It separates public messages from private messages.
     *
     * @param myUsername The username of the current user.
     */
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
    /**
     * Prints private messages.
     *
     * @param messages The list of private messages.
     * @param border The border to be printed.
     * @param resetColor The reset color code.
     * @param senderColor The sender color code.
     * @param recipientColor The recipient color code.
     * @param messageColor The message color code.
     * @param myUsername The username of the current user.
     */
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
    /**
     * Prints public messages.
     *
     * @param messages The list of private messages.
     * @param border The border to be printed.
     * @param resetColor The reset color code.
     * @param senderColor The sender color code.
     * @param recipientColor The recipient color code.
     * @param messageColor The message color code.
     */
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

}