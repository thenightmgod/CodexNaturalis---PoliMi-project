package it.polimi.ingsw.View.GUI.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.LinkedList;

/**
 * This class is a controller for the Winner view in the GUI.
 * It extends the GUIController class and manages the display of the winner and the standings.
 */
public class WinnerController extends GUIController {
    /**
     * A LinkedList of Strings representing the standings.
     */
    private LinkedList<String> standings;
    /**
     * The label for the first place player.
     */
    @FXML
    private Label labelUno;
    /**
     * The label for the second place player.
     */
    @FXML
    private Label labelDue;
    /**
     * The label for the third place player.
     */
    @FXML
    private Label labelTre;
    /**
     * The label for the fourth place player.
     */
    @FXML
    private Label labelQuattro;
    /**
     * The image view for the first place player.
     */
    @FXML
    private ImageView image1;
    /**
     * The image view for the second place player.
     */
    @FXML
    private ImageView image2;
    /**
     * The image view for the third place player.
     */
    @FXML
    private ImageView image3;
    /**
     * The image view for the fourth place player.
     */
    @FXML
    private ImageView image4;

    /**
     * Sets the arguments for the controller.
     * In this case, it sets the standings and calls the appropriate method to load the labels and images.
     * @param args The arguments to be set. The first argument should be a LinkedList of Strings representing the standings.
     */
    @Override
    public void setArgs(Object... args){
        standings = (LinkedList<String>) args[0];
        int size = standings.size();
        switch(size) {
            case 2 -> {
                loadLabelDue();
            }
            case 3 -> {
                loadLabelTre();
            }
            case 4 -> {
                loadLabelQuattro();
            }
        }
    }
    /**
     * Loads the labels and images for the first and second place.
     */
    private void loadLabelDue(){
        Image primo = loadImage("/view/MyCodexNaturalisPhotos/oro.png");
        image1.setImage(primo);
        image1.setFitHeight(110.0);
        image1.setFitWidth(110.0);
        labelUno.setText(standings.get(0));
        labelUno.setVisible(true);
        Image secondo = loadImage("/view/MyCodexNaturalisPhotos/argento.png");
        image2.setImage(secondo);
        image2.setFitHeight(110.0);
        image2.setFitWidth(110.0);
        labelDue.setText(standings.get(1));
        labelDue.setVisible(true);
    }

    /**
     * Loads the labels and images for the first, second, and third place.
     */
    private void loadLabelTre(){
        Image primo = loadImage("/view/MyCodexNaturalisPhotos/oro.png");
        image1.setImage(primo);
        labelUno.setText(standings.get(0));
        labelUno.setVisible(true);
        Image secondo = loadImage("/view/MyCodexNaturalisPhotos/argento.png");
        image2.setImage(secondo);
        labelDue.setText(standings.get(1));
        labelDue.setVisible(true);
        Image terzo = loadImage("/view/MyCodexNaturalisPhotos/bronzo.png");
        image3.setImage(terzo);
        labelTre.setText(standings.get(2));
        labelTre.setVisible(true);
    }

    /**
     * Loads the labels and images for the first, second, third, and fourth place.
     */
    private void loadLabelQuattro(){
        Image primo = loadImage("/view/MyCodexNaturalisPhotos/oro.png");
        image1.setImage(primo);
        image1.setFitHeight(110.0);
        image1.setFitWidth(110.0);
        labelUno.setText(standings.get(0));
        labelUno.setVisible(true);
        Image secondo = loadImage("/view/MyCodexNaturalisPhotos/argento.png");
        image2.setImage(secondo);
        image2.setFitHeight(110.0);
        image2.setFitWidth(110.0);
        labelDue.setText(standings.get(1));
        labelDue.setVisible(true);
        Image terzo = loadImage("/view/MyCodexNaturalisPhotos/bronzo.png");
        image3.setImage(terzo);
        image3.setFitHeight(110.0);
        image3.setFitWidth(110.0);
        labelTre.setText(standings.get(2));
        labelTre.setVisible(true);
        Image quarto = loadImage("/view/MyCodexNaturalisPhotos/carbone.png");
        image4.setImage(quarto);
        image4.setFitHeight(110.0);
        image4.setFitWidth(110.0);
        labelQuattro.setText(standings.get(3
        ));
        labelQuattro.setVisible(true);
    }
}
