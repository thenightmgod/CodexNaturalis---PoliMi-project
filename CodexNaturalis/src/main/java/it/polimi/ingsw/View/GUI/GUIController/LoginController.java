package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Network.RMI.RMIClient;
import it.polimi.ingsw.Network.Socket.SocketClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
/**
 * Controller for the Login feature in the GUI. This class handles the user input for server IP, username, and connection type (RMI or Socket).
 * It also manages the creation of a new game and the joining of an existing game.
 */
public class LoginController extends GUIController {
    boolean serverIpEntered=false;
    boolean connectionType;
    protected String serverIp;
    protected String username;
    protected int number;
    protected ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @FXML
    private ProgressBar myProgressBar;
    @FXML
    private Label myLabel2;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextField myText;
    @FXML
    private Label myLabel;
    @FXML
    private ImageView myImage;
    @FXML
    private Button myButton;
    @FXML
    private Button myButton1;
    @FXML
    private Button myButton2;
    @FXML
    private Button myButton3;
    @FXML
    private Button myNicknameButton;
    /**
     * Initializes the controller by setting up the UI elements.
     */
    @FXML
    private void initialize() {
        animateLabelText(myLabel,"Insert the server IP");
        myButton1.setVisible(false);
        myButton1.setDisable(true);
        myButton2.setVisible(false);
        myButton2.setDisable(true);
        myButton3.setVisible(false);
        myButton3.setDisable(true);
        myLabel2.setVisible(false);
        myProgressBar.setDisable(true);
        myProgressBar.setVisible(false);
        myNicknameButton.setVisible(false);
        myNicknameButton.setDisable(true);
    }
    /**
     * Handles the submit action. If the server IP has not been entered, it validates and stores the server IP.
     * If the server IP has been entered, it validates and stores the username.
     *
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void submit(ActionEvent event) throws IOException {
        if (!serverIpEntered) {
            serverIp = myText.getText();
            if (!isValidFormat(serverIp)) {
                showAlert("Error", "This is not a valid format!");
                return;
            }
            myText.clear();
            animateLabelText(myLabel, "Now insert your nickname");
            serverIpEntered = true;
        } else {
            username = myText.getText();
            myText.clear();
            if (username == null || username.isEmpty()) {
                showAlert("Error", "Username cannot be empty.");
                return;
            }
            gui.setName(username);
            myText.setVisible(false);
            myButton.setVisible(false);
            animateLabelText(myLabel, "Choose your protocol");
            myButton1.setVisible(true);
            myButton1.setDisable(false);
            myButton2.setVisible(true);
            myButton2.setDisable(false);
        }
    }
    /**
     * Prompts the user to enter their nickname again.
     */
    public void insertNicknameAgain() {
        myLabel.setVisible(true);
        myNicknameButton.setDisable(false);
        myNicknameButton.setVisible(true);
        myText.clear();
        myText.setVisible(true);
        myText.setDisable(false);
        animateLabelText(myLabel,"Please insert your nickname");
    }
    /**
     * Handles the submit action for the nickname. It validates and stores the username.
     *
     * @param event The ActionEvent that triggered this method.
     * @throws NotBoundException If the client cannot be bound.
     * @throws RemoteException If a remote method invocation error occurs.
     */
    @FXML
    public void submitNickname(ActionEvent event) throws NotBoundException, RemoteException {
        username = myText.getText();
        myText.clear();
        if (username == null || username.isEmpty()) {
            showAlert("Error", "Username cannot be empty.");
            return;
        }
        gui.setName(username);
        if(!(connectionType)) {
            ((RMIClient) this.client ).setName(username);
        }
        else{
            ((SocketClient) this.client).setName(username);
        }
        joinGame();
        myNicknameButton.setVisible(false);
        myNicknameButton.setDisable(true);
        myText.setVisible(false);
        myText.setDisable(true);
        myLabel.setVisible(false);
    }

