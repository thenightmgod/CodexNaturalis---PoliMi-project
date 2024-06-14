package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.RMIClient;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GUIController.GUIController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class LoginController extends GUIController {

    protected CommonClient client;
    protected Parent root;
    protected Stage stage;
    protected ProtocolController pc;
    protected GUI gui;
    boolean serverIpEntered=false;
    protected String serverIp;
    protected String username;
    protected int number;
    protected ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @FXML
    private Rectangle myRectangle;

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
    private void initialize() {
        animateLabelText(myLabel,"Insert the server IP");
        myButton1.setVisible(false);
        myButton1.setDisable(true);
        myButton2.setVisible(false);
        myButton2.setDisable(true);
        myButton3.setVisible(false);
        myButton3.setDisable(true);
        myLabel2.setVisible(false);
        myRectangle.setVisible(false);
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
            this.gui.setName(username);
            myText.setVisible(false);
            myButton.setVisible(false);
            animateLabelText(myLabel,"Choose your protocol");
            myButton1.setVisible(true);
            myButton1.setDisable(false);
            myButton2.setVisible(true);
            myButton2.setDisable(false);
            //stage = (Stage) scenePane.getScene().getWindow();
            //stage.close();

            //carico il file Protocol
           /* File fxmlFile = new File("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/Protocol.fxml");
            URL fxmlUrl = fxmlFile.toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            pc = loader.getController();
            pc.setRoot(root);
            pc.setScene(gui, stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("MyCodexProtocol");
            pc.start(stage, username, serverIp);*/
        }
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
                    //da gestire diversamente
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Name Already Taken");
                    alert.setHeaderText("Oops, this name is already taken!");
                    alert.setContentText("Please choose another name.");
                    alert.showAndWait();
                }
                case "RoomNotExistsException" -> {
                    // Gestione eccezione RoomNotExistsException
                    // Esempio:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Welcome, you're the first!");
                    alert.setContentText("No game started, please start a new one.");
                    alert.showAndWait();
                    createGame();
                    }
                default -> {
                    // Gestione di altre eccezioni se necessario
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
                // Gestisci il caso in cui il testo non possa essere convertito in intero
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
        myLabel.setVisible(false);
        myLabel.setDisable(true);

        myText.setVisible(false);
        myText.setDisable(true);

        myButton3.setVisible(false);
        myButton3.setDisable(true);
        myRectangle.setVisible(true);
        myLabel2.setVisible(true);
        animateLabelText(myLabel2, "Waiting for other participants...");

        String newImagePath = "/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/pic6069793.jpg";
        Image newImage = loadImage(newImagePath);
        myImage.setImage(newImage);
    }
    private Image loadImage(String imagePath) {
        try {
            File file = new File(imagePath);
            String localUrl = file.toURI().toURL().toString();
            return new Image(localUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    //--------STARTARE IL SOCKET!!!-----------------
    @FXML
    public void startSocket(ActionEvent event) {
        System.out.println("Stai startando un socketserver");

    }
    public static boolean isValidFormat(String input) {

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
    private static boolean isNumeric(String str) {
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
            File fxmlFile = new File("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/login.fxml");
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
}

