package it.polimi.ingsw.View.GUI.GUIController;
import it.polimi.ingsw.Network.CommonClient;

import it.polimi.ingsw.View.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;


public abstract class GUIController {
    protected GUI gui;
    protected Stage stage;

    protected CommonClient client;
    protected Parent root;

    @FXML
    private void highlightButton(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        if(mouseEvent.getEventType().getName().equals("MOUSE_ENTERED")){
            button.setStyle("-fx-background-color: #ffd900; -fx-border-color: #ffad00;");
        } else {
            button.setStyle("-fx-background-color: #ffd896; -fx-border-color: #ffac37;");
        }
    }

    public void setScene(GUI gui, Stage stage) {
        this.gui=gui;
        this.stage=stage;
    }
    public void setClient(CommonClient client) {
        this.client = client;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
    public void showException(String exception) {
    }
    //eventuali pulizie
    public void stop()throws Exception {
    }
    protected Image loadCardFrontImage(int cardId) throws FileNotFoundException {
        String cardPath = "/view/CODEX_cards_gold_front/" + String.format("%03d", cardId) + ".png";
        return loadImage(cardPath);
    }
    protected Image loadCardBackImage(int cardId) throws FileNotFoundException {
        String cardPath = "/view/CODEX_cards_gold_back/" + String.format("%03d", cardId) + ".png";
        return loadImage(cardPath);
    }

    protected Image loadImage(String imagePath) {
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

}
