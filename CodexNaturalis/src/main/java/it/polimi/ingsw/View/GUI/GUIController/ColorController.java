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
    protected boolean colorchosen;
    private LinkedList<GoalCard> goals;

    @Override
    public void setArgs(Object... args) {
        this.goals = (LinkedList<GoalCard>) args[0];
        loadColor();
    }
    @FXML
    private void initialize() {
        colorchosen=false;
        myLabel.setVisible(true);
    }

    public void loadColor() {
        setImageForGoalCard(yellow, goals.get(0));
        setImageForGoalCard(blue, goals.get(1));
        setImageForGoalCard(green, goals.get(0));
        setImageForGoalCard(red, goals.get(1));
        yellow.setVisible(true);
        blue.setVisible(true);
        green.setVisible(true);
        red.setVisible(true);
    }

    private void setImageForGoalCard(ImageView imageView, P) {
        try {
            int cardId = goal.getId();
            Image colorImage = loadColorImage(ca);

            imageView.setImage(colorImage);
            imageView.setFitWidth(125.0);  // Imposta la larghezza desiderata
            imageView.setFitHeight(187.5); // Imposta l'altezza desiderata
            imageView.setPreserveRatio(true); // Mantieni il rapporto d'aspetto

            enableColorInteractions();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void chooseGoalCard() {
        enableColorInteractions();
        myLabel.setText("CHOOSE YOUR COLOR");
    }

    private void enableColorInteractions() {
        yellow.setDisable(false);
        blue.setDisable(false);
        green.setDisable(false);
        red.setDisable(false);
    }

    private void disableColorInteractions() {
        yellow.setDisable(true);
        blue.setDisable(true);
        green.setDisable(true);
        red.setDisable(true);
    }

    public void waitYourTurn() {
        disableColorInteractions();
        if (!colorchosen){
            myLabel.setText("WAIT, IT'S NOT YOUR TURN");
        }
    }

    @FXML
    private void onColorMouseEntered(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView color = (ImageView) event.getSource();
            if (!color.isDisabled()) {
                color.setOpacity(0.5); // Opacità ridotta per evidenziare
            }
        }
    }
    @FXML
    private void onColorMouseExited(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView color = (ImageView) event.getSource();
            color.setOpacity(1.0); // Ripristina l'opacità originale
        }
    }
    @FXML
    private void onColorMouseClicked(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView color = (ImageView) event.getSource();
            if (!color.isDisabled()) {
                try {
                    if (color == yellow) {
                        client.endColor(PlayerColor.YELLOW);
                        System.out.println("set yellow");
                        blue.setOpacity(0.5);
                        green.setOpacity(0.5);
                        red.setOpacity(0.5);
                    } else if (color == blue){
                        client.endColor(PlayerColor.BLUE);
                        System.out.println("set blue");
                        yellow.setOpacity(0.5);
                        green.setOpacity(0.5);
                        red.setOpacity(0.5);
                    } else if(color == green){
                        client.endColor(PlayerColor.GREEN);
                        System.out.println("set green");
                        yellow.setOpacity(0.5);
                        blue.setOpacity(0.5);
                        red.setOpacity(0.5);
                    } else {
                        client.endColor(PlayerColor.RED);
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
        try {
            this.gui.endTurn("Goalard");
        } catch (RemoteException ignored) {}
    }
}

