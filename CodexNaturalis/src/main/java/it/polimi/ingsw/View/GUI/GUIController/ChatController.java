package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.View.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ChatController extends GUIController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox messageVBox;
    @FXML
    private TextArea messageInput;
    @FXML
    private Button sendButton;
    @FXML
    private VBox playersBox;
    @FXML
    private Label errorLabel;
    private List<String> activePlayers = new LinkedList<>();
    private List<CheckBox> playerCheckBoxes = new LinkedList<>();
    private CheckBox everyoneCheckBox; // CheckBox for "everyone"
    private Set<String> displayedMessages = new HashSet<>(); // Utilizziamo una Set di stringhe per controllare i messaggi unici

    public void initializeChat() {
        updateActivePlayers();
        loadMessages(client.getClient().getChat());
        errorLabel.setVisible(false);
        BackgroundFill backgroundFill = new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, null);
        Background background = new Background(backgroundFill);
        rootPane.setBackground(background);
    }

    public void updateActivePlayers() {
        LinkedList<String> allPlayers = client.getClient().getPlayers();
        String myName = client.getClient().getName();
        allPlayers.remove(myName);

        activePlayers.clear();
        activePlayers.addAll(allPlayers);
        createPlayerCheckboxes();
    }

    private void createPlayerCheckboxes() {
        playersBox.getChildren().clear();
        playerCheckBoxes.clear();

        // Create and add the "everyone" checkbox
        everyoneCheckBox = new CheckBox("everyone");
        everyoneCheckBox.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        everyoneCheckBox.setPadding(new Insets(5));
        everyoneCheckBox.setOnAction(event -> handleEveryoneCheckBox());

        playersBox.getChildren().add(everyoneCheckBox);

        for (String playerName : activePlayers) {
            CheckBox checkBox = new CheckBox(playerName);
            checkBox.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            checkBox.setPadding(new Insets(5));
            checkBox.setOnAction(event -> handleIndividualCheckBox(checkBox));

            playersBox.getChildren().add(checkBox);
            playerCheckBoxes.add(checkBox);
        }
    }

    private void handleEveryoneCheckBox() {
        if (everyoneCheckBox.isSelected()) {
            for (CheckBox checkBox : playerCheckBoxes) {
                checkBox.setSelected(false);
                checkBox.setDisable(true);
            }
        } else {
            for (CheckBox checkBox : playerCheckBoxes) {
                checkBox.setDisable(false);
            }
        }
    }

    private void handleIndividualCheckBox(CheckBox selectedCheckBox) {
        if (selectedCheckBox.isSelected()) {
            everyoneCheckBox.setSelected(false);
            everyoneCheckBox.setDisable(true);
            for (CheckBox checkBox : playerCheckBoxes) {
                if (checkBox != selectedCheckBox) {
                    checkBox.setSelected(false);
                    checkBox.setDisable(true);
                }
            }
        } else {
            everyoneCheckBox.setDisable(false);
            for (CheckBox checkBox : playerCheckBoxes) {
                checkBox.setDisable(false);
            }
        }
    }

    public void loadMessages(LinkedList<ChatMessage> chat) {
        for (ChatMessage message : chat) {
            String uniqueMessageIdentifier = message.getSender() + ": " + message.getMessage();
            if (!displayedMessages.contains(uniqueMessageIdentifier)) {
                Text senderText = new Text(message.getSender() + " ");
                senderText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                senderText.setFill(Color.DARKGREEN);

                String recipient = message.getRecipient().equals("everyone") ? "everyone" : message.getRecipient();
                Text recipientText = new Text("to " + recipient + ": ");
                recipientText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                recipientText.setFill(Color.DARKGREEN);

                Text messageText = new Text(message.getMessage());
                messageText.setFont(Font.font("Arial", 14));
                messageText.setFill(Color.BLACK);

                HBox messageBox = new HBox(senderText, recipientText, messageText);
                messageVBox.getChildren().add(messageBox);
                displayedMessages.add(uniqueMessageIdentifier);
            }
        }
    }

    @FXML
    public void sendMessage(ActionEvent event) {
        String messageText = messageInput.getText().trim();

        if (!messageText.isEmpty()) {
            List<String> selectedPlayers = new LinkedList<>();
            boolean everyone = false;

            if (everyoneCheckBox.isSelected()) {
                everyone = true;
                selectedPlayers.addAll(activePlayers);
                selectedPlayers.add(client.getClient().getName());
            } else {
                for (CheckBox checkBox : playerCheckBoxes) {
                    if (checkBox.isSelected()) {
                        selectedPlayers.add(checkBox.getText());
                    }
                }
            }

            if (selectedPlayers.isEmpty()) {
                errorLabel.setText("Please select at least one participant.");
                errorLabel.setVisible(true);
                return;
            } else {
                errorLabel.setVisible(false);
            }

            for (String recipient : selectedPlayers) {
                ChatMessage message;
                if (everyone) {
                    message = new ChatMessage(messageText, client.getClient().getName(), "everyone");
                } else {
                    message = new ChatMessage(messageText, client.getClient().getName(), recipient);
                }
                try {
                    client.sendChatMessage(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                addMessageToChat(message);
            }
            messageInput.clear();
        }
    }

    private void addMessageToChat(ChatMessage message) {
        String uniqueMessageIdentifier = message.getSender() + ": " + message.getMessage();
        if (!displayedMessages.contains(uniqueMessageIdentifier)) {
            Text senderText = new Text(message.getSender() + " ");
            senderText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            senderText.setFill(Color.DARKGREEN);

            String recipient = message.getRecipient().equals("everyone") ? "everyone" : message.getRecipient();
            Text recipientText = new Text("to " + recipient + ": ");
            recipientText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            recipientText.setFill(Color.DARKGREEN);

            Text messageText = new Text(message.getMessage());
            messageText.setFont(Font.font("Arial", 14));
            messageText.setFill(Color.BLACK);

            HBox messageBox = new HBox(senderText, recipientText, messageText);
            messageVBox.getChildren().add(messageBox);
            displayedMessages.add(uniqueMessageIdentifier);
        }
    }

    public void refreshChat() {
        loadMessages(client.getClient().getChat());
    }
}
