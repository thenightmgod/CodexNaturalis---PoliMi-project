package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.View.GUI.GUIController.ScoreBoard.ScoreBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class is a controller for the game turn in the GUI.
 * It extends the GUIController class and manages the game turn logic.
 */
public class TurnController extends GUIController{
    /**
     * The HBox that contains the cards in the user's hand.
     */
    @FXML
    private HBox myHandBox;
    /**
     * The HBox that contains the resource cards.
     */
    @FXML
    private HBox resourceBox;
    /**
     * The HBox that contains the gold cards.
     */
    @FXML
    private HBox goldBox;
    /**
     * The HBox that contains the goal cards.
     */
    @FXML
    private HBox goalsBox;
    /**
     * The label that displays messages to the user.
     */
    @FXML
    private Label messageLabel;
    /**
     * The label that displays the drawable resource cards.
     */
    @FXML
    private Label myResource;
    /**
     * The label that displays the drawable gold cards.
     */
    @FXML
    private Label myGold;
    /**
     * The label that displays the user's goal cards.
     */
    @FXML
    private Label myGoal;
    /**
     * The label that displays secondary messages to the user.
     */
    @FXML
    private Label secondmessageLabel;
    @FXML
    private Button myPointsButton;
    /**
     * The GridPane that represents the game board.
     */
    @FXML
    private GridPane marione;
    /**
     * The AnchorPane that contains the game board.
     */
    private AnchorPane anchorPane;
    /**
     * A LinkedList of GoalCards representing the common goals.
     */
    private LinkedList<GoalCard> commongoals;
    /**
     * A LinkedList of GoldCards representing the deck of gold cards.
     */
    private LinkedList<GoldCard> golddeck;
    /**
     * A LinkedList of ResourceCards representing the deck of resource cards.
     */
    private LinkedList<ResourceCard> resourcedeck = new LinkedList<>();
    /**
     * A LinkedList of PlayableCards representing the player's hand.
     */
    private LinkedList<PlayableCard> myhand;
    /**
     * A LinkedHashMap mapping player names to their points.
     */
    private LinkedHashMap<String,Integer> points = new LinkedHashMap<>();
    /**
     * A LinkedHashMap mapping player names to their playing fields.
     */
    private LinkedHashMap<String, PlayingField> othersPlayingField;
    /**
     * The ScoreBoard object representing the game's score board.
     */
    private ScoreBoard scoreBoard;
    /**
     * The PlayingField object representing the player's playing field.
     */
    private PlayingField field;
    /**
     * A boolean indicating whether it's the player's turn.
     */
    boolean myTurn;
    /**
     * A boolean indicating whether the front image of a card is loaded.
     */
    boolean isFrontImageLoaded=true;
    /**
     * A boolean indicating whether a card has been revealed.
     */
    boolean revealed;
    /**
     * A boolean indicating whether the points have been updated.
     */
    boolean updatedPoints=false;
    /**
     * A boolean indicating whether it's the last round of the game.
     */
    boolean lastRound=false;
    /**
     * A boolean used for game logic, its purpose is handling the last round.
     */
    boolean mirko = true;
    /**
     * A LinkedList of Booleans used for game logic, its purpose is handling the flip of the cards in the hand.
     */
    LinkedList<Boolean> omar = new LinkedList<>();

    /**
     * Sets the arguments for the controller.
     * In this case, it sets the resource deck, gold deck, common goals, my hand, field, and my turn.
     * Then it calls the method to load all arguments.
     * @param args The arguments to be set.
     */
    @Override
    public void setArgs(Object... args) {
        resourcedeck=(LinkedList<ResourceCard>) args[0];
        golddeck= (LinkedList<GoldCard>) args[1];
        commongoals= (LinkedList<GoalCard>) args[2];
        myhand=(LinkedList<PlayableCard>) args[3];
        field=(PlayingField) args[4];
        myTurn=(boolean)args[5];
        loadAllArgs();
    }

