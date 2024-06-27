package it.polimi.ingsw.View.GUI;


import it.polimi.ingsw.View.GUI.GUIController.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Runnable class for the GUI
 */

public class GUIApplication extends Application {
    private String[] args;


    @Override
    public void start(Stage stage) throws IOException {

        try {
            GUI gui=new GUI();
            gui.start(args, stage);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

