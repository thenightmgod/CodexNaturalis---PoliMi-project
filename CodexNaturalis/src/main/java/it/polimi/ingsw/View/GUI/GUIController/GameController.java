package it.polimi.ingsw.View.GUI.GUIController;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.View.GUI.GUI;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class GameController extends GUIController {
    protected String username;
    protected GUI gui;
    protected Stage stage;
    protected CommonClient client;
    protected Parent root;
    private GoalCardController goalCardController;
    boolean StartCardOk = false;
    boolean popUpShown = false;
    boolean popUpShowing = false;
    boolean choosingPersonalGoal = false;
    boolean isFrontImageLoaded = true;


    @FXML
    private Stage popupStage;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public Pane gameField;
    @FXML
    private AnchorPane neutralBackgroundPane;
    @FXML
    public VBox cardGroup1;
    @FXML
    public VBox cardGroup2;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label welcomeLabel;
    @FXML
    private VBox resourceDeck;

    @FXML
    private HBox commonGoalsBox;
    @FXML
    private HBox startCardsHBox;

    @FXML
    private HBox goalCardsHBox;
    @FXML
    private ImageView leftStartCardImage;

    @FXML
    private ImageView rightStartCardImage;

    @FXML
    private Label chooseSideLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView leftGoalCardImageView;

    @FXML
    private ImageView rightGoalCardImageView;

    public void setRoot(Parent root) {
        this.root = root;
    }


    public void initialize() {
        goalCardController = null;
        titleLabel = new Label();
        titleLabel.setVisible(false);
        startCardsHBox.setVisible(true);
        goalCardsHBox.setVisible(false);

        String newImagePath = "/view/MyCodexNaturalisPhotos/pic8211904.jpg";
        Image newImage = loadImage(newImagePath);
        if (newImage != null) {
            backgroundImage.setImage(newImage);
            backgroundImage.setEffect(new GaussianBlur(10));

        } else {
            System.out.println("Failed to load image from path: " + newImagePath);
        }
    }


    public void choosePersonalGoal(LinkedList<GoalCard> goals) throws IOException {

        stage.close();

        URL fxmlUrl = getClass().getResource("/view/goalcardchoose.fxml");
        if (fxmlUrl == null) {
            throw new RuntimeException("FXML file not found");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setLocation(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, 1250, 650);

        goalCardController = loader.getController();
        goalCardController.setClient(client);
        goalCardController.setRoot(root);
        goalCardController.setScene(gui, stage);
        gui.setGoalCardController(goalCardController);
        goalCardController.runGoal(goals);

        stage.setScene(scene);

    }


    public void setScene(GUI gui, Stage stage) {

        username = client.getName();
        stage.setTitle(username + "_CodexNaturalis");
        gui.setGameController(this);
        this.gui = gui;
        this.stage = stage;

        Platform.runLater(() -> {
            welcomeLabel.setText("Benvenuto nella nuova partita, " + username + "!");
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> showNeutralBackground());
            pause.play();
        });
    }

    public void updateStartCard(StartCard card) {
        try {
            int cardId = card.getId(); // Ottieni l'ID della carta
            Image leftCardImage = loadCardFrontImage(cardId);
            Image rightCardImage = loadCardBackImage(cardId);

            leftStartCardImage.setImage(leftCardImage);
            rightStartCardImage.setImage(rightCardImage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il caricamento delle immagini della carta", e);
        }
        setStartCardFace();
    }

    public void setStartCardFace() {
        Platform.runLater(() -> {
            chooseSideLabel.setVisible(true);
            leftStartCardImage.setVisible(true);
            rightStartCardImage.setVisible(true);
        });
    }

    public void chooseStartCardFace() {
        enableCardInteractions();
        chooseSideLabel.setText("Choose the side");
    }

    public void waitYourTurn() {
        if (!StartCardOk) {
            chooseSideLabel.setText("wait, it's not your turn");
            disableCardInteractions();
        } else {
            chooseSideLabel.setText("Well done, start card chosen!");
            disableCardInteractions();
        }
    }

    @FXML
    public void showNeutralBackground() {
        welcomeLabel.setVisible(false);
        backgroundImage.setVisible(false);
        neutralBackgroundPane.setVisible(true);
    }


    //--------METODI DA SPOSTARE NELL'ALTRO CONTROLLER---------
    public void updateCommonGoals(LinkedList<GoalCard> goals) {
        if (commonGoalsBox == null) {
            System.out.println("commonGoalsBox è null");
            return;
        }

        if (!commonGoalsBox.getChildren().isEmpty()) {
            commonGoalsBox.getChildren().clear(); // Rimuove tutte le carte attuali
        }

        for (GoalCard goal : goals) {
            int cardId = goal.getId();
            String imagePath = String.format("/view/CODEX_cards_gold_front/%03d.png", cardId);
            Image cardImage = new Image(getClass().getResource(imagePath).toExternalForm());
            ImageView cardImageView = new ImageView(cardImage);
            cardImageView.setFitWidth(160); // Imposta la larghezza desiderata
            cardImageView.setFitHeight(123); // Imposta l'altezza desiderata
            cardImageView.setPreserveRatio(true); // Mantiene il rapporto d'aspetto


            commonGoalsBox.getChildren().add(cardImageView);
        }
    }

    public void updateResourceDeck(LinkedList<ResourceCard> deck) {
        //qui dovrei posizionare il deck a dx

    }

    public void updateGoldDeck(LinkedList<GoldCard> golddeck) {
        //qui devo posizionare il deck a sx


    }

    public void setHand(LinkedList<PlayableCard> hand) {
        Platform.runLater(() -> {

            if (!cardGroup1.getChildren().isEmpty()) {
                cardGroup1.getChildren().clear(); // Rimuove tutte le carte attuali
            }

            if (!cardGroup2.getChildren().isEmpty()) {
                cardGroup2.getChildren().clear(); // Rimuove tutte le carte attuali
            }
            for (PlayableCard card : hand) {
                int cardId = card.getId();

                try {
                    Image cardImage = loadCardFrontImage(cardId);

                    ImageView imageView = new ImageView(cardImage);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);

                    setCardInteraction(imageView, cardId);

                    if (cardId >= 1 && cardId <= 40) {
                        cardGroup1.getChildren().add(imageView);
                    } else if (cardId >= 41 && cardId <= 80) {
                        cardGroup2.getChildren().add(imageView);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // Gestione dell'errore nel caricamento dell'immagine
                }
            }
        });
    }





   /* private void loadCardImages() {
        String directoryPath = "/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/CODEX_cards_gold_back";
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null) {
                for (File file : files) {
                    try {
                        FileInputStream inputStream = new FileInputStream(file);
                        Image image = new Image(inputStream);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(100);
                        imageView.setPreserveRatio(true);
                        cardGroup2.getChildren().add(imageView);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }*/

    private void setCardInteraction(ImageView imageView, int cardId) {
        imageView.setOnMouseEntered(event -> {
            // Aggiungi effetto di illuminazione
            imageView.setOpacity(0.7);
        });

        imageView.setOnMouseExited(event -> {
            // Rimuovi effetto di illuminazione
            imageView.setOpacity(1.0);
        });

        imageView.setOnMouseClicked(event -> {
            try {
                if (isFrontImageLoaded) {
                    // Se l'immagine front è caricata, carica l'immagine back
                    Image flippedImage = loadCardBackImage(cardId);
                    imageView.setImage(flippedImage);
                    isFrontImageLoaded = false; // Aggiorna lo stato dell'immagine
                } else {
                    // Se l'immagine back è caricata, carica l'immagine front
                    Image frontImage = loadCardFrontImage(cardId);
                    imageView.setImage(frontImage);
                    isFrontImageLoaded = true; // Aggiorna lo stato dell'immagine
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Gestione dell'errore nel caricamento dell'immagine
            }
        });
    }

    @FXML
    public void moveUp(ActionEvent event) {
    }

    @FXML
    public void moveDown(ActionEvent event) {
    }

    @FXML
    public void moveLeft(ActionEvent event) {
    }

    @FXML
    public void moveRight(ActionEvent event) {
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
        if (!choosingPersonalGoal) {
            if (event.getSource() instanceof ImageView) {
                ImageView card = (ImageView) event.getSource();
                if (!card.isDisabled()) {
                    if (card == leftStartCardImage) {
                        boolean face = true;
                        client.setStartCardFace(face, client);
                    } else if (card == rightStartCardImage) {
                        boolean face = false;
                        client.setStartCardFace(face, client);
                    }
                }
            }
            StartCardOk = true;
            this.gui.endTurn("StartCard");
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