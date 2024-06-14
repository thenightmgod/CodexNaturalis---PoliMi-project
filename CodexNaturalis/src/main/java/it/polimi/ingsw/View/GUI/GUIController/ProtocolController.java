package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.RMIClient;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ProtocolController extends GUIController{
    protected CommonClient client;
    protected GUI gui;
    protected String username;
    protected String serverIp;
    protected Parent root;
    @FXML
    Label myLabel;
    @FXML
    Button RMIButton;
    @FXML
    Button SOCKETButton;



    public void setColor() {
        myLabel.setBackground(Background.fill(Color.LIGHTSKYBLUE));
    }

    public void start(Stage stage, String username, String serverIp) throws IOException {
        setColor();
        this.username= username;
        this.serverIp=serverIp;
        stage.show();
    }


    public void chooseRMI(ActionEvent event) {
        try {
            //DA METTERE IL SERVER IP!!!!
            client= new RMIClient(username, serverIp);
            client.setView(this.gui);
            this.client.joinGame(username);

        }catch (NotBoundException e) {
            System.out.print("NotBoundException occurred while initializing the client");
        } catch (RemoteException e) {
            System.out.println("an exception occurred while starting the client");
        }
    }

    @Override
    public void showException(String exception) {
        switch(exception) {
            case "NameAlreadyTakenException"-> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Name Already Taken");
                alert.setHeaderText("Ooooops, this name is already taken!");
                alert.setContentText("Please choose another name.");

                alert.showAndWait();
                gui.getLoginController().showLoginScene();
            }
        }
    }

    public void chooseSocket(ActionEvent event) {
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
    public void setScene(GUI gui, Stage stage) {
       // gui.setProtocolController(this);
        super.setScene(gui, stage);
    }
    public void setClient(CommonClient client) {
        this.client = client;
    }
}
