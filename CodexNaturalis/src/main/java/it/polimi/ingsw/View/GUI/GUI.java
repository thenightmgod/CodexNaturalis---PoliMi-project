package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.View.GUI.GUIController.*;
import it.polimi.ingsw.View.GameView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GUI implements GameView {
    private String username;
    private Stage primaryStage;
    private Map<String, GUIController> guicontrollers = new HashMap<>();
    private Player Turn;
    private CommonClient client;
    private String[] args;
    public boolean first_turn = true;

    public void setFirst_turn(boolean first_turn) {
        this.first_turn = first_turn;
    }

    //-------METODI DEI CONTROLLER---------------------------
    public void start(String[] args, Stage stage) throws IOException {
        this.client = null;
        this.args = args;
        this.primaryStage = stage;
        this.Turn = null;
        switchToScene("login");
    }

    public String[] getArgs() {
        return args;
    }

    public void switchToScene(String sceneName, Object... args) throws IOException {
        GUIController controller = guicontrollers.get(sceneName);
        if (controller == null) {
            controller = loadController(sceneName, args);
            guicontrollers.put(sceneName, controller);
        }
        primaryStage.setScene(controller.getScene());

        switch(sceneName) {
            case "declareWinner" -> {
                primaryStage.setTitle("FINAL RESULTS");
            }
            case "login" -> {
                primaryStage.setTitle("Login");
            }
            default -> {
                primaryStage.setTitle(client.getName() + "'s " + sceneName);
            }
        }
        primaryStage.show();
    }

    private GUIController loadController(String sceneName, Object... args) throws IOException {
        URL fxmlUrl = getClass().getResource("/view/" + sceneName + ".fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        GUIController controller = loader.getController();
        controller.setGui(this);
        controller.setStage(primaryStage);
        controller.setClient(client);
        controller.setRoot(root);
        controller.setArgs(args);
        return controller;
    }

    public void setClient(CommonClient client) {
        this.client = client;
    }

    public void setName(String username) {
        this.username = username;
    }


    //----------METODI DELLA GAMEVIEW(interfaccia comune view)-------------------------------


    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) {
        client.getClient().setPointsCounter(points);
        Platform.runLater(()-> {
            ((TurnController)guicontrollers.get("turn")).updatePoints(points);
        });
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        Platform.runLater(() -> {
            try {
                switchToScene("goalCard", goals);
            } catch (IOException e) {
                System.out.println("Goal Card non correttamente inizializzata");
            }
        });
    }

    @Override
    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        client.getClient().setCommonGoals(goals);
        //Platform.runLater(() -> gameController.updateCommonGoals(goals));
    }


    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {

        client.getClient().setHand(hand);
        if(!first_turn) {
            Platform.runLater(() -> {
                ((TurnController)guicontrollers.get("turn")).updateHands(hand);
            });
        }
    }

    @Override
    public void updateField(PlayingField field, String name) {
        if (name.equals(client.getName())) {
            client.getClient().setField(field);
            if (!first_turn) {
                Platform.runLater(() -> {
                    ((TurnController) guicontrollers.get("turn")).plotField();
                });
            }
        }
    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {
        //PROB INUTILE IN TUTTO IL CODICE
    }


    @Override
    public void showException(String name, String exception) throws RemoteException, NotBoundException {
        switch (name) {
            case "NameAlreadyTakenException" -> {
                Platform.runLater(() -> {
                    guicontrollers.get("login").showException("NameAlreadyTakenException");
                });
            }
            case "RoomNotExistsException" -> {
                Platform.runLater(() -> {
                    guicontrollers.get("login").showException("RoomNotExistsException");
                });
            }
            case "RequirementsNotSatisfied" -> {
                Platform.runLater( () -> {
                    guicontrollers.get("turn").showException("RequirementsNotSatisfied");
                });
            }
            case "Nothing" -> {
                Platform.runLater(() -> {
                    ((TurnController) guicontrollers.get("turn")).drawCard();
                });
            }
        }
    }

    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        Platform.runLater(() -> {
            try {
                if ((guicontrollers).get("startCard") != null) {
                    ((StartCardController) guicontrollers.get("startCard")).showStartCard(card);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Show start card scene non correttamente inizializzata");
            }
        });
    }

    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
        //in tutta questa non ci va il platoform run later???
        this.Turn = player;
        if (Turn.getName().equals(client.getName())) {
            Platform.runLater(() -> {
                    switch (mex) {
                        case "StartCard" -> {
                                ((StartCardController) guicontrollers.get("startCard")).chooseStartCard();
                        }
                        case "GoalCard" -> {
                                ((GoalCardController) guicontrollers.get("goalCard")).chooseGoalCard();
                        }
                        case "NormalTurn"-> {
                            try {
                                switchToScene("turn", client.getClient().getDrawableResourceCards(), client.getClient().getDrawableGoldCards() , client.getClient().getCommonGoals(), client.getClient().getHand(), client.getClient().getField(), true);
                                first_turn=false;
                            } catch (IOException e) {
                                System.out.println("turnScene non correttamente inizializzata");
                            }
                        }
                    }
                });
            } else {
            //Non sono sicuro, forse il switch to scene
                try {
                    switchToScene("turn", client.getClient().getDrawableResourceCards(), client.getClient().getDrawableGoldCards(), client.getClient().getCommonGoals(), client.getClient().getHand(), client.getClient().getField(), true);
                } catch (IOException e) {
                    System.out.println("turnScene non correttamente inizializzata, non turno mio");
                }
        }
    }

    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    @Override
    public void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name) {
        Platform.runLater(() -> {
            if (start) {
                client.getClient().setDrawableGoldCards(deck);
            }
        });
    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name) {
        Platform.runLater(() -> {
            if (start) {
                client.getClient().setDrawableResourceCards(deck);
            }
        });
    }

    @Override
    public void startingGame() throws RemoteException {
        Platform.runLater(() -> {
            try {
                switchToScene("startCard", 1);
            } catch (IOException e) {
                System.out.println("StartCardScene non correttamente inizializzata");
            }
        });
    }

    @Override
    public void declareWinner(LinkedList<String> standings) {
        Platform.runLater( () -> {
            try {
                switchToScene("declareWinner", standings);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void twenty(String name) {

    }

    @Override
    public void lastRound() {

    }

    @Override
    public void printNotYourTurn(Player turn, String mex) {
        this.Turn = turn;
        Platform.runLater(() -> {
            switch(mex) {
                case "StartCard"-> {
                    ((StartCardController) guicontrollers.get("startCard")).waitYourTurn();
                }
                case "GoalCard"-> {
                    ((GoalCardController) guicontrollers.get("goalCard")).waitYourTurn();
                }
                case "NormalTurn"->{
                    try {
                    switchToScene("turn", client.getClient().getDrawableResourceCards(), client.getClient().getDrawableGoldCards() , client.getClient().getCommonGoals(), client.getClient().getHand(), client.getClient().getField(), false);
                    first_turn=false;
                } catch (IOException e) {
                    System.out.println("turnScene non correttamente inizializzata");
                }
                }
            }
        });
    }

    @Override
    public void leaveGameMessage() {

    }

    public CommonClient getClient() {
        return this.client;
    }

    public PlayerColor getPlayerColor() {
        PlayerColor color = Turn.getColor();
        return color;
    }

    public Player getTurn() {
        return Turn;
    }
}