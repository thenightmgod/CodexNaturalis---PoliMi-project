package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.RMIClient;
import it.polimi.ingsw.View.GUI.GUI;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class LoginController extends GUIController {

    protected  CommonClient client;
    protected Parent root;
    protected  Stage stage;
    // protected ProtocolController pc;
    protected  GUI gui;
    boolean serverIpEntered=false;
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

    @FXML
    private void submit(ActionEvent event) throws IOException {
        if (!serverIpEntered) {
            animateLabelText(myLabel,"Insert the server ip");
            serverIp = myText.getText();
         /*   if (serverIp == null || serverIp.isEmpty()) {
                showAlert("Error", "Server IP cannot be empty.");
                return;
            }else if (!isValidFormat(serverIp)) {
                showAlert("Error", "This is not a valid format!");
                return;
            }*/
            myText.clear();
            animateLabelText(myLabel,"Now insert your nickname");
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
            animateLabelText(myLabel,"Choose your protocol");
            myButton1.setVisible(true);
            myButton1.setDisable(false);
            myButton2.setVisible(true);
            myButton2.setDisable(false);
        }
    }
    public void insertNicknameAgain() {
        myLabel.setVisible(true);
        myNicknameButton.setDisable(false);
        myNicknameButton.setVisible(true);
        myText.clear();
        myText.setVisible(true);
        myText.setDisable(false);
        animateLabelText(myLabel,"Please insert your nickname");
    }

    @FXML
    public void submitNickname(ActionEvent event) throws NotBoundException, RemoteException {
        username= myText.getText();
        myText.clear();
        if (username == null || username.isEmpty()) {
            showAlert("Error", "Username cannot be empty.");
            return;
        }
        gui.setName(username);
        client=new RMIClient(username,serverIp);
        gui.setClient(client);
        joinGame();
        myNicknameButton.setVisible(false);
        myNicknameButton.setDisable(true);
        myText.setVisible(false);
        myText.setDisable(true);
        myLabel.setVisible(false);
        //if(VirtualServer server = client.getServer())
    }


    @FXML
    public void startRMI(ActionEvent event)  {
        try {
            client= new RMIClient(username, serverIp);
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

    public void joinGame() {
        try {
            client.joinGame(username);
        } catch (RemoteException e) {
            System.out.println("there's been a problem in the join game");
        }
    }

    public void showException(String exception) {
        Platform.runLater(() -> {
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
                default -> {
                }
            }
        });
    }

    public void createGame() {
        myLabel.setVisible(true);
        animateLabelText(myLabel,"Enter the number of players");
        myText.setText("");
        myText.setVisible(true);
        myButton3.setVisible(true);
        myButton3.setDisable(false);
    }

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

    public void showWaitingScene() {
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

    public void showGameScene() throws IOException {
        URL fxmlUrl = getClass().getResource("/view/Game.fxml");
        if (fxmlUrl == null) {
            throw new RuntimeException("FXML file not found");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setLocation(fxmlUrl);
        Parent root= loader.load();

        Scene scene = new Scene(root, 1250,650);
        GuiGameController guiGameController =loader.getController();
        guiGameController.setClient(client);
        guiGameController.setRoot(root);
        guiGameController.setScene(gui,stage);
        gui.setGameController(guiGameController);
        stage.setScene(scene);
    }
    private Image loadImage(String imagePath) {
        try {
            URL resourceUrl = getClass().getResource(imagePath);
            if (resourceUrl == null) {
                System.out.println("Resource not found: " + imagePath);
                return null;
            }
            return new Image(resourceUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //--------STARTARE IL SOCKET!!!-----------------
    @FXML
    public void startSocket(ActionEvent event) {
        System.out.println("Stai startando un socketserver");

    }
    public boolean isValidFormat(String input) {

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
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // Null per non mostrare intestazione
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void showLoginScene() {
        try {
            File fxmlFile = new File("/view/login.fxml");
            URL fxmlUrl = fxmlFile.toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlUrl);
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setRoot(root);
            loginController.setScene(gui, stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("MyCodexNaturalis");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setScene(GUI gui, Stage stage) {
        gui.setLoginController(this);
        this.gui=gui;
        this.stage=stage;
    }
    public void setClient(CommonClient client) {
        this.client=client;
    }

    public void stop() throws Exception {
        super.stop();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
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

        timeline.setCycleCount(Timeline.INDEFINITE); // Ripeti indefinitamente
        timeline.play();
    }
}