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

/**
 * Abstract class for GUI controllers. This class provides common functionality for all GUI controllers.
 */
public abstract class GUIController {
    /**
     * The GUI instance associated with this controller.
     */
    protected GUI gui;
    /**
     * The stage on which the GUI is displayed.
     */
    protected Stage stage;
    /**
     * The client associated with this controller.
     */
    protected CommonClient client;
    /**
     * The root node of the scene graph.
     */
    protected Parent root;

    /**
     * Changes the style of a button when the mouse enters or exits the button.
     *
     * @param mouseEvent The mouse event that triggered this method.
     */
    @FXML
    private void highlightButton(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        if(mouseEvent.getEventType().getName().equals("MOUSE_ENTERED")){
            button.setStyle("-fx-background-color: #ffd900; -fx-border-color: #ffad00;");
        } else {
            button.setStyle("-fx-background-color: #ffd896; -fx-border-color: #ffac37;");
        }
    }
    /**
     * Sets the arguments for this controller. This method should be overridden by subclasses.
     *
     * @param args The arguments to set.
     * @throws FileNotFoundException If a file cannot be found.
     */
    public void setArgs(Object... args) throws FileNotFoundException {
    }
    /**
     * Returns the client associated with this controller.
     *
     * @return The client.
     */
    public CommonClient getClient(){
        return this.client;
    }
    /**
     * Sets the GUI instance for this controller.
     *
     * @param gui The GUI instance to set.
     */
    public void setGui(GUI gui) {
        this.gui=gui;
   }
    /**
     * Sets the stage for this controller.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage=stage;
   }
    /**
     * Sets the client for this controller.
     *
     * @param client The client to set.
     */
    public void setClient(CommonClient client) {
        this.client = client;
    }
    /**
     * Sets the root node for this controller.
     *
     * @param root The root node to set.
     */
    public void setRoot(Parent root) {
        this.root = root;
    }
    /**
     * Returns the scene associated with this controller.
     *
     * @return The scene.
     */
    public Scene getScene() {
        return new Scene(root);
    }

    
    public void showException(String exception) {
    }
    public void stop()throws Exception {
    }
    /**
     * Loads the front image of a card.
     *
     * @param cardId The ID of the card.
     * @return The loaded image.
     * @throws FileNotFoundException If the image file cannot be found.
     */
    protected Image loadCardFrontImage(int cardId) throws FileNotFoundException {
        String cardPath = "/view/CODEX_cards_gold_front/" + String.format("%03d", cardId) + ".png";
        return loadImage(cardPath);
    }
    /**
     * Loads an image corresponding to a specific PlayerColor.
     *
     * @param c The PlayerColor for which to load the image.
     * @return The loaded image.
     * @throws FileNotFoundException If the image file cannot be found.
     */
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


    /**
     * Loads the back image of a card based on the card ID. The card ID determines the path of the image file.
     *
     * @param cardId The ID of the card.
     * @return The loaded image.
     * @throws FileNotFoundException If the image file cannot be found.
     */
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
    /**
     * Loads an image from a given path. If the image file cannot be found, this method returns null.
     *
     * @param imagePath The path of the image file.
     * @return The loaded image, or null if the image file cannot be found.
     */
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
