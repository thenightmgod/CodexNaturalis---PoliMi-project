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

    private boolean StartCardOk=false;
    private StartCard card;

    @Override
    public void setArgs(Object... args) throws FileNotFoundException {
        this.card = (StartCard) args[0];
        showStartCard(card);
    }

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

    @FXML
    public void showNeutralBackground() {
        welcomeLabel.setVisible(false);
        backgroundImage.setVisible(false);
        neutralBackgroundPane.setVisible(true);
        chooseSideLabel.setVisible(true);
    }

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

    public void chooseStartCard() {
        enableCardInteractions();
        chooseSideLabel.setText("CHOOSE THE SIDE");
    }
    @FXML
    private void onCardMouseEntered(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView card = (ImageView) event.getSource();
            if (!card.isDisabled()) {
                card.setOpacity(0.5); // Opacità ridotta per evidenziare
            }
        }
    }

    @FXML
    private void onCardMouseExited(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView card = (ImageView) event.getSource();
            card.setOpacity(1.0); // Ripristina l'opacità originale
        }
    }

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

    public void waitYourTurn() {
        if (!StartCardOk) {
            chooseSideLabel.setText("WAIT, IT'S NOT YOUR TURN");
            disableCardInteractions();
        } else {
            chooseSideLabel.setText("STARTCARD CHOSEN!");
            disableCardInteractions();
        }
    }

    private void enableCardInteractions() {
        leftStartCardImage.setDisable(false);
        rightStartCardImage.setDisable(false);
    }

    private void disableCardInteractions() {
        leftStartCardImage.setDisable(true);
        rightStartCardImage.setDisable(true);
    }
}
