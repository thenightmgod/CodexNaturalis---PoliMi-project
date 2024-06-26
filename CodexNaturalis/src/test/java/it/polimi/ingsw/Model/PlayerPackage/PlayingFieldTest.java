package it.polimi.ingsw.Model.PlayerPackage;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import it.polimi.ingsw.Model.DeckPackage.StartDeck;
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayingFieldTest {
    private static Position x;
    private static PlayingField prova;
    private static Position z;
    private static Corner primoHR, primoHL, primoLR, primoLL, secondoHR, secondoHL, secondoLR, secondoLL, terzoHR, terzoHL, terzoLL, terzoLR, quartoHR, quartoHL,quartoLR,quartoLL,quintoHR,quintoHL,quintoLL,quintoLR,sestoHR,sestoLL,sestoHL,sestoLR;

    /**
     * Contains the initialization steps to make before running each test.
     */
    @BeforeEach
    void setup(){
        x = new Position(1,1);
        prova = new PlayingField();

        primoHR = new Corner(EMPTY, Orientation.HR);
        primoHL = new Corner(EMPTY, Orientation.HL);
        primoLR = new Corner(ABSENT, Orientation.LR);
        primoLL = new Corner(ABSENT, Orientation.LL);
        secondoHR = new Corner(EMPTY, Orientation.HR);
        secondoHL = new Corner(ABSENT, Orientation.HL);
        secondoLR = new Corner(EMPTY, Orientation.LR);
        secondoLL = new Corner(ABSENT, Orientation.LL);
        terzoHR = new Corner(EMPTY, Orientation.HR);
        terzoHL = new Corner(ABSENT, Orientation.HL);
        terzoLR = new Corner(EMPTY, Orientation.LR);
        terzoLL = new Corner(EMPTY, Orientation.LL);
        quartoHR = new Corner(ABSENT, Orientation.HR);
        quartoHL = new Corner(ABSENT, Orientation.HL);
        quartoLR = new Corner(EMPTY, Orientation.LR);
        quartoLL = new Corner(EMPTY, Orientation.LL);
        sestoHR = new Corner(EMPTY, Orientation.HR);
        sestoHL = new Corner(EMPTY, Orientation.HL);
        sestoLR = new Corner(EMPTY, Orientation.LR);
        sestoLL = new Corner(EMPTY, Orientation.LL);


        quintoHR = new Corner(EMPTY, Orientation.HR);
        quintoHL = new Corner(EMPTY, Orientation.HL);
        quintoLR = new Corner(EMPTY, Orientation.LR);
        quintoLL = new Corner(EMPTY, Orientation.LL);



    }

    /**
     * This test checks if the getPosFromCornerFunction returns the right position when called on a Corner.
     */
    @Test
    void getPosFromCornerTest(){
        z= prova.getPosFromCorner(x, Orientation.LR);
        x.setX(2);
        x.setY(0);

        assert(x.equals(z));
    }

    @Test
    void updateFreePositionsTest() throws RemoteException, NotBoundException {
        LinkedList<Player> players = new LinkedList<>();
        Player p1 = new Player("franco", PlayerColor.RED);
        players.add(p1);
        LinkedList<VirtualView> views = new LinkedList<>();
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
        views.add(v1);

        Room r1 = new Room(1, players, views);

        ResourceDeck rd = new ResourceDeck();
        StartDeck sd = new StartDeck();
        GoldDeck gd = new GoldDeck();
        StartCard sd1 = (StartCard) sd.getCardById(81);

        ResourceCard c1 = (ResourceCard) rd.getCardById(24);
        GoldCard c2 = (GoldCard) gd.getCardById(42);

        ResourceCard c3 = (ResourceCard) rd.getCardById(7);
        ResourceCard c4 = (ResourceCard) rd.getCardById(3);

        ResourceCard c5 = (ResourceCard) rd.getCardById(9);
        ResourceCard c6 = (ResourceCard) rd.getCardById(10);


        LinkedList<PlayableCard> hand = p1.getHand();
            hand.add(sd1);

            r1.placeStartCard(p1, FB.BACK);

            LinkedList<Position> free =  p1.getPlayerField().getFreePositions();

            r1.placeCard(c1, FB.FRONT, -1, -1);

            r1.placeCard(c2, FB.BACK, 1, -1);

            r1.placeCard(c5, FB.BACK,2, -2);

            r1.placeCard(c3, FB.FRONT, 1, -3);

            r1.placeCard(c6, FB.BACK, 0, -4);

            Position x1 = new Position(0, -2);
            assert(free.contains(x1));

            r1.placeCard(c4, FB.FRONT, -1, -3);
            assert(!free.contains(x1));
        }
}