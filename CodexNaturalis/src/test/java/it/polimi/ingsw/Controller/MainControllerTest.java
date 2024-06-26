package it.polimi.ingsw.Controller;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.VirtualView;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

class MainControllerTest {
    @Test
    void Maincontroller() throws NotBoundException, RemoteException {
        MainController game1 = new MainController();

        VirtualView v1 = new VirtualView() {
            @Override
            public void sendPlayers(LinkedList<String> players) throws RemoteException {

            }

            @Override
            public void updateChat(String name, LinkedList<ChatMessage> chat) throws RemoteException {

            }

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

            /**
             * Gets the name of the player.
             *
             * @return The name of the player.
             * @throws RemoteException If a remote access error occurs.
             */
            @Override
            public String getNames() throws RemoteException {
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

            /**
             * Checks if this client is alive.
             *
             * @throws RemoteException If a remote access error occurs.
             */
            @Override
            public void isAlivee() throws RemoteException {

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
        game1.createGame("hamingway", 2, v1, 1);

        VirtualView v2 = new VirtualView() {
            @Override
            public void sendPlayers(LinkedList<String> players) throws RemoteException {

            }

            @Override
            public void updateChat(String name, LinkedList<ChatMessage> chat) throws RemoteException {

            }

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
            public String getNames() throws RemoteException {
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
            public void isAlivee() throws RemoteException {

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
        game1.joinGame("pino", v2, 1);

        game1.getYourRoomId("pino");

    }

}