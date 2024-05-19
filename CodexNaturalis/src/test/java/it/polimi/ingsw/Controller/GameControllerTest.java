package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.PlayerPackage.*;
import it.polimi.ingsw.Network.VirtualView;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void Controller() {
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

        beppe.startGame();
        beppe.createDecks();
        //beppe.chooseGoalCard(beppe.getPlayers().getFirst(),1);
        assertThrows(WrongIndexException.class, () -> beppe.chooseGoalCard(beppe.getPlayers().getFirst(), 3));
        beppe.giveStartCard(FB.FRONT);

    }
}