package it.polimi.ingsw.Model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.*;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.RMI.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.LinkedList;
class RoomTest {
    /**
     * This test checks if various small functions of the Room class works properly, (createDecks, giveHands,
     * commonGoals, show2GoalCards, pickGoalCard).
     */
    @Test
    void testMetodiMinori(){
        GameController beppe = new GameController(5, 4);

        VirtualView client1 = new VirtualView() {
            @Override
            public void showException(String details) throws RemoteException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void showGoals(LinkedList<GoalCard> goals) throws RemoteException {

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
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView client2 = new VirtualView() {
            @Override
            public void showException(String details) throws RemoteException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void showGoals(LinkedList<GoalCard> goals) throws RemoteException {

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
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView client3 = new VirtualView() {
            @Override
            public void showException(String details) throws RemoteException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void showGoals(LinkedList<GoalCard> goals) throws RemoteException {

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
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView client4 = new VirtualView() {
            @Override
            public void showException(String details) throws RemoteException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void showGoals(LinkedList<GoalCard> goals) throws RemoteException {

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
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };

        beppe.addPlayer("dalla", PlayerColor.YELLOW, client1);
        beppe.addPlayer("degregori", PlayerColor.GREEN, client2);
        beppe.addPlayer("venditti", PlayerColor.RED, client3);
        beppe.addPlayer("hamingway", PlayerColor.BLUE, client4);

        beppe.getPlayers().getFirst().addPoints(21);

        beppe.startGame();
        beppe.createDecks();
        try {
            beppe.giveHands();
        } catch (RemoteException e) {
            fail("Unexpected RemoteException: " + e.getMessage());
        }
        beppe.commonGoals();
        beppe.getGame().setTwentyFlag();
        beppe.getGame().setLastRound();
        try {
            beppe.chooseGoalCard(beppe.getPlayers().getFirst(), 2);
        } catch (WrongIndexException e) {
            fail("Unexpected WrongIndexException: " + e.getMessage());
        }
    }
}