    /**
     * Starts the RMI client.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void startRMI(ActionEvent event)  {
        try {
            client= new RMIClient(username, serverIp);
            connectionType = false;
            gui.setClient(client);
            client.setView(gui);
            joinGame();
            myButton1.setVisible(false);
            myButton1.setDisable(true);
            myButton2.setVisible(false);
            myButton2.setDisable(true);
        }catch (RemoteException e) {
            System.out.println("an exception occurred while starting the client");
        } catch (NotBoundException e){
            System.out.print("NotBoundException occurred while initializing the client");
        }
    }
    /**
     * Joins the game with the stored username.
     */
    public void joinGame() {
        try {
            client.joinGame(username);
        } catch (RemoteException e) {
            System.out.println("there's been a problem in the join game");
        }
    }
    /**
     * Shows an exception message to the user based on the type of exception.
     *
     * @param exception The type of exception.
     */
    @Override
    public void showException(String exception) {
            switch(exception) {
                case "NameAlreadyTakenException" -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Name Already Taken");
                    alert.setHeaderText("Oops, this name is already taken!");
                    alert.setContentText("Please choose another name.");
                    alert.showAndWait();
                    insertNicknameAgain();
                }
                case "RoomNotExistsException" -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Welcome, you're the first!");
                    alert.setContentText("No game started, please start a new one.");
                    alert.showAndWait();
                    createGame();
                }
                case "RoomFullException" -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Room is full");
                    alert.setContentText("The room is full, please start a new one");
                    alert.showAndWait();
                    createGame();
                }
                default -> {
                }
            }
    }
    /**
     * Prompts the user to create a new game.
     */
    public void createGame() {
        myLabel.setVisible(true);
        animateLabelText(myLabel,"Enter the number of players");
        myText.setText("");
        myText.setVisible(true);
        myButton3.setVisible(true);
        myButton3.setDisable(false);
    }
    /**
     * Handles the submit action for the number of players. It validates and stores the number of players, and creates a new game.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void enterNumberOfPlayers(ActionEvent event) {
        boolean goon = false;
        do {
            String numofPlayers= myText.getText();
            myText.clear();
            if (numofPlayers.isEmpty() || !numofPlayers.matches("\\d+")) {
                showAlert("Error", "Please enter a valid number in the field.");
                return;
            }
            try {
                number = Integer.parseInt(numofPlayers);
                if (number < 2 || number > 4) {
                    showAlert("Error", "The number of players must be from 2 to 4!");
                    return;
                } else {
                    goon=true;
                }
            } catch (NumberFormatException e) {
                // Gestisco il caso in cui il testo non possa essere convertito in intero
                System.out.println("Impossibile convertire il testo in numero intero: " + numofPlayers);
            }
        } while(!goon);
        try {
            client.createGame(username, number);
            showWaitingScene();
        } catch ( RemoteException e) {
            System.out.println("There was a problem creating the game.");
        }
    }
    /**
     * Shows the waiting scene to the user.
     */
    public void showWaitingScene() {
        myImage= new ImageView();
        myNicknameButton.setVisible(false);
        myNicknameButton.setDisable(true);
        myLabel.setVisible(false);
        myLabel.setDisable(true);

        myText.setVisible(false);
        myText.setDisable(true);

        myButton3.setVisible(false);
        myButton3.setDisable(true);
        myLabel2.setVisible(true);
        myProgressBar.setVisible(true);
        myProgressBar.setDisable(false);
        updateProgressBarRandomly(myProgressBar);

        animateLabelText(myLabel2, "Waiting for other participants...");


        String newImagePath = "/view/MyCodexNaturalisPhotos/pic6069793.jpg";
        Image newImage = loadImage(newImagePath);
        myImage.setImage(newImage);
    }



    //--------STARTARE IL SOCKET!!!-----------------
    /**
     * Starts the Socket client.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void startSocket(ActionEvent event) {
        client= new SocketClient(username, serverIp);
        connectionType = true;
        gui.setClient(client);
        client.setView(gui);
        joinGame();
        myButton1.setVisible(false);
        myButton1.setDisable(true);
        myButton2.setVisible(false);
        myButton2.setDisable(true);

    }
    /**
     * Checks if the input string is a valid IP address format.
     *
     * @param input The string to check.
     * @return true if the input string is a valid IP address format, false otherwise.
     */
    public boolean isValidFormat(String input) {

        if(input.isEmpty()){
            return true;
        }
        String[] parts = input.split("\\.");

        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            if (!isNumeric(part)) {
                return false;
            }
            int num = Integer.parseInt(part);
            if (num < 0 || num > 255) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if the input string is numeric.
     *
     * @param str The string to check.
     * @return true if the input string is numeric, false otherwise.
     */
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Animates the text of a label by gradually revealing each character.
     *
     * @param label The label to animate.
     * @param text The text to display in the label.
     */
    private void animateLabelText(Label label, String text) {

        final int length = text.length();
        Timeline timeline = new Timeline();

        for (int i = 0; i <= length; i++) {
            final String partialText = text.substring(0, i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(50 * i), event -> label.setText(partialText));
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();


    }
    /**
     * Shows an error alert with the specified title and message.
     *
     * @param title The title of the alert.
     * @param message The message of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // Null per non mostrare intestazione
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Stops the execution of the controller, including shutting down the scheduler if it's running.
     *
     * @throws Exception If an error occurs while stopping the controller.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    /**
     * Shows an information alert with the specified message.
     *
     * @param message The message of the alert.
     */
    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Updates the progress of a progress bar randomly.
     *
     * @param bar The progress bar to update.
     */
    private void updateProgressBarRandomly(ProgressBar bar) {
        Timeline timeline = new Timeline();
        Random random = new Random();

        for (int i = 0; i <= 100; i++) {
            double progress = i / 100.0;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(50 * i), event -> {
                bar.setProgress(progress);
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}