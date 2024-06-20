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

public class GoalCardController extends GUIController{

    @FXML
    private Pane myPane;
    @FXML
    private Label myLabel;
    @FXML
    private ImageView leftGoalCard;
    @FXML
    private ImageView rightGoalCard;
    protected boolean goalcardchosen;
    private LinkedList<GoalCard> goals;

    @Override
    public void setArgs(Object... args) {
        this.goals = (LinkedList<GoalCard>) args[0];
        loadGoalCard();
    }
    @FXML
    private void initialize() {
        goalcardchosen=false;
        myLabel.setVisible(true);
    }

    public void loadGoalCard() {
        setImageForGoalCard(leftGoalCard, goals.get(0));
        setImageForGoalCard(rightGoalCard, goals.get(1));
        leftGoalCard.setVisible(true);
        rightGoalCard.setVisible(true);
    }

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

    public void chooseGoalCard() {
        enableCardInteractions();
        myLabel.setText("CHOOSE YOUR PERSONAL GOALCARD");
    }

    private void enableCardInteractions() {
        leftGoalCard.setDisable(false);
        rightGoalCard.setDisable(false);
    }

    private void disableCardInteractions() {
        leftGoalCard.setDisable(true);
        rightGoalCard.setDisable(true);
    }

    public void waitYourTurn() {
        disableCardInteractions();
        if (!goalcardchosen){
            myLabel.setText("WAIT, IT'S NOT YOUR TURN");
        }
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