    /**
     * Loads all arguments and initializes the game field.
     */
    private void loadAllArgs() {
        for(int i=0; i<3; i++){
            this.omar.add(i, true);
        }
        loadResourceBox();
        loadGoldBox();
        loadGoalBox();
        loadMyHand();
        plotField();
        if(myTurn) {
            isYourTurn();
        }else {
            waitMyTurn();
        }
    }

    /**
     * Plots the game field with the current state of the game.
     */
    public void plotField() {
        PlayingField field = this.gui.getClient().getClient().getField();
        this.marione.getChildren().clear();
        this.marione.getRowConstraints().clear();
        this.marione.getColumnConstraints().clear();
        LinkedList<Position> frees = field.getFreePositions();

        int fmaxX = frees.stream().mapToInt(Position::getX).max().orElse(400);
        int fminX = frees.stream().mapToInt(Position::getX).min().orElse(400);
        int fmaxY = frees.stream().mapToInt(Position::getY).max().orElse(400);
        int fminY = frees.stream().mapToInt(Position::getY).min().orElse(400);

        int maxX = field.getField().keySet().stream().mapToInt(Position::getX).max().orElse(400);
        int minX = field.getField().keySet().stream().mapToInt(Position::getX).min().orElse(400);
        int maxY = field.getField().keySet().stream().mapToInt(Position::getY).max().orElse(400);
        int minY = field.getField().keySet().stream().mapToInt(Position::getY).min().orElse(400);

        if(maxX < fmaxX) maxX = fmaxX;
        if(minX > fminX) minX = fminX;
        if(maxY < fmaxY) maxY = fmaxY;
        if(minY > fminY) minY = fminY;

        double cellWidth = 210;
        double cellHeight = 140.0;

        this.marione.setVgap(0);
        this.marione.setHgap(0);

        for (int j = 0; j < maxY - minY + 1; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(cellHeight);
            rowConstraints.setMaxHeight(cellHeight);
            this.marione.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < maxX - minX + 1; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(cellWidth);
            columnConstraints.setMaxWidth(cellWidth);
            this.marione.getColumnConstraints().add(columnConstraints);
        }

        for (Position prato : frees){
            ImageView imageView = new ImageView();
            int x = prato.getX() - minX;
            int y = maxY - prato.getY();
            Image image = loadImage("/view/MyCodexNaturalisPhotos/freepos.png");
            imageView.setImage(image);
            imageView.setFitWidth(cellWidth); // Set the preferred width of the ImageView
            imageView.setFitHeight(cellHeight);
            GridPane.setMargin(imageView, new javafx.geometry.Insets(0, 0, 111 * y, -45.94 * x));
            imageView.setOpacity(0.4);
            imageView.setPreserveRatio(true);
            this.marione.add(imageView, x, y);

            imageView.setOnDragOver(event -> {
                if (event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });



            imageView.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if(db.hasString()) {
                    try {
                        int cardIndex = Integer.parseInt(db.getString());
                        this.gui.setFirst_turn(false);
                        FB face = FB.FRONT;
                        if(!omar.get(cardIndex - 1)){
                            face = FB.BACK;
                        }
                        client.placeCard(client, cardIndex, prato.getX() , prato.getY(), face);
                        success = true;
                    } catch (RemoteException e) {
                        System.out.println("Error in place card");
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            });
        }

        for (Position p : field.getField().keySet()) {
            PlayableCard card = field.getField().get(p);
            ImageView imageView = new ImageView();
            try {
                int x = p.getX() - minX;
                int y = maxY - p.getY();
                if(p.getFace()==FB.BACK) {
                    Image image = loadCardBackImage(card.getId());
                    imageView.setImage(image);
                } else {Image image = loadCardFrontImage(card.getId());
                    imageView.setImage(image);
                }
                imageView.setFitWidth(cellWidth);
                imageView.setFitHeight(cellHeight);
                GridPane.setMargin(imageView, new javafx.geometry.Insets(0, 0, 111 * y, -45.94 * x));
                imageView.setPreserveRatio(true);
                this.marione.add(imageView, x, y);
            } catch (FileNotFoundException e) {
                System.out.println("errore nel print playing field");
            }
        }
    }

    /**
     * Updates the resource deck and reloads the resource box.
     * @param resourcedeck The new resource deck.
     */
    public void updateResourceDeck(LinkedList<ResourceCard> resourcedeck) {
        this.resourcedeck = resourcedeck;
        loadResourceBox();
    }

    /**
     * Updates the gold deck and reloads the gold box.
     * @param golddeck The new gold deck.
     */
    public void updateGoldDeck(LinkedList<GoldCard> golddeck){
        this.golddeck = golddeck;
        loadGoldBox();
    }

    /**
     * Handles the actions to be performed when it's the user's turn.
     */
    public void isYourTurn() {
        messageLabel.setText("IT'S YOUR TURN!");
        messageLabel.setVisible(true);
        loadMyHand();
        for(int cardIndex = 0; cardIndex < this.gui.getClient().getClient().getHand().size(); cardIndex++) {
            PlayableCard card = this.gui.getClient().getClient().getHand().get(cardIndex);
            ImageView imageView = (ImageView) myHandBox.getChildren().get(cardIndex);
            if (imageView.getImage().getUrl().contains(String.valueOf(card.getId()))) {
                int finalCardIndex = cardIndex;
                imageView.setOnDragDetected(event -> {
                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(String.valueOf(finalCardIndex + 1)); // +1 because cardIndex starts from 0
                    db.setContent(content);
                    db.setDragView(imageView.getImage());
                    event.consume();
                });
            }
        }
        if(lastRound) {
            secondmessageLabel.setText("THIS IS THE LAST ROUND!");
            secondmessageLabel.setVisible(true);
        }
    }

    /**
     * Handles the action of drawing a card.
     * @throws RemoteException If there's an error in the remote method invocation.
     */
    public void drawCard() throws RemoteException {
        messageLabel.setText("Now, draw a card!");
        messageLabel.setVisible(true);
        if(mirko){
            for (int i = 0; i < resourceBox.getChildren().size(); i++) {
                ImageView imageView = (ImageView) resourceBox.getChildren().get(i);
                int indexCard= getIndexInHBox(imageView, 1);
                addDrawEffect(imageView,1, indexCard);
            }
            for (int i = 0; i < goldBox.getChildren().size(); i++) {
                ImageView imageView = (ImageView) goldBox.getChildren().get(i);
                int indexCard= getIndexInHBox(imageView, 2);
                addDrawEffect(imageView, 2, indexCard);
            }
        } else{
            this.gui.endTurn("NormalTurn");
        }
    }

    /**
     * Adds a draw effect to the specified image.
     * @param image The image to add the effect to.
     * @param deck The deck the image belongs to.
     * @param index The index of the image in the deck.
     */
    private void addDrawEffect(ImageView image, int deck, int index) {
        image.setOnMouseClicked(event -> {
            removeDrawEffect();
            try {
                client.drawCard(deck, index, this.client);
            } catch (RemoteException e) {
                System.out.println("Errore nel draw card");
            }
            messageLabel.setText("Well done, now wait your turn.");
        });
    }

    /**
     * Gets the index of the specified image in the HBox.
     * @param image The image to get the index of.
     * @param deck The deck the image belongs to.
     * @return The index of the image in the HBox.
     */
    private int getIndexInHBox(ImageView image, int deck) {
        if (deck==1) {
            int index = resourceBox.getChildren().indexOf(image);
            int numChildren = resourceBox.getChildren().size();

            if (index >= 0) {
                if (index == numChildren - 1) {
                    return 1; //ultima2 immagine a dx
                } else if (index == numChildren - 2) {
                    return 2; //immagine in mezzo
                } else if (index == numChildren - 3) {
                    return 3; //immagine a sx
                }
            }
        } else {
            int index = goldBox.getChildren().indexOf(image);
            int numChildren = goldBox.getChildren().size();

            if (index >= 0) {
                if (index == numChildren - 1) { //ultima immagine a dx
                    return 1;
                } else if (index == numChildren - 2) {
                    return 2; //seconda immagine centrale
                } else if (index == numChildren - 3) {
                    return 3; //prima immagine da sx
                }
            }
        }
        return 0;
    }

    /**
     * Removes the draw effect from all images in the resource box and gold box.
     */
    private void removeDrawEffect() {
        for (int i = 0; i < resourceBox.getChildren().size(); i++) {
            ImageView imageView = (ImageView) resourceBox.getChildren().get(i);
            imageView.setOnMouseClicked(null);
        }
        for (int i = 0; i < goldBox.getChildren().size(); i++) {
            ImageView imageView = (ImageView) goldBox.getChildren().get(i);
            imageView.setOnMouseClicked(null);
        }
    }

    /**
     * Handles the actions to be performed when it's not the user's turn.
     */
    public void waitMyTurn() {
        messageLabel.setText("Please, wait your turn to place a card");
        messageLabel.setVisible(true);
        if (lastRound) {
            secondmessageLabel.setText("THIS IS THE LAST ROUND!");
            secondmessageLabel.setVisible(true);
        }
    }

    /**
     * Loads the resource box with images of the resource cards.
     * The images are loaded from the resource deck.
     * The first two cards are shown with their front image, the rest with their back image.
     */
    private void loadResourceBox() {

        resourceBox.getChildren().clear();
        resourceBox.setPrefHeight(160.0);
        resourceBox.setPrefWidth(492.0);
        myResource.setText("DRAWABLE RESOURCECARDS");
        myResource.setVisible(true);
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
                addClickEffect(imageView);
                resourceBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the gold box with images of the gold cards.
     * The images are loaded from the gold deck.
     * The first two cards are shown with their front image, the rest with their back image.
     */
    private void loadGoldBox() {

        goldBox.getChildren().clear();
        goldBox.setPrefHeight(160.0);
        goldBox.setPrefWidth(492.0);
        myGold.setText("DRAWABLE GOLDCARDS");
        myGold.setVisible(true);
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
                addClickEffect(imageView);
                goldBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the goal box with images of the goal cards.
     * The images are loaded from the common goals.
     * The first two cards are shown with their front image, the rest with their back image.
     * The last card in the common goals has a special click effect to reveal or hide the secret goal card.
     */
    private void loadGoalBox() {
        goalsBox.setPrefHeight(160.0);
        goalsBox.setPrefWidth(492.0);
        myGoal.setText("GOALCARDS");
        myGoal.setVisible(true);
        double imageViewWidth = (goalsBox.getPrefWidth()-45) / 3.0;
        try {
            for (int i = commongoals.size()-1 ; i >=0 ; i--) {
                int id=commongoals.get(i).getId();
                Image image;
                if (i < 2) {
                    image = loadCardFrontImage(id);
                } else {
                    image = loadCardBackImage(id);
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageViewWidth);
                imageView.setFitHeight(goalsBox.getPrefHeight());
                imageView.setPreserveRatio(true);
                if(i==commongoals.size()-1 ) {
                    addSpecialGoalClickEffect(imageView, id);
                    revealed=false;
                }else {
                    addClickEffect(imageView);
                }
                goalsBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the player's hand with images of the cards.
     * The images are loaded from the player's hand.
     * All cards are shown with their front image.
     * Each card has an interaction effect to flip the card when clicked.
     */
    private void loadMyHand() {
        for(int i = 0; i < 3; i++)
            this.omar.set(i, true);
        myHandBox.getChildren().clear();
        myHandBox.setPrefHeight(160.0);
        myHandBox.setPrefWidth(492.0);
        double imageViewWidth = (myHandBox.getPrefWidth()-45) / 3.0;
        try {
            for (PlayableCard card : this.gui.getClient().getClient().getHand()) {
                Image image;
                image = loadCardFrontImage(card.getId());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageViewWidth);
                imageView.setFitHeight(myHandBox.getPrefHeight());
                imageView.setPreserveRatio(true);
                //addClickEffect(imageView);
                setmyHandinteraction(imageView, card.getId());
                myHandBox.getChildren().add(imageView);
            }
        } catch (FileNotFoundException e) {
            System.out.println("errore nel caricare la mano");
        }
    }

    /**
     * Adds a special click effect to the given image view.
     * The effect is to reveal or hide the secret goal card when the image view is clicked.
     * @param imageView The image view to add the effect to.
     * @param id The id of the card.
     */
    private void addSpecialGoalClickEffect(ImageView imageView, int id) {
        ColorAdjust colorAdjust = new ColorAdjust();
        Label label = new Label("");
        VBox parent = (VBox) goalsBox.getParent();  // Assumiamo che goalsBox sia contenuto in un StackPane
        parent.getChildren().add(label);
        label.setVisible(false);
        label.setStyle("-fx-text-fill: white");
        imageView.setOnMouseEntered(event -> {
            if(!revealed) {
                label.setText("Click to reveal your secret goal card!");
            }else {
                label.setText("Click to hide your secret goal card!");
            }
            label.setVisible(true);
            if (colorAdjust.getBrightness() == 0) {
                colorAdjust.setBrightness(0.5);
            } else {
                colorAdjust.setBrightness(0);
            }
            imageView.setEffect(colorAdjust);
        });
        imageView.setOnMouseExited(event -> {
            label.setVisible(false);
            if (colorAdjust.getBrightness() == 0.5) {
                colorAdjust.setBrightness(0);
            } else {
                colorAdjust.setBrightness(0.5);
            }
            imageView.setEffect(colorAdjust);
        });
        imageView.setOnMouseClicked(event -> {
            if(!revealed) {
                try {
                    Image frontImage = loadCardFrontImage(id);
                    imageView.setImage(frontImage);
                    label.setVisible(false);
                    revealed=true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Image backImage = loadCardBackImage(id);
                    imageView.setImage(backImage);
                    label.setVisible(false);
                    revealed=false;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds a click effect to the given image view.
     * The effect is to change the brightness of the image view when the mouse enters or exits the image view.
     * @param imageView The image view to add the effect to.
     */
    private void addClickEffect(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        imageView.setOnMouseEntered(event -> {
            if (colorAdjust.getBrightness() == 0) {
                colorAdjust.setBrightness(0.5);
            } else {
                colorAdjust.setBrightness(0);
            }
            imageView.setEffect(colorAdjust);
        });

        imageView.setOnMouseExited( event -> {
            if (colorAdjust.getBrightness() == 0.5) {
                colorAdjust.setBrightness(0);
            } else {
                colorAdjust.setBrightness(0.5);
            }
            imageView.setEffect(colorAdjust);
        });
    }

    /**
     * Sets the interaction for the given image view in the player's hand.
     * The interaction is to flip the card when the image view is clicked.
     * @param imageView The image view to set the interaction for.
     * @param cardId The id of the card.
     */
    private void setmyHandinteraction(ImageView imageView, int cardId) {
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
                    for(int i=0; i<this.gui.getClient().getClient().getHand().size(); i++){
                        if(this.gui.getClient().getClient().getHand().get(i).getId()==cardId){
                            omar.set(i, false);
                        }
                    }
                    imageView.setImage(flippedImage);
                    isFrontImageLoaded = false;
                } else {
                    Image frontImage = loadCardFrontImage(cardId);
                    for(int i=0; i<this.gui.getClient().getClient().getHand().size(); i++) {
                        if (this.gui.getClient().getClient().getHand().get(i).getId() == cardId) {
                            omar.set(i, true);
                        }
                    }
                    imageView.setImage(frontImage);
                    isFrontImageLoaded = true;
                }
            } catch (FileNotFoundException ignored) {
                System.out.println("boh");
            } catch (IndexOutOfBoundsException e){
                System.out.println("IndexOutOfBoundsException in setmyHandinteraction");
                System.out.println("myhand size: " + this.gui.getClient().getClient().getHand().size());
                System.out.println("omar size: " + omar.size());
                System.out.println("cardId: " + cardId);
            }
        });
    }

    /**
     * Updates the player's hand with the given hand.
     * @param hand The new hand of the player.
     */
    public void updateHands(LinkedList<PlayableCard> hand) {
        myhand = hand;
        loadMyHand();
    }

    /**
     * Updates the points of the players with the given points.
     * @param points The new points of the players.
     */
    public void updatePoints(HashMap<String, Integer> points) {
        if (points != null) {
            this.points = new LinkedHashMap<>(points);
            this.scoreBoard = new ScoreBoard(this.points);
            this.updatedPoints = true;
        } else {
            throw new IllegalArgumentException("Points map cannot be null");
        }
    }

    /**
     * Handles the action event of showing the points counter. If the points have been updated, it creates a new stage and
     * displays the scoreboard. If the points have not been updated yet, it displays a message indicating that the game has just started.
     * @param event The action event that triggered this method.
     */
    @FXML
    public void showPointsCounter(ActionEvent event) {
        if (updatedPoints) {
            Stage newStage = new Stage();
            newStage.setTitle("Scoreboard");

            AnchorPane scoreboardPane = new AnchorPane();

            BackgroundFill backgroundFill = new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, null);
            Background background = new Background(backgroundFill);

            scoreboardPane.setBackground(background);

            VBox vbox = new VBox();
            scoreboardPane.getChildren().add(vbox);
            vbox.setPrefWidth(500);
            vbox.setPrefHeight(300);
            AnchorPane.setTopAnchor(vbox, 270.0);
            AnchorPane.setLeftAnchor(vbox, 440.0);
            PlayerColor[] playerColors = PlayerColor.values();
            LinkedList<String> playersName = new LinkedList<>();
            for(String key : points.keySet()){
                playersName.add(key);
            }
            for(int i=0; i< points.size(); i++){
                PlayerColor color = playerColors[i];
                HBox hbox1 = createHbox("/view/MyCodexNaturalisPhotos/CODEX_pion_" + color.name().toLowerCase() + ".png", playersName.get(i));
                vbox.getChildren().add(hbox1);
            }
            Image image = loadImage("/view/MyCodexNaturalisPhotos/plateau.png");
            ImageView scoreboardImage = new ImageView(image);
            scoreboardImage.setFitWidth(334);
            scoreboardImage.setFitHeight(679);
            scoreboardImage.setPreserveRatio(true);

            scoreboardPane.getChildren().add(scoreboardImage);

            AnchorPane.setTopAnchor(scoreboardImage, 15.0);
            AnchorPane.setLeftAnchor(scoreboardImage, 84.0);


            scoreBoard.updatePlaceholders();
            for (ImageView placeholder : scoreBoard.getPlaceholders().values()) {
                scoreboardPane.getChildren().add(placeholder);
            }

            Scene scoreboardScene = new Scene(scoreboardPane, 735, 700);
            newStage.setScene(scoreboardScene);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(stage);
            newStage.setResizable(false);
            newStage.show();
        }else {
            messageLabel.setText("");
            String text= messageLabel.getText();
            messageLabel.setText("This game has just started!\n Every players has zero points." +text);
            return;
        }
    }

    /**
     * Creates a horizontal box (HBox) with an image and a label. The image is loaded from the provided path and the label
     * is set with the provided text.
     * @param imagePath The path to the image file.
     * @param labelText The text to be set on the label.
     * @return The created HBox.
     */
    private HBox createHbox(String imagePath, String labelText){
        ImageView image = new ImageView(loadImage(imagePath));
        image.setFitWidth(50);  // Set appropriate width
        image.setFitHeight(50); // Set appropriate height
        image.setPreserveRatio(true);

        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: black; -fx-font-size: 40px; -fx-font-family: 'Weibei TC'; -fx-font-weight: bold;");
        HBox hbox = new HBox(10); // Spaziatura tra ImageView e Label
        hbox.getChildren().addAll(image, label);
        return hbox;
    }

    /**
     * Displays an exception message based on the provided exception string.
     * @param exception The exception string.
     */
    public void showException(String exception) {
        switch (exception) {
            case "RequirementsNotSatisfied"-> {
                messageLabel.setText("Requirements NOT satisfied!\n" +
                        "Please, flip this gold card or play another one.");
                //setterò di nuovo la carta posizionabile nel field
            }
        }
    }

    /**
     * Sets the last round flag to false.
     */
    public void lastRound() {
        this.mirko = false;
    }

    /**
     * Displays a message indicating that a player has reached 20 points.
     * @param name The name of the player who reached 20 points.
     */
    public void twenty(String name) {
        if (! (name.equals(client.getNames()))) {
            secondmessageLabel.setText(name+ " HAS REACHED 20 POINTS!");
        } else {
            secondmessageLabel.setText("WOW, YOU REACHED 20 POINTS!");
        }
        secondmessageLabel.setVisible(true);
//        lastRound=true;
    }

    /**
     * This method is triggered when the user wants to view the playing fields of other players.
     * It creates a new stage and populates it with buttons for each other player.
     * When a button is clicked, it calls the plotOthersField method to display the selected player's playing field.
     *
     * @param event the action event that triggered this method
     */
    @FXML
    private void viewOthersPlayingField(ActionEvent event) {
        Stage newStage = new Stage();
        newStage.setTitle("MyCodexNaturalis");

        StackPane stackPane = new StackPane();

        BackgroundFill backgroundFill = new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, null);
        Background background = new Background(backgroundFill);
        stackPane.setBackground(background);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        stackPane.getChildren().add(vbox);
        Label instructionLabel = new Label("Whose playing field do you want to view?");
        instructionLabel.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-family: 'Weibei TC'; -fx-font-weight: bold;");
        vbox.getChildren().add(instructionLabel);

        HashMap<String, PlayingField> others = client.getClient().getOtherFields();


        for (String playerName : others.keySet()) {
            Button playerButton = new Button(playerName);
            playerButton.setStyle(
                    "-fx-background-color: #154c16;" + // Colore di sfondo verde
                            "-fx-text-fill: white;" + // Colore del testo bianco
                            "-fx-font-size: 14px;" + // Dimensione del font
                            "-fx-font-family: 'Arial';" + // Famiglia del font
                            "-fx-padding: 8px 16px;" + // Padding interno (top-bottom, left-right)
                            "-fx-border-radius: 5px;" + // Arrotondamento dei bordi
                            "-fx-background-radius: 5px;" + // Arrotondamento dei bordi del background
                            "-fx-cursor: hand;" + // Cambia il cursore quando si passa sopra il bottone
                            "-fx-background-color: #154c16;" + // Colore di sfondo verde
                            "-fx-text-fill: white;" + // Colore del testo bianco
                            "-fx-font-size: 14px;" + // Dimensione del font
                            "-fx-font-family: 'Arial';" + // Famiglia del font
                            "-fx-padding: 8px 16px;" + // Padding interno (top-bottom, left-right)
                            "-fx-border-radius: 5px;" + // Arrotondamento dei bordi
                            "-fx-background-radius: 5px;" + // Arrotondamento dei bordi del background
                            "-fx-cursor: hand;" + // Cambia il cursore quando si passa sopra il bottone
                            "-fx-background-color: #154c16;" + // Colore di sfondo verde
                            "-fx-text-fill: white;" + // Colore del testo bianco
                            "-fx-font-size: 14px;" + // Dimensione del font
                            "-fx-font-family: 'Arial';" +
                            "-fx-padding: 8px 16px;" + // Padding interno (top-bottom, left-right)
                            "-fx-border-radius: 5px;" + // Arrotondamento dei bordi
                            "-fx-background-radius: 5px;" + // Arrotondamento dei bordi del background
                            "-fx-cursor: hand;" // Cambia il cursore quando si passa sopra il bottone
            );
            playerButton.setOnAction(e -> {
                plotOthersField(playerName);
            });
            vbox.getChildren().add(playerButton);
        }


        Scene scene = new Scene(stackPane, 600, 400);
        newStage.setScene(scene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(stage);
        newStage.show();
    }

    /**
     * This method is used to display the playing field of a specific player.
     * It creates a new stage and populates it with the cards in the player's playing field.
     * The cards are displayed in a grid, with their positions determined by their coordinates in the playing field.
     *
     * @param name the name of the player whose playing field is to be displayed
     */
    @FXML
    public void plotOthersField(String name) {
        PlayingField field = this.gui.getClient().getClient().getOtherFields().get(name);
        StackPane stackPane = new StackPane();
        ScrollPane scrollPane = new ScrollPane();
        GridPane mariuccio = new GridPane();

        mariuccio.setHgap(0);
        mariuccio.setVgap(0);

        double cellWidth = 210;
        double cellHeight = 140.0;

        int maxX = field.getField().keySet().stream().mapToInt(Position::getX).max().orElse(400);
        int minX = field.getField().keySet().stream().mapToInt(Position::getX).min().orElse(400);
        int maxY = field.getField().keySet().stream().mapToInt(Position::getY).max().orElse(400);
        int minY = field.getField().keySet().stream().mapToInt(Position::getY).min().orElse(400);

        for (int j = 0; j < maxY - minY + 1; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(cellHeight);
            rowConstraints.setMaxHeight(cellHeight);
            mariuccio.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < maxX - minX + 1; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(cellWidth);
            columnConstraints.setMaxWidth(cellWidth);
            mariuccio.getColumnConstraints().add(columnConstraints);
        }

        for (Position p : field.getField().keySet()) {
            PlayableCard card = field.getField().get(p);
            ImageView imageView = new ImageView();
            try {
                int x = p.getX() - minX;
                int y = maxY - p.getY();
                Image image;
                if (p.getFace() == FB.BACK) {
                    image = loadCardBackImage(card.getId());
                } else {
                    image = loadCardFrontImage(card.getId());
                }
                imageView.setImage(image);
                imageView.setFitWidth(cellWidth);
                imageView.setFitHeight(cellHeight);
                imageView.setPreserveRatio(true);
                GridPane.setMargin(imageView, new Insets(0, 0, 111 * y, -45.94 * x));
                mariuccio.add(imageView, x, y);
            } catch (FileNotFoundException e) {
                System.out.println("Errore nel print playing field");
            }
        }

        mariuccio.setAlignment(Pos.CENTER);
        scrollPane.setContent(mariuccio);
        scrollPane.setPrefSize(800, 600);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        stackPane.getChildren().add(scrollPane);
        StackPane.setAlignment(scrollPane, Pos.CENTER);

        Stage stage = new Stage();
        Scene scene = new Scene(stackPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle(name + "'s Playing Field");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.stage);
        stage.show();
    }

    /**
     * This method is triggered when the user wants to open the chat.
     * It creates a new stage and loads the chat.fxml file into it.
     * It also initializes the chat controller and sets the client and GUI for it.
     *
     * @param event the action event that triggered this method
     * @throws IOException if there is an error loading the chat.fxml file
     */
    @FXML
    private void openChat(ActionEvent event) throws IOException {
        URL fxmlUrl = getClass().getResource("/view/chat.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        AnchorPane chatRoot = loader.load();
        ChatController chatController = loader.getController();
        chatController.setGui(this.gui);
        chatController.setClient(client);
        chatController.initializeChat();
        this.gui.setChatController(chatController);

        Scene chatScene = new Scene(chatRoot);

        Stage chatStage = new Stage();
        chatStage.setTitle("Chat");
        chatStage.setScene(chatScene);
        chatStage.initModality(Modality.WINDOW_MODAL);
        chatStage.initOwner(stage);
        chatStage.setOnCloseRequest(e -> {
        });
        chatStage.show();
    }
}
