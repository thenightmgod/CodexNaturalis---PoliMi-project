package it.polimi.ingsw.Model.PlayerPackage;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.Composition;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
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
    private static Corner primoHR, primoHL, primoLR, primoLL, secondoHR, secondoHL, secondoLR, secondoLL, terzoHR, terzoHL, terzoLL,terzoLR, quartoHR,quartoHL,quartoLR,quartoLL,quintoHR,quintoHL,quintoLL,quintoLR,sestoHR,sestoLL,sestoHL,sestoLR;
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
     * This test checks if the FreePositions array is correctly updated by the function, when a new card is placed.
     */
//    void updateFreePositionsTest() throws RemoteException {
 /*       LinkedList<Corner> CornersPrimo = new LinkedList<>();
        LinkedList<Corner> CornersSecondo = new LinkedList<>();
        LinkedList<Corner> CornersTerzo = new LinkedList<>();
        LinkedList<Corner> CornersQuarto = new LinkedList<>();
        LinkedList<Corner> CornersQuinto = new LinkedList<>();
        LinkedList<Corner> CornersSesto = new LinkedList<>();
        boolean primoRes[] = new boolean[4];
        boolean secondoRes[] = new boolean[4];
        boolean terzoRes[] = new boolean[4];
        boolean quartoRes[] = new boolean[4];
        boolean quintoRes[] = new boolean[4];
        boolean sestoRes[] = new boolean[4];
        primoRes[0] = false;
        primoRes[1] = false;
        primoRes[2] = false;
        primoRes[3] = true;
        secondoRes[0] = true;
        secondoRes[1] = false;
        secondoRes[2] = false;
        secondoRes[3] = false;
        terzoRes[0] = true;
        terzoRes[1] = false;
        terzoRes[2] = false;
        terzoRes[3] = false;
        quartoRes[0] = true;
        quartoRes[1] = false;
        quartoRes[2] = false;
        quartoRes[3] = false;
        quintoRes[0] = true;
        quintoRes[1] = false;
        quintoRes[2] = false;
        quintoRes[3] = false;
        sestoRes[0] = true;
        sestoRes[1] = false;
        sestoRes[2] = false;
        sestoRes[3] = false;

        CornersPrimo.addLast(primoHR);
        CornersPrimo.addLast(primoHL);
        CornersPrimo.addLast(primoLR);
        CornersPrimo.addLast(primoLL);

        CornersSecondo.addLast(secondoHR);
        CornersSecondo.addLast(secondoHL);
        CornersSecondo.addLast(secondoLR);
        CornersSecondo.addLast(secondoLL);

        CornersTerzo.addLast(terzoHR);
        CornersTerzo.addLast(terzoHL);
        CornersTerzo.addLast(terzoLR);
        CornersTerzo.addLast(terzoLL);

        CornersQuarto.addLast(quartoHR);
        CornersQuarto.addLast(quartoHL);
        CornersQuarto.addLast(quartoLR);
        CornersQuarto.addLast(quartoLL);

        CornersQuinto.addLast(quintoHR);
        CornersQuinto.addLast(quintoHL);
        CornersQuinto.addLast(quintoLR);
        CornersQuinto.addLast(quintoLL);

        CornersSesto.addLast(sestoHR);
        CornersSesto.addLast(sestoHL);
        CornersSesto.addLast(sestoLR);
        CornersSesto.addLast(sestoLL);

        Position primo = new Position (1,1);
        Position secondo = new Position (0,2);
        Position terzo = new Position (1,3);
        Position quarto = new Position (2,4);
        Position quinto = new Position (2,2);
        Position sesto = new Position (4,4);

        ResourceCard primores = new ResourceCard(1, primoRes, CardColor.BLUE, 3, CornersPrimo);
        ResourceCard secondores = new ResourceCard(2, secondoRes, CardColor.BLUE, 3, CornersSecondo);
        ResourceCard terzores = new ResourceCard(3, terzoRes, CardColor.BLUE, 3, CornersTerzo);
        ResourceCard quartores = new ResourceCard(4, quartoRes, CardColor.BLUE, 3, CornersQuarto);
        ResourceCard quintores = new ResourceCard(5, quintoRes, CardColor.BLUE, 3, CornersQuinto);
        ResourceCard sestores = new ResourceCard(6, sestoRes, CardColor.BLUE, 3, CornersSesto);

        HashMap<Position, PlayableCard> mappa = prova.getField();

        mappa.put(primo, primores);
        mappa.put(secondo, secondores);
        mappa.put(terzo, terzores);
        mappa.put(quarto, quartores);
        mappa.put(quinto,quintores);
        mappa.put(sesto,sestores);

        LinkedList<Position> posizionilibere = prova.getFreePositions();
        Position toCheck = new Position (3,3);

        posizionilibere.add(quinto);

        prova.updateFreePositions(quinto);

        assert(posizionilibere.contains(toCheck));

        LinkedList<Resources> Backres = new LinkedList<>();
        Backres = primores.getBackRes();
        assert(Backres.contains(Resources.INSECT_KINGDOM));*/



 /*       VirtualView v1 = new VirtualView() {
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

        beppe.addPlayer("hamingway", PlayerColor.YELLOW, v1);
        beppe.addPlayer("pino", PlayerColor.BLUE, v2);

        beppe.initializeRoom();
        beppe.startGame();



        ResourceCard c1 = (ResourceCard) beppe.getGame().getResourceDeck().getCardById(24);
        GoldCard c2 = (GoldCard) beppe.getGame().getGoldDeck().getCardById(42);
        ResourceCard c3 = (ResourceCard)beppe.getGame().getResourceDeck().getCardById(7);
        ResourceCard c4 = (ResourceCard)beppe.getGame().getResourceDeck().getCardById(3);

        PlayingField gigi = beppe.getPlayerByName("hamingway").getPlayerField();
        gigi.




    }*/
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
                    return null;
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

 /*           Position x6 = new Position (-2, 0);
            Position x7 = new Position (-2, -2);
            Position x8 = new Position (-2, 2);
            Position x9 = new Position (0, 2);
            Position x10 = new Position (3, 1);

            assert(free.contains(x7));
            assert(free.contains(x8));


            assert(free.contains(x9));*/
        }
}