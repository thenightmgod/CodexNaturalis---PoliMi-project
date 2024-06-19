package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.LinkedList;

public class GoalCardController extends GUIController{

    protected boolean goalcardchosen;
    @FXML
    private Pane myPane;
    @FXML
    private Label myLabel;
    @FXML
    private ImageView leftGoalCard;
    @FXML
    private ImageView rightGoalCard;
    private LinkedList<GoalCard> goals;

    @Override
    public void setArgs(Object... args) {
        this.goals = (LinkedList<GoalCard>) args[0];
    }

    @FXML
    private void initialize() {
        goalcardchosen=false;
        myLabel.setVisible(true);
        loadGoalCard();
    }

    public void loadGoalCard() {
        //carico le goal card nella scena
    }


    public void chooseGoalCard() {
        enableCardInteractions();
        myLabel.setText("Now choose your personal goal card!");
    }

    private void enableCardInteractions() {
        leftGoalCard.setDisable(false);
        rightGoalCard.setDisable(false);
    }

    private void disableCardInteractions() {
        leftGoalCard.setOnMouseClicked(null); // Rimuove l'azione onClick
        rightGoalCard.setOnMouseClicked(null);
    }

    public void waitYourTurn() {
        disableCardInteractions();
        if (!goalcardchosen){
            myLabel.setText("Wait for your turn to choose goal card!");
        }else {
            myLabel.setText("Well done, goal card chosen!");
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
    private void onCardMouseClicked(MouseEvent event) throws RemoteException {
        if (event.getSource() instanceof ImageView) {
            ImageView card = (ImageView) event.getSource();
            if (!card.isDisabled()) {
                boolean isLeftCard = card == leftGoalCard;
                try {
                    if (isLeftCard) {
                        client.chooseGoalCard(1, client);
                        System.out.println("settata carta goal sinistra");
                    } else {
                        client.chooseGoalCard(2, client);
                        System.out.println("settata carta goal destra");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        goalcardchosen = true;
        this.gui.endTurn("GoalCard");
    }






















    /*

    public void runGoal(LinkedList <GoalCard> goals) {
         Platform.runLater(() -> {
             myPane.setVisible(true);
             this.goals=goals;
             myLabel=new Label();
             leftGoalCard = new ImageView();
             rightGoalCard= new ImageView();

            if (goals.size() >= 2) {
                GoalCard goal1 = goals.get(0);
                GoalCard goal2 = goals.get(1);

                setImageForGoalCard(leftGoalCard, goal1);
                setImageForGoalCard(rightGoalCard, goal2);
            }
             myLabel.setVisible(true);
             leftGoalCard.setVisible(true);
             rightGoalCard.setVisible(true);

             disableCardInteractions(leftGoalCard);
             disableCardInteractions(rightGoalCard);
        });

    }

    private void setImageForGoalCard(ImageView imageView, GoalCard goal) {
        try {
            int cardId = goal.getId();
            Image cardImage = loadCardFrontImage(cardId);

            imageView.setImage(cardImage);
            imageView.setFitWidth(200);  // Imposta la larghezza desiderata
            imageView.setFitHeight(160); // Imposta l'altezza desiderata
            imageView.setPreserveRatio(true); // Mantieni il rapporto d'aspetto

            setCardInteraction(imageView, goal);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


        imageView.setOnMouseClicked(event -> {
            boolean isLeftCard = imageView == leftGoalCard;

            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.close();
            try {
                // Invia l'indice corretto a seconda se è l'immagine sinistra (1) o destra (2)
                if (isLeftCard) {
                    client.chooseGoalCard(1, client);
                    System.out.println("settata carta goal sinistra");
                } else {
                    client.chooseGoalCard(2, client);
                    System.out.println("settata carta goal destra");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }



    */
}
