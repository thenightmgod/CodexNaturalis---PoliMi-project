package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.View.GUI.GUIController.LoginController;
import it.polimi.ingsw.View.GUI.GUIController.ProtocolController;
import it.polimi.ingsw.View.GameView;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class GUI implements GameView {
    private String name;
    private CommonClient client;
    private GameController gameController;
    private LoginController loginController;
    private String[] args;
    //avrò anche un GameController

    public void setArgs(String[] args) {
        this.args=args;
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
    public void updatePoints(int points, String name) {

    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {

    }

    @Override
    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {

    }

    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {

    }

    @Override
    public void updateField(PlayingField field, String name) {

    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {

    }


    //-----------DEVO CHIAMARE IL PLATFORM RUN LATER????
    @Override
    public void showException(String name, String exception) throws RemoteException, NotBoundException {
        switch(name) {
            case "NameAlreadyTakenException" -> {
                //DA CAMBIARE COMPLETAMENTE QUESTA ECCEZIONE
                //if(protocolController != null) {
                //    Platform.runLater(() -> protocolController.showException("NameAlreadyTakenException"));
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

    }

    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {

    }

    @Override
    public void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name) {

    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name) {

    }

    @Override
    public void startingGame() throws RemoteException {

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

    }

    @Override
    public void leaveGameMessage() {

    }
}
