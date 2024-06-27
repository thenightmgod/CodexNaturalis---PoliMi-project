package it.polimi.ingsw.View.GUI.GUIController;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.CommonClient;

import it.polimi.ingsw.View.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void setArgs(Object... args) {
    }

    public CommonClient getClient(){
        return this.client;
    }

    public void setGui(GUI gui) {
        this.gui=gui;
   }

    public void setStage(Stage stage) {
        this.stage=stage;
   }

    public void setClient(CommonClient client) {
        this.client = client;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Scene getScene() {
        return new Scene(root);
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

    protected Image loadColorImage(PlayerColor c) throws FileNotFoundException {
        String colorPath = "carlos";
        if(c == PlayerColor.YELLOW)
            colorPath = "/view/MyCodexNaturalisPhotos/CODEX_pion_yellow.png";
        else if(c == PlayerColor.RED)
            colorPath = "/view/MyCodexNaturalisPhotos/CODEX_pion_red.png";
        else if(c == PlayerColor.BLUE)
            colorPath = "/view/MyCodexNaturalisPhotos/CODEX_pion_blue.png";
        else
            colorPath = "/view/MyCodexNaturalisPhotos/CODEX_pion_green.png";
        return loadImage(colorPath);
    }



    protected Image loadCardBackImage(int cardId) throws FileNotFoundException {
        int path = 0;
        if (cardId >= 1 && cardId <= 10)
            path = 1;
        else if (cardId >= 11 && cardId <= 20)
            path = 11;
        else if( cardId >= 21 && cardId <= 30)
            path = 21;
        else if( cardId >= 31 && cardId <= 40)
            path = 31;
        else if( cardId >= 41 && cardId <= 50)
            path = 41;
        else if( cardId >= 51 && cardId <= 60)
            path = 51;
        else if( cardId >= 61 && cardId <= 70)
            path = 66;
        else if( cardId >= 71 && cardId <= 80)
            path = 73;
        else if( cardId == 81)
            path = 81;
        else if( cardId == 82)
            path = 82;
        else if( cardId == 83)
            path = 83;
        else if( cardId == 84)
            path = 84;
        else if( cardId == 85)
            path = 85;
        else if( cardId == 86)
            path = 86;
        else if( cardId >= 87)
            path = 87;
        String cardPath = "/view/CODEX_cards_gold_back/" + String.format("%03d", path) + ".png";
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
