package it.polimi.ingsw.Model;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.*;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import it.polimi.ingsw.Model.PlayerPackage.*;
import it.polimi.ingsw.Model.RoomPackage.ObserverManager;
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
class RoomTest {

    /**
     * This test checks if various small functions of the Room class works properly, (createDecks, placeCard,
     * changeTurns, show2GoalCards, pickGoalCard).
     */
    @Test
    void testMetodiMinori() throws RemoteException, NotBoundException {

        VirtualView client1 = new VirtualView() {
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
        VirtualView client2 = new VirtualView() {
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
        VirtualView client3 = new VirtualView() {
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
        VirtualView client4 = new VirtualView() {
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

        Player p1 = new Player("lollo", PlayerColor.RED);
        Player p2 = new Player("nina", PlayerColor.GREEN);
        Player p3 = new Player("hamin", PlayerColor.BLUE);
        Player p4 = new Player("pie", PlayerColor.YELLOW);
        LinkedList<Player> players = new LinkedList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        LinkedList<VirtualView> views = new LinkedList<>();
        views.add(client1);
        views.add(client2);
        views.add(client3);
        views.add(client4);
        Room r1 = new Room (1, players, views);
        ResourceDeck rd = new ResourceDeck();

        PlayingField pf = r1.getTurn().getPlayerField();
        LinkedList<Position> free = pf.getFreePositions();
        Position o = new Position(3,4);
        free.add(o);

        ResourceCard rc = (ResourceCard) rd.getCardById(5);
        r1.createDecks();
        r1.giveStartCards();
        r1.giveInitialCards(p1);
        r1.placeStartCard(p1, FB.FRONT);
        r1.placeCard(rc , FB.FRONT, 3, 4);
        r1.show2GoalCards(p1);
        r1.pickGoalCard(p1, 1);
        r1.changeTurns("StartCard");
        r1.changeTurns("GoalCard");
        r1.changeTurns("NormalTurn");
        r1.declareWinner();
    }
}
