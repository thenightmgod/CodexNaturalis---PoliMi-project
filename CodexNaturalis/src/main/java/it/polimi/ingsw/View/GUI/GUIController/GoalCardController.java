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
    private AnchorPane myPane;
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

    private void setCardInteraction(ImageView imageView, GoalCard goal) {
        imageView.setOnMouseEntered(event -> {
            imageView.setOpacity(0.7);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setOpacity(1.0);
        });

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
    private void disableCardInteractions(ImageView imageView) {
        imageView.setOnMouseClicked(null); // Rimuove l'azione onClick
        imageView.setOpacity(0.5); // Opacità ridotta per indicare che le interazioni sono disabilitate
    }

    public void showGoalCardscene() {
        goalcardchosen=true;

        leftGoalCard.setVisible(true);
        rightGoalCard.setVisible(true);

        leftGoalCard.setDisable(false);
        rightGoalCard.setDisable(false);
        myLabel.setText("Choose you personal goal card");
    }

    public void showWaitingScene() {
        if(goalcardchosen) {
            myLabel.setText("Well done, goal card chosen!");
        } else {
            myLabel.setText("Wait your turn");
        }
        disableCardInteractions(leftGoalCard);
        disableCardInteractions(rightGoalCard);
        leftGoalCard.setVisible(true);
        rightGoalCard.setVisible(true);
        myLabel.setVisible(true);
    }

    public void setClient(CommonClient client) {
        this.client = client;
    }*/
}
