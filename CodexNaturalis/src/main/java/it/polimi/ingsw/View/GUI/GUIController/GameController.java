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
    boolean StartCardOk=false;
    boolean popUpShown=false;
    boolean isFrontImageLoaded=true;
    boolean choosingPersonalGoal=false;
    @FXML
    private Pane paneStartCard;
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
    private ImageView leftGoalCardImageView;
    @FXML
    private ImageView rightGoalCardImageView;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label welcomeLabel;
    @FXML
    private VBox resourceDeck;

    @FXML
    private HBox commonGoalsBox;
    @FXML
    private ImageView leftStartCardImage;
    @FXML
    private ImageView rightStartCardImage;

    @FXML
    private Label chooseSideLabel;
    @FXML
    private Label titleLabel;

    public void setRoot(Parent root) {
        this.root = root;
    }


    public void initialize() {
        titleLabel = new Label();
        titleLabel.setVisible(false);
        String newImagePath = "/view/MyCodexNaturalisPhotos/pic8211904.jpg";
        Image newImage = loadImage(newImagePath);
        if (newImage != null) {
            backgroundImage.setImage(newImage);
            backgroundImage.setEffect(new GaussianBlur(10));

        } else {
            System.out.println("Failed to load image from path: " + newImagePath);
        }
    }


    //mi carica i goal personali
    public void choosePersonalGoal(LinkedList<GoalCard> goals) {
        choosingPersonalGoal=true;
        leftGoalCardImageView = new ImageView();
        leftGoalCardImageView.setFitWidth(200);
        leftGoalCardImageView.setFitHeight(160);

        rightGoalCardImageView= new ImageView();
        rightGoalCardImageView.setFitHeight(160);
        rightGoalCardImageView.setFitWidth(200);

        if (goals.size() >= 2) {
            GoalCard goal1 = goals.get(0);
            GoalCard goal2 = goals.get(1);

            setImageForGoalCard(leftGoalCardImageView, goal1);
            setImageForGoalCard(rightGoalCardImageView, goal2);
        }
        showPersonalGoal();
    }

    public void showPersonalGoal() {
        welcomeLabel.setVisible(false);
        backgroundImage.setVisible(false);
        neutralBackgroundPane.setVisible(false);
        titleLabel=new Label();
        titleLabel.setText("");
        titleLabel.setVisible(true);
        leftGoalCardImageView.setVisible(true);
        rightGoalCardImageView.setVisible(true);
        disableCardInteractions(leftGoalCardImageView);
        disableCardInteractions(rightGoalCardImageView);
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
            boolean isLeftCard = imageView == leftGoalCardImageView;

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

    private Image loadImage(String imagePath) {
        try {
            URL resourceUrl = getClass().getResource(imagePath);
            if (resourceUrl == null) {
                System.out.println("Resource not found: " + imagePath);
                return null;
            }
            return new Image(resourceUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setScene(GUI gui, Stage stage) {
        username = client.getName();
        stage.setTitle(username + "_CodexNaturalis");
        gui.setGameController(this);
        this.gui = gui;
        this.stage = stage;
        Platform.runLater(() -> {
            welcomeLabel.setText("Welcome to the game, " + username + "!");
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> showNeutralBackground());
            pause.play();
        });
    }

    //----------METODI STARTCARD--------------

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
            neutralBackgroundPane.setDisable(false);
        });
    }

    public void chooseStartCardFace() {
        enableCardInteractions();
        chooseSideLabel.setText("Choose the side");
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
                } else if (card == rightStartCardImage) {
                    boolean face = false;
                    client.setStartCardFace(face, client);
                }
            }
        }
        this.gui.endTurn("StartCard");
    }

    private void enableCardInteractions() {
        leftStartCardImage.setDisable(false);
        rightStartCardImage.setDisable(false);
    }

    private void disableCardInteractions() {
        leftStartCardImage.setDisable(true);
        rightStartCardImage.setDisable(true);
    }


    public void waitYourTurn() {
        //se ho già assegnato le carte goal
        if (choosingPersonalGoal) {
            //se ho già mostrato la showGoalsCardsscene
            if (popUpShown) {
                titleLabel.setText("Well done, goal card chosen!");
                disableCardInteractions(leftGoalCardImageView);
                disableCardInteractions(rightGoalCardImageView);
            } else {
                titleLabel.setText("Wait for your turn to choose the personal goal card!");
                titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gold; -fx-font-weight: bold;");
            }
        } else {
            if (!StartCardOk) {
                chooseSideLabel.setText("wait, it's not your turn");
                disableCardInteractions();
            } else {
                chooseSideLabel.setText("Well done, start card chosen!");
                disableCardInteractions();
            }
        }
    }

    public void showGameLayout() {
        Color grayColor = Color.rgb(204, 204, 204); // #CCCCCC

        BackgroundFill backgroundFill = new BackgroundFill(grayColor, null, null);

        Background background = new Background(backgroundFill);

        mainBorderPane.setBackground(background);

        mainBorderPane.setVisible(true);
    }

    @FXML
    public void showNeutralBackground() {
        welcomeLabel.setVisible(false);
        backgroundImage.setVisible(false);
        neutralBackgroundPane.setVisible(true);
    }

    public void setClient(CommonClient client) {
        this.client = client;
    }

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

    private Image loadCardFrontImage(int cardId) throws FileNotFoundException {
        String cardPath = "/view/CODEX_cards_gold_front/" + String.format("%03d", cardId) + ".png";
        return loadImage(cardPath);
    }

    private Image loadCardBackImage(int cardId) throws FileNotFoundException {
        String cardPath = "/view/CODEX_cards_gold_back/" + String.format("%03d", cardId) + ".png";
        return loadImage(cardPath);
    }

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
//-------goal card

    private void enableCardInteractions(ImageView imageView) {
        imageView.setOnMouseEntered(event -> {
            imageView.setOpacity(0.7);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setOpacity(1.0);
        });

        imageView.setOnMouseClicked(event -> {
            boolean isLeftCard = imageView == leftGoalCardImageView;

            // Chiudi la finestra di selezione del goal personale
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.close();

            try {
                // Invia l'indice corretto a seconda se è l'immagine sinistra (1) o destra (2)
                if (isLeftCard) {
                    client.chooseGoalCard(1, client); // Indice 1 per l'immagine sinistra
                } else {
                    client.chooseGoalCard(2, client); // Indice 2 per l'immagine destra
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }try {
                gui.endTurn("GoalCard");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void disableCardInteractions(ImageView imageView) {
        imageView.setOnMouseClicked(null); // Rimuove l'azione onClick
        imageView.setOpacity(0.5); // Opacità ridotta per indicare che le interazioni sono disabilitate
    }

    public void showGoalCardsscene() {
        popUpShown = true;
        System.out.println("showGoalCardsscene called");
        titleLabel.setText("Choose your personal Goal card");
        stage.setTitle(username + "_chooseGoalCard");
        enableCardInteractions(leftGoalCardImageView);
        enableCardInteractions(rightGoalCardImageView);
    }

    private ImageView createGoalCardImageView(GoalCard goal) {
        try {
            int cardId = goal.getId();
            Image cardImage = loadCardFrontImage(cardId);

            ImageView imageView = new ImageView(cardImage);
            imageView.setFitWidth(160);
            imageView.setFitHeight(123);
            imageView.setPreserveRatio(true);

            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Gestione dell'errore nel caricamento dell'immagine
            return new ImageView(); // Oppure gestione alternativa
        }
    }
}