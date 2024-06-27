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
/**
 * Controller for the chat feature in the GUI.
 */
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
    private Set<String> displayedMessages = new HashSet<>(); // Utilizziamo una Set di stringhe per controllare i messaggi unici

    /**
     * Initializes the chat by updating the active players, loading messages, and setting the background color.
     */
    public void initializeChat() {
        updateActivePlayers();
        loadMessages(client.getClient().getChat());
        errorLabel.setVisible(false);
        BackgroundFill backgroundFill = new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, null);
        Background background = new Background(backgroundFill);
        rootPane.setBackground(background);
    }

    /**
     * Updates the list of active players by getting the list of all players and removing the current client's name.
     */
    public void updateActivePlayers() {
        LinkedList<String> allPlayers = client.getClient().getPlayers();
        String myName = client.getClient().getName();
        allPlayers.remove(myName);

        activePlayers.clear();
        activePlayers.addAll(allPlayers);
        createPlayerCheckboxes();
    }

    /**
     * Creates checkboxes for each active player and adds them to the playersBox VBox.
     */
    private void createPlayerCheckboxes() {
        playersBox.getChildren().clear();

        for (String playerName : activePlayers) {
            CheckBox checkBox = new CheckBox(playerName);
            checkBox.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            checkBox.setPadding(new Insets(5));

            playersBox.getChildren().add(checkBox);
            playerCheckBoxes.add(checkBox);
        }
    }

    /**
     * Loads messages from the chat and displays them in the messageVBox VBox. Each message is displayed as a HBox containing
     * the sender's name and the message text.
     *
     * @param chat The list of chat messages to load.
     */
    public void loadMessages(LinkedList<ChatMessage> chat) {
        for (ChatMessage message : chat) {
            String uniqueMessageIdentifier = message.getSender() + ": " + message.getMessage();
            if (!displayedMessages.contains(uniqueMessageIdentifier)) {
                Text senderText = new Text(message.getSender() + ": ");
                senderText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                senderText.setFill(Color.DARKGREEN);

                Text messageText = new Text(message.getMessage());
                messageText.setFont(Font.font("Arial", 14));
                messageText.setFill(Color.BLACK);

                HBox messageBox = new HBox(senderText, messageText);
                messageVBox.getChildren().add(messageBox);
                displayedMessages.add(uniqueMessageIdentifier);
            }
        }
    }
    /**
     * Sends a message to the selected players. The message text is retrieved from the messageInput TextArea. If no players
     * are selected, an error message is displayed.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void sendMessage(ActionEvent event) {
        String messageText = messageInput.getText().trim();

        if (!messageText.isEmpty()) {
            List<String> selectedPlayers = new LinkedList<>();

            for (CheckBox checkBox : playerCheckBoxes) {
                if (checkBox.isSelected()) {
                    selectedPlayers.add(checkBox.getText());
                }
            }

            if (selectedPlayers.isEmpty()) {
                errorLabel.setText("Please select at least one participant.");
                errorLabel.setVisible(true);
                return;
            } else {
                errorLabel.setVisible(false);
            }

            selectedPlayers.add(client.getClient().getName());

            for (String recipient : selectedPlayers) {
                ChatMessage message = new ChatMessage(messageText, client.getClient().getName(), recipient);
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

    /**
     * Adds a message to the chat by creating a HBox containing the sender's name and the message text, and adding it to the
     * messageVBox VBox.
     *
     * @param message The message to add to the chat.
     */
    private void addMessageToChat(ChatMessage message) {
        String uniqueMessageIdentifier = message.getSender() + ": " + message.getMessage();
        if (!displayedMessages.contains(uniqueMessageIdentifier)) {
            Text senderText = new Text(message.getSender() + ": ");
            senderText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            senderText.setFill(Color.DARKGREEN);

            Text messageText = new Text(message.getMessage());
            messageText.setFont(Font.font("Arial", 14));
            messageText.setFill(Color.BLACK);

            HBox messageBox = new HBox(senderText, messageText);
            messageVBox.getChildren().add(messageBox);
            displayedMessages.add(uniqueMessageIdentifier);
        }
    }

    /**
     * Refreshes the chat by loading messages from the client's chat.
     */
    public void refreshChat() {
        loadMessages(client.getClient().getChat());
    }
}
