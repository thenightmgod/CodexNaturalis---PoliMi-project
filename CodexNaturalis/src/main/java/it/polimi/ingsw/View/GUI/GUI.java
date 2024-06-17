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
import it.polimi.ingsw.View.GUI.GUIController.GameController;
import it.polimi.ingsw.View.GUI.GUIController.LoginController;
import it.polimi.ingsw.View.GameView;
import javafx.application.Platform;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public class GUI implements GameView {
    private String name;
    private Player Turn;
    private CommonClient client;
    private GameController gameController;

    private LoginController loginController;
    private String[] args;
    //avrò anche un GameController

    public void setArgs(String[] args) {
        this.args=args;
        this.Turn=null;
    }

    //-------METODI DEI CONTROLLER---------------------------

    public String[] getArgs(){
        return args;
    };
    public GameController getGameController() {
        return gameController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    public void setGameController(GameController gc) {
        this.gameController = gc;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setClient(CommonClient client) {
        this.client=client;
    }


    //----------METODI DELLA GAMEVIEW---------------------------


    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) {
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        Platform.runLater(() -> gameController.choosePersonalGoal(goals));
    }

    @Override
    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        client.getClient().setCommonGoals(goals);
        Platform.runLater(() -> gameController.updateCommonGoals(goals));
    }


    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {
        client.getClient().setHand(hand);
        Platform.runLater(() -> gameController.setHand(hand));
    }

    @Override
    public void updateField(PlayingField field, String name) {

    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {

    }


    @Override
    public void showException(String name, String exception) throws RemoteException, NotBoundException {
        switch(name) {
            case "NameAlreadyTakenException" -> {
                if(loginController != null) {
                    Platform.runLater(() -> loginController.showException("NameAlreadyTakenException"));
                }
                }
            case "RoomNotExistsException" -> {
                if(loginController != null) {
                    loginController.showException("RoomNotExistsException");
                }
            }
        }
    }



    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        Platform.runLater(() -> {
            gameController.updateStartCard(card);
        });
    }

    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
        this.Turn = player;
        if (Turn.getName().equals(client.getName())) {
            switch (mex) {
                case "StartCard" -> {
                    Platform.runLater(() -> {
                        gameController.chooseStartCardFace();
                    });
                }
                case "GoalCard" -> {
                    Platform.runLater(() -> {
                        gameController.enablePopUpScene();
                    });
                    ;
                }
            }
        }
    }

    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    @Override
    public void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name) {
        Platform.runLater(() -> {
            if(start) {
                gameController.updateGoldDeck(deck);
                client.getClient().setDrawableGoldCards(deck);
            }
        } );
    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name) {
        Platform.runLater(() -> {
            if(start) {
                gameController.updateResourceDeck(deck);
                client.getClient().setDrawableResourceCards(deck);
            }

        } );
    }
    @Override
    public void startingGame() throws RemoteException{
        Platform.runLater( ()-> {
                try {
                    loginController.showGameScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
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
            gameController.waitYourTurn();
        });
    }

    @Override
    public void leaveGameMessage() {

    }
    public PlayerColor getPlayerColor() {
        PlayerColor color=Turn.getColor();
        return color;
    }
    public Player getTurn() {
        return Turn;
    }
}
