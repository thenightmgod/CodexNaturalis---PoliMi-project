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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.*;
import java.rmi.RemoteException;
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
    @FXML
    private Label messageLabel;
    @FXML
    private Label myResource;
    @FXML
    private Label myGold;
    @FXML
    private Label myGoal;
    @FXML
    private HBox label_button_box;
    @FXML
    private Button piazzala;
    @FXML
    private Button myPointsButton;
    @FXML
    private GridPane marione;
    private AnchorPane anchorPane;
    private LinkedList<GoalCard> commongoals;
    private LinkedList<GoldCard> golddeck;
    private LinkedList<ResourceCard> resourcedeck = new LinkedList<>();
    private LinkedList<PlayableCard> myhand;
    private LinkedHashMap<String,Integer> points = new LinkedHashMap<>();

    private ScoreBoard scoreBoard;
    private PlayingField field;
    private int Points;
    boolean myTurn;
    boolean isFrontImageLoaded=true;
    boolean revealed;
    boolean updatedPoints=false;
    LinkedList<Boolean> omar = new LinkedList<>();


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
            points.put("Player1", 21);
            points.put("Player2", 21);
            points.put("Player3", 56);
            scoreBoard = new ScoreBoard(points);
        }else {
            waitMyTurn();
        }
    }

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

        for (int i = 0; i < maxX - minX + 1; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(cellHeight);
            rowConstraints.setMaxHeight(cellHeight);
            this.marione.getRowConstraints().add(rowConstraints);
        }
        for (int j = 0; j < maxY - minY + 1; j++) {
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

    public void updateResourceDeck(LinkedList<ResourceCard> resourcedeck) {
        this.resourcedeck = resourcedeck;
        loadResourceBox();
    }

    public void updateGoldDeck(LinkedList<GoldCard> golddeck){
        this.golddeck = golddeck;
        loadGoldBox();
    }

    public void isYourTurn() {
        messageLabel.setText("IT'S YOUR TURN!");
        messageLabel.setVisible(true);

        for(int cardIndex = 0; cardIndex < this.gui.getClient().getClient().getHand().size(); cardIndex++) {
            PlayableCard card = this.gui.getClient().getClient().getHand().get(cardIndex);
            for (Node node : myHandBox.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView imageView = (ImageView) node;
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
                        imageView.setOnDragOver(event -> {
                            if (event.getDragboard().hasString()) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }
                            event.consume();
                        });
                    }
                }
            }
        }
    }

    public void drawCard(){
        messageLabel.setText("Now, draw a card!");
        messageLabel.setVisible(true);

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
    }

    private void addDrawEffect(ImageView image, int deck, int index) {
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                client.drawCard(deck, index, this.client);
            } catch (RemoteException e) {
                System.out.println("Errore nel draw card");
            }
            removeDrawEffect();
            messageLabel.setText("Well done, now wait your turn.");
        });
    }

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

    public void waitMyTurn() {
        messageLabel.setText("Please, wait your turn to place a card");
        messageLabel.setVisible(true);
    }

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

    private void addSpecialGoalClickEffect(ImageView imageView, int id) {
        ColorAdjust colorAdjust = new ColorAdjust();
        Label label = new Label("");
        VBox parent = (VBox) goalsBox.getParent();  // Assumiamo che goalsBox sia contenuto in un StackPane
        parent.getChildren().add(label);
        label.setVisible(false);
        label.setStyle("-fx-text-fill: white");
        imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
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
        imageView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            label.setVisible(false);
            if (colorAdjust.getBrightness() == 0.5) {
                colorAdjust.setBrightness(0);
            } else {
                colorAdjust.setBrightness(0.5);
            }
            imageView.setEffect(colorAdjust);
        });
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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

    private void addClickEffect(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {

            if (colorAdjust.getBrightness() == 0) {
                colorAdjust.setBrightness(0.5);
            } else {
                colorAdjust.setBrightness(0);
            }
            imageView.setEffect(colorAdjust);
        });
        imageView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (colorAdjust.getBrightness() == 0.5) {
                colorAdjust.setBrightness(0);
            } else {
                colorAdjust.setBrightness(0.5);
            }
            imageView.setEffect(colorAdjust);
        });
    }

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

    public void updateHands(LinkedList<PlayableCard> hand) {
        myhand = hand;
        loadMyHand();
    }
    public void updatePoints(HashMap<String, Integer> points) {
        if (points != null) {
            this.points = new LinkedHashMap<>(points);
            this.scoreBoard = new ScoreBoard(this.points);
            this.updatedPoints = true;
        } else {
            throw new IllegalArgumentException("Points map cannot be null");
        }
    }

    @FXML
    public void showPointsCounter(ActionEvent event) {
        if (updatedPoints) {
            Stage newStage = new Stage();
            newStage.setTitle("Scoreboard");

            AnchorPane scoreboardPane = new AnchorPane();
            VBox vbox = new VBox();
            scoreboardPane.getChildren().add(vbox);
            vbox.setPrefWidth(500);
            vbox.setPrefHeight(300);
            AnchorPane.setTopAnchor(vbox, 300.0);
            AnchorPane.setLeftAnchor(vbox, 450.0);
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
            String text= messageLabel.getText();
            messageLabel.setText("This game has just started! Every players has zero points.\n" +text);
            return;
        }
    }

    private HBox createHbox(String imagePath, String labelText){
        ImageView image = new ImageView(loadImage(imagePath));
        image.setFitWidth(50);  // Set appropriate width
        image.setFitHeight(50); // Set appropriate height
        image.setPreserveRatio(true);

        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: black; -fx-tick-label-font: 50px Weibei TC Bold;");
        HBox hbox = new HBox(10); // Spaziatura tra ImageView e Label
        hbox.getChildren().addAll(image, label);
        return hbox;
    }


    public void showException(String exception) {
        switch (exception) {
            case "RequirementsNotSatisfied"-> {
                messageLabel.setText("The requirements for the card you chose aren't satisfied!\n" +
                        "Please, flip this gold card or play another one.");
                //setterò di nuovo la carta posizionabile nel field
            }
        }
    }

}
