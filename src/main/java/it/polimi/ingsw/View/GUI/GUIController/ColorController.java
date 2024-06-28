package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
/**
 * Controller for the color selection feature in the GUI. This class handles the loading of colors, enabling and disabling color interactions,
 * and handling mouse events related to color selection.
 */
public class ColorController extends GUIController{

    @FXML
    private Pane myPane;
    @FXML
    private Label myLabel;
    @FXML
    private ImageView yellow;
    @FXML
    private ImageView blue;
    @FXML
    private ImageView green;
    @FXML
    private ImageView red;


    private boolean flag;
    /**
     * A list of PlayerColor objects representing the colors available for players.
     */
    private LinkedList<PlayerColor> colors;
    /**
     * A boolean indicating whether a color has been chosen by the player.
     */
    protected boolean colorchosen;

    /**
     * Sets the arguments for this controller. The arguments should include a list of colors and a flag indicating whether to load the colors.
     *
     * @param args The arguments for this controller.
     * @throws FileNotFoundException If an error occurs while loading the color images.
     */
    @Override
    public void setArgs(Object... args) throws FileNotFoundException {
        this.colors = (LinkedList<PlayerColor>) args[0];
        this.flag = (boolean) args[1];
        if(flag) loadColor();
        else waitYourTurn();
    }
    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        colorchosen=false;
        myLabel.setVisible(true);
    }

    /**
     * Loads the colors in a disabled state. This method is used when it's not the client's turn to choose a color.
     * @throws FileNotFoundException If an error occurs while loading the color images.
     */
    private void loadColordisabled() throws FileNotFoundException {
        disableColorInteractions();
        myLabel.setText("WAIT, IT'S NOT YOUR TURN");
        for(int i=0; i< colors.size(); i++){
            if(colors.get(i) == PlayerColor.YELLOW){
                setImageDisabledForColor(yellow, colors.get(i));
                yellow.setVisible(true);
            } else if(colors.get(i) == PlayerColor.BLUE){
                setImageDisabledForColor(blue, colors.get(i));
                blue.setVisible(true);
            } else if(colors.get(i) == PlayerColor.GREEN){
                setImageDisabledForColor(green, colors.get(i));
                green.setVisible(true);
            } else if(colors.get(i) == PlayerColor.RED){
                setImageDisabledForColor(red, colors.get(i));
                red.setVisible(true);
            }
        }
    }

    /**
     * Loads the colors. This method is used when it's the client's turn to choose a color.
     */
    public void loadColor() {
        for(int i=0; i< colors.size(); i++){
            if(colors.get(i) == PlayerColor.YELLOW){
                setImageForColor(yellow, colors.get(i));
                yellow.setVisible(true);
            } else if(colors.get(i) == PlayerColor.BLUE){
                setImageForColor(blue, colors.get(i));
                blue.setVisible(true);
            } else if(colors.get(i) == PlayerColor.GREEN){
                setImageForColor(green, colors.get(i));
                green.setVisible(true);
            } else if(colors.get(i) == PlayerColor.RED){
                setImageForColor(red, colors.get(i));
                red.setVisible(true);
            }
        }
    }

    /**
     * Sets the image for a color in an enabled state.
     *
     * @param imageView The ImageView to set the image for.
     * @param c The color to set the image for.
     */
    private void setImageForColor(ImageView imageView, PlayerColor c) {
        try {
            Image colorImage = loadColorImage(c);

            imageView.setImage(colorImage);
            imageView.setFitWidth(125.0);  // Imposta la larghezza desiderata
            imageView.setFitHeight(187.5); // Imposta l'altezza desiderata
            imageView.setPreserveRatio(true); // Mantieni il rapporto d'aspetto
            enableColorInteractions();
            myLabel.setText("CHOOSE YOUR COLOR");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the image for a color in a disabled state.
     *
     * @param imageView The ImageView to set the image for.
     * @param c The color to set the image for.
     * @throws FileNotFoundException If an error occurs while loading the color images.
     */
    private void setImageDisabledForColor(ImageView imageView, PlayerColor c) throws FileNotFoundException {
        Image colorImage = loadColorImage(c);

        imageView.setImage(colorImage);
        imageView.setFitWidth(125.0);
        imageView.setFitHeight(187.5);
        imageView.setPreserveRatio(true); //
        disableColorInteractions();
        myLabel.setText("WAIT TO CHOOSE YOUR COLOR");
    }

    /**
     * Enables the client to choose a color.
     */
    public void chooseColor(){
        enableColorInteractions();
        myLabel.setText("CHOOSE YOUR COLOR");
    }

    /**
     * Enables interactions with the color images.
     */
    private void enableColorInteractions() {
        yellow.setDisable(false);
        blue.setDisable(false);
        green.setDisable(false);
        red.setDisable(false);
    }

    /**
     * Disables interactions with the color images.
     */
    private void disableColorInteractions() {
        yellow.setDisable(true);
        blue.setDisable(true);
        green.setDisable(true);
        red.setDisable(true);
    }

    /**
     * Disables the client's ability to choose a color and updates the label to indicate that it's not the client's turn.
     */
    public void waitYourTurn() {
        disableColorInteractions();
        myLabel.setText("WAIT, IT'S NOT YOUR TURN");
    }

    /**
     * Handles the mouse entered event for the color ImageView. If the color is not disabled, it reduces the opacity to 0.5.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onColorMouseEntered(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView color = (ImageView) event.getSource();
            if (!color.isDisabled()) {
                color.setOpacity(0.5); // Opacità ridotta per evidenziare
            }
        }
    }

    /**
     * Handles the mouse exited event for the color ImageView. It restores the opacity to 1.0.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onColorMouseExited(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView color = (ImageView) event.getSource();
            color.setOpacity(1.0); // Ripristina l'opacità originale
        }
    }

    /**
     * Handles the mouse clicked event for the color ImageView. If the color is not disabled, it sets the player's color to the
     * color of the clicked ImageView, reduces the opacity of the other colors to 0.5, and disables further color interactions.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onColorMouseClicked(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView color = (ImageView) event.getSource();
            if (!color.isDisabled()) {
                try {
                    if (color == yellow) {
                        client.endColor(PlayerColor.YELLOW);
                        gui.setPlayerColor(PlayerColor.YELLOW);
                        System.out.println("set yellow");
                        blue.setOpacity(0.5);
                        green.setOpacity(0.5);
                        red.setOpacity(0.5);
                    } else if (color == blue){
                        gui.setPlayerColor(PlayerColor.BLUE);
                        client.endColor(PlayerColor.BLUE);
                        System.out.println("set blue");
                        yellow.setOpacity(0.5);
                        green.setOpacity(0.5);
                        red.setOpacity(0.5);
                    } else if(color == green){
                        gui.setPlayerColor(PlayerColor.GREEN);
                        client.endColor(PlayerColor.GREEN);
                        System.out.println("set green");
                        yellow.setOpacity(0.5);
                        blue.setOpacity(0.5);
                        red.setOpacity(0.5);
                    } else {
                        client.endColor(PlayerColor.RED);
                        gui.setPlayerColor(PlayerColor.RED);
                        System.out.println("set red");
                        yellow.setOpacity(0.5);
                        blue.setOpacity(0.5);
                        green.setOpacity(0.5);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        colorchosen = true;
        disableColorInteractions();
        myLabel.setText("COLOOR CHOSEN!");
    }

    /**
     * Loads the colors for the color selection feature. This method is called with a list of colors as an argument.
     *
     * @param colors The list of colors to load.
     */
    public void loadThings(LinkedList<PlayerColor> colors){
        this.colors = colors;
        loadColor();
    }

}

