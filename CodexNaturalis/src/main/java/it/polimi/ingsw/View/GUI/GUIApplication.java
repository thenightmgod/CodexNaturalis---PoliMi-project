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
            //----------STARTA IL LOGIN CONTROLLER-----------------------
            String userDir = System.getProperty("user.dir");

            File fxmlFile = new File(userDir, "/src/main/resources/view/login.fxml");
            URL fxmlUrl = fxmlFile.toURI().toURL();
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(fxmlUrl);
            Parent root= loader.load();
            LoginController controller=loader.getController();
            controller.setRoot(root);
            Scene scene=new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
            stage.setTitle("MyCodexNaturalis");

            GUI gui=new GUI();
            gui.setArgs(args);
            gui.setLoginController(controller);
            controller.setScene(gui,stage);
            stage.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

