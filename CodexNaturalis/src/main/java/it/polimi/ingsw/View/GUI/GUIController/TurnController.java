package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

public class TurnController extends GUIController{
    @FXML
    private HBox myHandBox;
    @FXML
    private HBox resourceBox;
    @FXML
    private HBox goldBox;
    @FXML
    private HBox goalsBox;
    private LinkedList<GoalCard> commongoals;
    private LinkedList<GoldCard> golddeck;
    private LinkedList<ResourceCard> resourcedeck = new LinkedList<>();
    private LinkedList<PlayableCard> myhand;
    private PlayingField field;
    boolean isFrontImageLoaded=true;


    @Override
    public void setArgs(Object... args) {
        System.out.println("setArgs");
        resourcedeck=(LinkedList<ResourceCard>) args[0];
        golddeck= (LinkedList<GoldCard>) args[1];
        commongoals= (LinkedList<GoalCard>) args[2];
        myhand=(LinkedList<PlayableCard>) args[3];
        field=(PlayingField) args[4];
        loadAllArgs();
    }

    private void loadAllArgs() {
        loadResourceBox();
        loadGoldBox();
        loadGoalBox();

        System.out.println("loadAllArgs");
    }

    private void loadResourceBox() {
        resourceBox.setPrefHeight(160.0);
        resourceBox.setPrefWidth(492.0);
        double imageViewWidth = (resourceBox.getPrefWidth()-45) / 3.0;
        try {
            for (int i =resourcedeck.size()-1 ; i >=0 ; i--) {
                Image image;
                if (i < 2) {
                    image = loadCardFrontImage(resourcedeck.get(i).getId());
                } else {
                    image = loadCardBackImage(resourcedeck.get(i).getId());
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageViewWidth);
                imageView.setFitHeight(resourceBox.getPrefHeight());
                imageView.setPreserveRatio(true);
                resourceBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadGoldBox() {
        goldBox.setPrefHeight(160.0);
        goldBox.setPrefWidth(492.0);
        double imageViewWidth = (goldBox.getPrefWidth()-45) / 3.0;

        try {
            for (int i =golddeck.size()-1 ; i >=0 ; i--) {
                Image image;
                if (i < 2) {
                    image = loadCardFrontImage(golddeck.get(i).getId());
                } else {
                    image = loadCardBackImage(golddeck.get(i).getId());
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageViewWidth);
                imageView.setFitHeight(goldBox.getPrefHeight());
                imageView.setPreserveRatio(true);
                goldBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadGoalBox() {
        goalsBox.setPrefHeight(160.0);
        goalsBox.setPrefWidth(492.0);
        double imageViewWidth = (goalsBox.getPrefWidth()-45) / 3.0;
        try {
            for (int i = commongoals.size()-1 ; i >=0 ; i--) {
                Image image;
                if (i < 2) {
                    image = loadCardFrontImage(commongoals.get(i).getId());
                } else {
                    image = loadCardBackImage(commongoals.get(i).getId());
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageViewWidth);
                imageView.setFitHeight(goalsBox.getPrefHeight());
                imageView.setPreserveRatio(true);
                goalsBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //interazioni per myhand
    private void setBasicCardInteraction(ImageView imageView, int cardId) {
        imageView.setOnMouseEntered(event -> {
            imageView.setOpacity(0.7);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setOpacity(1.0);
        });
        imageView.setOnMouseClicked(event -> {
            try {
                if (isFrontImageLoaded) {
                    Image flippedImage = loadCardBackImage(cardId);
                    imageView.setImage(flippedImage);
                    isFrontImageLoaded = false;
                } else {
                    Image frontImage = loadCardFrontImage(cardId);
                    imageView.setImage(frontImage);
                    isFrontImageLoaded = true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
