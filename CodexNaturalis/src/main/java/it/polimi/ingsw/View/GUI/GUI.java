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
    private LinkedList<GoalCard> goals;

    //-------METODI DEI CONTROLLER---------------------------
    public void start(String[] args, Stage stage) throws IOException {
        this.client=null;
        this.args=args;
        this.primaryStage=stage;
        this.Turn=null;
        switchToScene("login");
    }
    public String[] getArgs(){
        return args;
    }
    public void switchToScene(String sceneName) throws IOException {
        GUIController controller = guicontrollers.get(sceneName);
        if (controller == null) {
            controller = loadController(sceneName);
            guicontrollers.put(sceneName, controller);
        }
        primaryStage.setScene(controller.getScene());
        primaryStage.show();
    }

    private GUIController loadController(String sceneName) throws IOException {
        URL fxmlUrl = getClass().getResource("/view/" +sceneName + ".fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        GUIController controller = loader.getController();
        controller.setGui(this);
        controller.setStage(primaryStage);
        controller.setClient(client);
        controller.setRoot(root);
        return controller;
    }

    public void setClient(CommonClient client) {
        this.client=client;
    }
    public void setName(String username) {
        this.username=username;
    }


    //----------METODI DELLA GAMEVIEW---------------------------


    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) {
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        Platform.runLater(()-> {
            try {
                switchToScene("goalCard");
                this.goals=goals;
            } catch (IOException e) {
                System.out.println("StartCardScene non correttamente inizializzata");
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
        //Platform.runLater(() -> gameController.setHand(hand));
    }

    @Override
    public void updateField(PlayingField field, String name) {

    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {

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
        }
    }

    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        Platform.runLater(() -> {
            try {
                if((guicontrollers).get("startCard") != null) {
                    ((StartCardController) guicontrollers.get("startCard")).showStartCard(card);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Show start card scene non correttamente inizializzata");
            }
        });
    }

    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
        this.Turn = player;
        if (Turn.getName().equals(client.getName())) {
            switch (mex) {
                case "StartCard" -> {
                    Platform.runLater(() -> {
                        ((StartCardController) guicontrollers.get("startCard")).chooseStartCard();
                    });
                }
                case "GoalCard" ->{

                }
                   // Platform.runLater(() ->
                   //     goalCardController.showGoalCardscene());
            }
        }else {

        }
    }

    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    @Override
    public void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name) {
        Platform.runLater(() -> {
            if(start) {
             //   gameController.updateGoldDeck(deck);
                client.getClient().setDrawableGoldCards(deck);
            }
        } );
    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name) {
        Platform.runLater(() -> {
            if(start) {
              //  gameController.updateResourceDeck(deck);
                client.getClient().setDrawableResourceCards(deck);
            }

        } );
    }
    @Override
    public void startingGame() throws RemoteException{
        Platform.runLater(()-> {
            try {
                switchToScene("startCard");
            } catch (IOException e) {
                System.out.println("StartCardScene non correttamente inizializzata");
            }

        });

            //   try {
                //loginController.showGameScene();
        //    } //catch (IOException e) {
         //       throw new RuntimeException(e);
       //     }
    }

    @Override
    public void declareWinner(LinkedList<String> standings) {

    }

    @Override
    public void twenty(String name) {

    }

    @Override
    public void lastRound() {

    }

    @Override
    public void printNotYourTurn(Player turn) {
        this.Turn = turn;
        Platform.runLater(() -> {
            ((StartCardController) guicontrollers.get("startCard")).waitYourTurn();
        });
    }

    @Override
    public void leaveGameMessage() {

    }
    public CommonClient getClient() {
        return this.client;
    }
    public PlayerColor getPlayerColor() {
        PlayerColor color=Turn.getColor();
        return color;
    }
    public Player getTurn() {
        return Turn;
    }
}