package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
/**
 * Controller for the StartCard feature in the GUI. This class handles the loading of StartCards, enabling and disabling interactions with the StartCards,
 * and handling mouse events for the StartCards.
 */
public class StartCardController extends GUIController{

    @FXML
    private ImageView backgroundImage;
    @FXML
    private Label welcomeLabel;
    @FXML
    private AnchorPane neutralBackgroundPane;
    @FXML
    private Label chooseSideLabel;
    @FXML
    private ImageView leftStartCardImage;
    @FXML
    private ImageView rightStartCardImage;
    /**
     * A boolean indicating whether the start card has been confirmed by the player.
     */
    private boolean StartCardOk=false;
    /**
     * The start card loaded.
     */
    private StartCard card;
    /**
     * Sets the arguments for this controller. In this case, it sets the StartCard and loads it.
     *
     * @param args The arguments to set.
     */
    @Override
    public void setArgs(Object... args) throws FileNotFoundException {
        this.card = (StartCard) args[0];
        showStartCard(card);
    }
    /**
     * Initializes the controller by loading the background image and setting the welcome label.
     */
    @FXML
    private void initialize() {
        String newImagePath = "/view/MyCodexNaturalisPhotos/pic8211904.jpg";
        Image newImage = loadImage(newImagePath);
        if (newImage != null) {
            backgroundImage.setImage(newImage);
            backgroundImage.setEffect(new GaussianBlur(10));

        } else {
            System.out.println("Failed to load image from path: " + newImagePath);
        }
        welcomeLabel.setText("WELCOME TO THE GAME...");
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> showNeutralBackground());
        pause.play();
    }
    /**
     * Shows the neutral background and the choose side label.
     */
    @FXML
    public void showNeutralBackground() {
        welcomeLabel.setVisible(false);
        backgroundImage.setVisible(false);
        neutralBackgroundPane.setVisible(true);
        chooseSideLabel.setVisible(true);
    }
    /**
     * Loads the StartCard , front and back.
     *
     * @param card The StartCard to load.
     */
    public void showStartCard(StartCard card) throws FileNotFoundException {

        int cardId = card.getId(); // Ottieni l'ID della carta
        Image leftCardImage = loadCardFrontImage(cardId);
        Image rightCardImage = loadCardBackImage(cardId);

        leftStartCardImage.setImage(leftCardImage);
        rightStartCardImage.setImage(rightCardImage);
        chooseSideLabel.setVisible(true);
        leftStartCardImage.setVisible(true);
        rightStartCardImage.setVisible(true);
    }
    /**
     * Enables the client to choose a StartCard and updates the label to prompt the client to choose a side.
     */
    public void chooseStartCard() {
        enableCardInteractions();
        chooseSideLabel.setText("CHOOSE THE SIDE");
    }
    /**
     * Handles the mouse entered event for the StartCard ImageView. If the StartCard is not disabled, it reduces the opacity to 0.5.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onCardMouseEntered(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView card = (ImageView) event.getSource();
            if (!card.isDisabled()) {
                card.setOpacity(0.5); // Opacità ridotta per evidenziare
            }
        }
    }
    /**
     * Handles the mouse exited event for the StartCard ImageView. It restores the opacity to 1.0.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onCardMouseExited(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView card = (ImageView) event.getSource();
            card.setOpacity(1.0); // Ripristina l'opacità originale
        }
    }
    /**
     * Handles the mouse clicked event for the StartCard ImageView. If the StartCard is not disabled, it sets the client's StartCard to the
     * StartCard of the clicked ImageView, reduces the opacity of the other StartCard to 0.5, and disables further StartCard interactions.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onCardMouseClicked(MouseEvent event) throws RemoteException {
            if (event.getSource() instanceof ImageView) {
                ImageView card = (ImageView) event.getSource();
                if (!card.isDisabled()) {
                    if (card == leftStartCardImage) {
                        boolean face = true;
                        client.setStartCardFace(face, client);
                        rightStartCardImage.setOpacity(0.5);
                    } else if (card == rightStartCardImage) {
                        boolean face = false;
                        client.setStartCardFace(face, client);
                        leftStartCardImage.setOpacity(0.5);
                    }
                }
            }
            StartCardOk = true;
            this.gui.endTurn("StartCard");
    }
    /**
     * Disables the client's ability to choose a StartCard and updates the label to indicate that it's not the client's turn or that the StartCard has been chosen.
     */
    public void waitYourTurn() {
        if (!StartCardOk) {
            chooseSideLabel.setText("WAIT, IT'S NOT YOUR TURN");
            disableCardInteractions();
        } else {
            chooseSideLabel.setText("STARTCARD CHOSEN!");
            disableCardInteractions();
        }
    }
    /**
     * Enables interactions with the StartCard ImageViews.
     */
    private void enableCardInteractions() {
        leftStartCardImage.setDisable(false);
        rightStartCardImage.setDisable(false);
    }
    /**
     * Disables interactions with the StartCard ImageViews.
     */
    private void disableCardInteractions() {
        leftStartCardImage.setDisable(true);
        rightStartCardImage.setDisable(true);
    }
}
