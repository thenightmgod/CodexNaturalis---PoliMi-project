package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.*;
import it.polimi.ingsw.Network.VirtualView;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    /** This Class tests the main methods of the MainController,
     * the initialization,
     * the functions that add and remove players,
     * and the initialize room and start game functions,
     */
    @Test
    void Controller() throws RemoteException {
        GameController beppe = new GameController(5, 4);

        assertEquals(5, beppe.getRoomId());
        assertEquals(4, beppe.getNumPlayers());

        VirtualView v1 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn, String mex) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void isAlive() throws RemoteException {

            }

            @Override
            public void leaveGame() throws RemoteException {

            }

            @Override
            public void leaveGameMessage() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView v2 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn, String mex) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void isAlive() throws RemoteException {

            }

            @Override
            public void leaveGame() throws RemoteException {

            }

            @Override
            public void leaveGameMessage() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView v3 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn, String mex) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void isAlive() throws RemoteException {

            }

            @Override
            public void leaveGame() throws RemoteException {

            }

            @Override
            public void leaveGameMessage() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView v4 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn, String mex) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void isAlive() throws RemoteException {

            }

            @Override
            public void leaveGame() throws RemoteException {

            }

            @Override
            public void leaveGameMessage() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };

        beppe.addPlayer("hamingway", PlayerColor.YELLOW, v1);
        beppe.addPlayer("pino", PlayerColor.BLUE, v2);
        beppe.addPlayer("pinguino", PlayerColor.GREEN, v3);

        assertEquals(3, beppe.getHowManyPlayers());

        beppe.addPlayer("mirking", PlayerColor.RED, v4);

        assertEquals(4, beppe.getHowManyPlayers());

        beppe.initializeRoom();
        beppe.startGame();

        beppe.removePlayer("mirking");
        assertEquals(3, beppe.getHowManyPlayers());
    }
}