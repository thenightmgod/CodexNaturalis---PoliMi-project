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
import java.rmi.RemoteException;
import java.util.LinkedList;

public class GameController extends GUIController {
    protected String username;
    protected GUI gui;
    protected Stage stage;
    protected CommonClient client;
    protected Parent root;

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
    private Label nextLabel;
    @FXML
    private ImageView leftStartCardImage;

    @FXML
    private ImageView rightStartCardImage;

    @FXML
    private Label chooseSideLabel;

    public void setRoot(Parent root) {
        this.root = root;
    }


    public void initialize() {
        String newImagePath = "/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/MyCodexNaturalisPhotos/pic8211904.jpg";
        Image newImage = loadImage(newImagePath);
        if (newImage != null) {
            backgroundImage.setImage(newImage);
            backgroundImage.setEffect(new GaussianBlur(10));
        } else {
            System.out.println("Failed to load image from path: " + newImagePath);
        }
    }

    public void choosePersonalGoal(LinkedList<GoalCard> goals) {
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
            File file = new File(imagePath);
            String localUrl = file.toURI().toURL().toString();
            return new Image(localUrl);
        } catch (MalformedURLException e) {
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
            welcomeLabel.setText("Benvenuto nella nuova partita, " + username + "!");
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> showNeutralBackground());
            pause.play();
        });
    }

    public void updateStartCard(StartCard card) {
        try {
            int cardId = card.getId(); // Ottieni l'ID della carta
            File cardFileLeft = new File("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/CODEX_cards_gold_front/" + String.format("%03d", cardId) + ".png");
            if (!cardFileLeft.exists()) {
                throw new FileNotFoundException("File non trovato: " + cardFileLeft.getPath());
            }
            Image leftCardImage = new Image(cardFileLeft.toURI().toURL().toString());

            File cardFileRight = new File("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/CODEX_cards_gold_back/" + String.format("%03d", cardId) + ".png");
            if (!cardFileRight.exists()) {
                throw new FileNotFoundException("File non trovato: " + cardFileRight.getPath());
            }
            Image rightCardImage = new Image(cardFileRight.toURI().toURL().toString());

            leftStartCardImage.setImage(leftCardImage);
            rightStartCardImage.setImage(rightCardImage);
        } catch (FileNotFoundException | MalformedURLException e) {
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
        if (leftGoalCardImageView != null) {
            disablePopUpScene();
        }
        }else if() {


        }  {
        else
        disableCardInteractions();
        chooseSideLabel.setText("It's not your turn");
    }

    public void showGameLayout() {
        welcomeLabel.setVisible(false);
        backgroundImage.setVisible(false);
        neutralBackgroundPane.setVisible(false);

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
            try {
                File cardFile = new File("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/CODEX_cards_gold_front/" + String.format("%03d", cardId) + ".png");
                Image cardImage = new Image(cardFile.toURI().toURL().toString());
                ImageView cardImageView = new ImageView(cardImage);
                cardImageView.setFitWidth(160); // Imposta la larghezza desiderata
                cardImageView.setFitHeight(123); // Imposta l'altezza desiderata
                cardImageView.setPreserveRatio(true); // Mantiene il rapporto d'aspetto


                commonGoalsBox.getChildren().add(cardImageView);
            } catch (MalformedURLException e) {
                e.printStackTrace(); // Stampa l'errore in caso di URL malformato
            }
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
        showGameLayout();
    }

    private Image loadCardFrontImage(int cardId) throws FileNotFoundException {
        String imagePath = "/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/CODEX_cards_gold_front/" + String.format("%03d", cardId) + ".png";
        File file = new File(imagePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getPath());
        }
        return new Image(file.toURI().toString());
    }


    private void loadCardImages() {
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
    }

    private Image loadCardBackImage(int cardId) throws FileNotFoundException {
        String imagePath = "/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/CODEX_cards_gold_back/" + String.format("%03d", cardId) + ".png";
        File file = new File(imagePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getPath());
        }
        return new Image(file.toURI().toString());
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
            // Esegui flip della carta
            try {
                Image flippedImage = loadCardBackImage(cardId);
                imageView.setImage(flippedImage);
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

    public void enablePopUpScene() {
            Platform.runLater(() -> {
                if (popupStage != null) {
                    popupStage.close();
                }
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initOwner(stage);
                popupStage.setTitle(username+"_choosePopUp");

                VBox vbox = new VBox(10);
                vbox.setAlignment(Pos.CENTER);

                Label titleLabel = new Label("Choose your personal goal card!");
                titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gold; -fx-font-weight: bold;");

                HBox cardsBox = new HBox(20);
                cardsBox.setAlignment(Pos.CENTER);

                // Aggiungi le ImageView delle carte goal
                cardsBox.getChildren().addAll(leftGoalCardImageView, rightGoalCardImageView);

                vbox.getChildren().addAll(titleLabel, cardsBox);

                // Abilita le interazioni con le carte
                enableCardInteractions(leftGoalCardImageView);
                enableCardInteractions(rightGoalCardImageView);

                // Creazione della scena e visualizzazione della finestra
                Scene popupScene = new Scene(vbox, 600, 400);
                popupScene.getStylesheets().add("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/styles2.css"); // Aggiungi eventuali stili CSS
                popupStage.setScene(popupScene);
                popupStage.showAndWait(); // Mostra la finestra e aspetta che venga chiusa
            });
        }

    public void disablePopUpScene() {
        if (commonGoalsBox.getChildren().size() > 2) {
            return;
        }
        Platform.runLater(() -> {
            if (popupStage != null) {
                popupStage.close();
            }
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(stage);
            popupStage.setTitle(username+"waitPopUp");

            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);

            Label titleLabel = new Label("Wait for your turn to choose the personal goal card!");
            titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gold; -fx-font-weight: bold;");

            HBox cardsBox = new HBox(20);
            cardsBox.setAlignment(Pos.CENTER);

            // Aggiungi le ImageView delle carte goal
            cardsBox.getChildren().addAll(leftGoalCardImageView, rightGoalCardImageView);

            vbox.getChildren().addAll(titleLabel, cardsBox);

            // Disabilita le interazioni con le carte
            disableCardInteractions(leftGoalCardImageView);
            disableCardInteractions(rightGoalCardImageView);


            Scene popupScene = new Scene(vbox, 600, 400);
            popupScene.getStylesheets().add("/Users/caterinagerini/Desktop/CodexNaturalis/CodexNaturalis/src/main/Resources/view/styles2.css"); // Aggiungi eventuali stili CSS
            popupStage.setScene(popupScene);
            popupStage.showAndWait();
        });
    }

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
            closePopupStage();
        });
    }
    private void closePopupStage() {
        if (popupStage != null) {
            popupStage.close();
        }
    }

    private void disableCardInteractions(ImageView imageView) {
        imageView.setOnMouseClicked(null); // Rimuove l'azione onClick
        imageView.setOpacity(0.5); // Opacità ridotta per indicare che le interazioni sono disabilitate
    }
}