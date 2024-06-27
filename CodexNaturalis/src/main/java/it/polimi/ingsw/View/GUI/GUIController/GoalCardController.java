package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Controller for the GoalCard feature in the GUI. This class handles the loading of GoalCards, enabling and disabling interactions with the GoalCards,
 * and handling mouse events for the GoalCards.
 */
public class GoalCardController extends GUIController{

    @FXML
    private Pane myPane;
    @FXML
    private Label myLabel;
    @FXML
    private ImageView leftGoalCard;
    @FXML
    private ImageView rightGoalCard;
    /**
     * A boolean indicating whether a goal card has been chosen by the player.
     */
    protected boolean goalcardchosen;
    /**
     * A list of GoalCard objects representing the goal cards available for the player.
     */
    private LinkedList<GoalCard> goals;

    /**
     * Sets the arguments for this controller. In this case, it sets the list of GoalCards and loads them.
     *
     * @param args The arguments to set.
     */
    @Override
    public void setArgs(Object... args) {
        this.goals = (LinkedList<GoalCard>) args[0];
        loadGoalCard();
    }
    /**
     * Initializes the controller by setting goalcardchosen to false and making the label visible.
     */
    @FXML
    private void initialize() {
        goalcardchosen=false;
        myLabel.setVisible(true);
    }

    /**
     * Loads the GoalCards by setting the image for each GoalCard ImageView.
     */
    public void loadGoalCard() {
        setImageForGoalCard(leftGoalCard, goals.get(0));
        setImageForGoalCard(rightGoalCard, goals.get(1));
        leftGoalCard.setVisible(true);
        rightGoalCard.setVisible(true);
    }

    /**
     * Sets the image for a GoalCard.
     *
     * @param imageView The ImageView to set the image for.
     * @param goal The GoalCard to get the image from.
     */
    private void setImageForGoalCard(ImageView imageView, GoalCard goal) {
        try {
            int cardId = goal.getId();
            Image cardImage = loadCardFrontImage(cardId);

            imageView.setImage(cardImage);
            imageView.setFitWidth(250.0);  // Imposta la larghezza desiderata
            imageView.setFitHeight(187.5); // Imposta l'altezza desiderata
            imageView.setPreserveRatio(true); // Mantieni il rapporto d'aspetto

            enableCardInteractions();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Enables the client to choose a GoalCard and updates the label to prompt the client to choose a GoalCard.
     */
    public void chooseGoalCard() {
        enableCardInteractions();
        myLabel.setText("CHOOSE YOUR PERSONAL GOALCARD");
    }

    /**
     * Enables interactions with the GoalCard ImageViews.
     */
    private void enableCardInteractions() {
        leftGoalCard.setDisable(false);
        rightGoalCard.setDisable(false);
    }

    /**
     * Disables interactions with the GoalCard ImageViews.
     */
    private void disableCardInteractions() {
        leftGoalCard.setDisable(true);
        rightGoalCard.setDisable(true);
    }

    /**
     * Disables the client's ability to choose a GoalCard and updates the label to indicate that it's not the client's turn.
     */
    public void waitYourTurn() {
        disableCardInteractions();
        if (!goalcardchosen){
            myLabel.setText("WAIT, IT'S NOT YOUR TURN");
        }
    }
    /**
     * Handles the mouse entered event for the GoalCard ImageView. If the GoalCard is not disabled, it reduces the opacity to 0.5.
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
     * Handles the mouse exited event for the GoalCard ImageView. It restores the opacity to 1.0.
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
     * Handles the mouse clicked event for the GoalCard ImageView. If the GoalCard is not disabled, it sets the client's GoalCard to the
     * GoalCard of the clicked ImageView, reduces the opacity of the other GoalCard to 0.5, and disables further GoalCard interactions.
     *
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    private void onCardMouseClicked(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView card = (ImageView) event.getSource();
            if (!card.isDisabled()) {
                boolean isLeftCard = card == leftGoalCard;
                try {
                    if (isLeftCard) {
                        client.chooseGoalCard(1, client);
                        System.out.println("settata carta goal sinistra");
                        rightGoalCard.setOpacity(0.5);
                    } else {
                        client.chooseGoalCard(2, client);
                        System.out.println("settata carta goal destra");
                        leftGoalCard.setOpacity(0.5);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        goalcardchosen = true;
        disableCardInteractions();
        myLabel.setText("GOALCARD CHOSEN!");
        try {
            this.gui.endTurn("GoalCard");
        } catch (RemoteException ignored) {}
    }
}
