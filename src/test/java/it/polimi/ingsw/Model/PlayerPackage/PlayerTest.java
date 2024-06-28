package it.polimi.ingsw.Model.PlayerPackage;


import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * This test is composed of an inizialization of all the elements needed to call the PlaceStartCard function
     * on the player, such as the card itself and a player. Then checks if the card is correctly placed.
     */
    @Test
    void placeStartcardTest(){
        Player player1 = new Player("Lazzarone", PlayerColor.RED);

        boolean primoRes[] = new boolean[4];  //inizio con la startcard con una backres (fungo)
        primoRes[0] = false;
        primoRes[1] = false;
        primoRes[2] = true;
        primoRes[3] = false;
        Corner primoHRfront = new Corner(EMPTY, Orientation.HR);
        Corner primoHLfront = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HL);
        Corner primoLRfront = new Corner(Resources.FUNGI_KINGDOM, Orientation.LR);
        Corner primoLLfront = new Corner(EMPTY, Orientation.LL);
        Corner primoHR = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        Corner primoHL = new Corner(Resources.PLANT_KINGDOM, Orientation.HL);
        Corner primoLR = new Corner(Resources.INSECT_KINGDOM, Orientation.LR);
        Corner primoLL = new Corner(Resources.FUNGI_KINGDOM, Orientation.LL);
        LinkedList<Corner> CornersPrimo = new LinkedList<>();
        LinkedList<Corner> CornersPrimofront = new LinkedList<>();
        CornersPrimo.addLast(primoHR);
        CornersPrimo.addLast(primoHL);
        CornersPrimo.addLast(primoLR);
        CornersPrimo.addLast(primoLL);

        CornersPrimofront.addLast(primoHRfront);
        CornersPrimofront.addLast(primoHLfront);
        CornersPrimofront.addLast(primoLRfront);
        CornersPrimofront.addLast(primoLLfront);

        StartCard startcard1 = new StartCard(5, primoRes, CornersPrimo, CornersPrimofront);

        player1.placeStartCard(startcard1, FB.FRONT);
        assertEquals(2, player1.getResourceCounter(Resources.FUNGI_KINGDOM));
        assertEquals(1, player1.getResourceCounter(Resources.ANIMAL_KINGDOM));
        assertEquals(Resources.ANIMAL_KINGDOM, startcard1.getCorner(Orientation.HL).getRes());

        boolean secondoRes[] = new boolean[4];  //inizio con la startcard con una backres (insect)
        secondoRes[0] = false;
        secondoRes[1] = false;
        secondoRes[2] = false;
        secondoRes[3] = true;
        Corner secondoHR = new Corner(Resources.PLANT_KINGDOM, Orientation.HR);
        Corner secondoHL = new Corner(Resources.FUNGI_KINGDOM, Orientation.HL);
        Corner secondoLR = new Corner(Resources.ANIMAL_KINGDOM, Orientation.LR);
        Corner secondoLL = new Corner(Resources.INSECT_KINGDOM, Orientation.LL);

        Corner secondoHRfront = new Corner(Resources.PLANT_KINGDOM, Orientation.HR);
        Corner secondoHLfront = new Corner(EMPTY, Orientation.HL);
        Corner secondoLRfront = new Corner(EMPTY, Orientation.LR);
        Corner secondoLLfront = new Corner(Resources.INSECT_KINGDOM, Orientation.LL);
        LinkedList<Corner> CornersSecondo = new LinkedList<>();
        LinkedList<Corner> CornersSecondofront = new LinkedList<>();
        CornersSecondo.addLast(secondoHR);
        CornersSecondo.addLast(secondoHL);
        CornersSecondo.addLast(secondoLR);
        CornersSecondo.addLast(secondoLL);
        CornersSecondofront.addLast(secondoHRfront);
        CornersSecondofront.addLast(secondoHLfront);
        CornersSecondofront.addLast(secondoLRfront);
        CornersSecondofront.addLast(secondoLLfront);

        StartCard startcard2 = new StartCard(6, secondoRes, CornersSecondo, CornersSecondofront);

        player1.placeStartCard(startcard2, FB.BACK);
        assertEquals(1, player1.getResourceCounter(Resources.PLANT_KINGDOM));
        assertEquals(1, player1.getResourceCounter(Resources.INSECT_KINGDOM));
        assertEquals(2, player1.getResourceCounter(Resources.ANIMAL_KINGDOM));
        assertEquals(3, player1.getResourceCounter(Resources.FUNGI_KINGDOM));


        Player player2 = new Player("Lazzaro", PlayerColor.GREEN);

        boolean terzoRes[] = new boolean[4];  //inizio con la startcard con due backres (fungo piu pianta)
        terzoRes[0] = true;
        terzoRes[1] = false;
        terzoRes[2] = true;
        terzoRes[3] = false;
        Corner terzoHR = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        Corner terzoHL = new Corner(Resources.PLANT_KINGDOM, Orientation.HL);
        Corner terzoLR = new Corner(Resources.INSECT_KINGDOM, Orientation.LR);
        Corner terzoLL = new Corner(Resources.FUNGI_KINGDOM, Orientation.LL);
        Corner terzoHRfront = new Corner(EMPTY, Orientation.HR);
        Corner terzoHLfront = new Corner(EMPTY, Orientation.HL);
        Corner terzoLRfront = new Corner(EMPTY, Orientation.LR);
        Corner terzoLLfront = new Corner(EMPTY, Orientation.LL);
        LinkedList<Corner> CornersTerzo = new LinkedList<>();
        LinkedList<Corner> CornersTerzofront = new LinkedList<>();
        CornersTerzo.addLast(terzoHR);
        CornersTerzo.addLast(terzoHL);
        CornersTerzo.addLast(terzoLR);
        CornersTerzo.addLast(terzoLL);
        CornersTerzofront.addLast(terzoHRfront);
        CornersTerzofront.addLast(terzoHLfront);
        CornersTerzofront.addLast(terzoLRfront);
        CornersTerzofront.addLast(terzoLLfront);


        StartCard startcard3 = new StartCard(7, terzoRes, CornersTerzo, CornersTerzofront);
        player2.placeStartCard(startcard3, FB.FRONT);
        assertEquals(EMPTY, startcard3.getCorner(Orientation.HL).getRes());
        assertEquals(1, player2.getResourceCounter(Resources.PLANT_KINGDOM));
        assertEquals(1, player2.getResourceCounter(Resources.FUNGI_KINGDOM));


        Player player3 = new Player("Lazzaronissimo", PlayerColor.GREEN);

        boolean quartoRes[] = new boolean[4];  //inizio con la startcard con tre backres (fungo piu pianta piu animal)
        quartoRes[0] = true;
        quartoRes[1] = true;
        quartoRes[2] = true;
        quartoRes[3] = false;
        Corner quartoHR = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        Corner quartoHL = new Corner(Resources.PLANT_KINGDOM, Orientation.HL);
        Corner quartoLR = new Corner(Resources.INSECT_KINGDOM, Orientation.LR);
        Corner quartoLL = new Corner(Resources.FUNGI_KINGDOM, Orientation.LL);
        Corner quartoHRfront = new Corner(EMPTY, Orientation.HR);
        Corner quartoHLfront = new Corner(EMPTY, Orientation.HL);
        Corner quartoLRfront = new Corner(ABSENT, Orientation.LR);
        Corner quartoLLfront = new Corner(ABSENT, Orientation.LL);
        LinkedList<Corner> CornersQuarto = new LinkedList<>();
        LinkedList<Corner> CornersQuartofront = new LinkedList<>();
        CornersQuarto.addLast(quartoHR);
        CornersQuarto.addLast(quartoHL);
        CornersQuarto.addLast(quartoLR);
        CornersQuarto.addLast(quartoLL);
        CornersQuartofront.addLast(quartoHRfront);
        CornersQuartofront.addLast(quartoHLfront);
        CornersQuartofront.addLast(quartoLRfront);
        CornersQuartofront.addLast(quartoLLfront);

        StartCard startcard4 = new StartCard(8, quartoRes, CornersQuarto, CornersQuartofront);
        player3.placeStartCard(startcard4, FB.FRONT);
        assertEquals(EMPTY, startcard4.getCorner(Orientation.HL).getRes());
        assertEquals(ABSENT, startcard4.getCorner(Orientation.LL).getRes());
        assertEquals(1, player3.getResourceCounter(Resources.PLANT_KINGDOM));
        assertEquals(1, player3.getResourceCounter(Resources.FUNGI_KINGDOM));
    }

    /**
     * This test is composed of an inizialization of all the elements needed to call the PlaceCard function
     * on the player, such as the card itself and a player. Then checks if the card is correctly placed.
     */
    @Test
    void placecardTest() {
        Player player1 = new Player("Lazzarone", PlayerColor.RED);

        boolean primoRes[] = new boolean[4];  //inizio con la startcard con una backres (fungo)
        primoRes[0] = false;
        primoRes[1] = true;
        primoRes[2] = false;
        primoRes[3] = false;
        Corner primoHR = new Corner(ABSENT, Orientation.HR);
        Corner primoHL = new Corner(EMPTY, Orientation.HL);
        Corner primoLR = new Corner(ABSENT, Orientation.LR);
        Corner primoLL = new Corner(Objects.MANUSCRIPT, Orientation.LL);
        LinkedList<Corner> CornersPrimo = new LinkedList<>();
        CornersPrimo.addLast(primoHR);
        CornersPrimo.addLast(primoHL);
        CornersPrimo.addLast(primoLR);
        CornersPrimo.addLast(primoLL);

        ResourceCard uno = new ResourceCard(5, primoRes, CardColor.BLUE, 3, CornersPrimo);
        Position x = new Position (FB.FRONT, 1, 1);
        player1.placeCard(uno, x);

        assertEquals(1, player1.getObjectCounter(Objects.MANUSCRIPT));

        boolean quartoRes[] = new boolean[4];
        quartoRes[0] = false;
        quartoRes[1] = false;
        quartoRes[2] = true;
        quartoRes[3] = false;
        Corner quartoHR = new Corner(ABSENT, Orientation.HR);
        Corner quartoHL = new Corner(EMPTY, Orientation.HL);
        Corner quartoLR = new Corner(Resources.FUNGI_KINGDOM, Orientation.LR);
        Corner quartoLL = new Corner(EMPTY, Orientation.LL);
        LinkedList<Corner> CornersQuarto = new LinkedList<>();
        CornersQuarto.addLast(quartoHR);
        CornersQuarto.addLast(quartoHL);
        CornersQuarto.addLast(quartoLR);
        CornersQuarto.addLast(quartoLL);

        ResourceCard quarto = new ResourceCard(5, quartoRes, CardColor.RED, 3, CornersQuarto);
        Position tooo = new Position (FB.FRONT, -1, 1);
        player1.placeCard(quarto, tooo);

        assertEquals(1, player1.getResourceCounter(Resources.FUNGI_KINGDOM));

        boolean secondoRes[] = new boolean[4];  //inizio con la startcard con una backres (fungo)
        secondoRes[0] = false;
        secondoRes[1] = true;
        secondoRes[2] = false;
        secondoRes[3] = false;
        Corner secondoHR = new Corner(ABSENT, Orientation.HR);
        Corner secondoHL = new Corner(EMPTY, Orientation.HL);
        Corner secondoLR = new Corner(Resources.ANIMAL_KINGDOM, Orientation.LR);
        Corner secondoLL = new Corner(EMPTY, Orientation.LL);
        LinkedList<Corner> CornersSecondo = new LinkedList<>();
        CornersSecondo.addLast(secondoHR);
        CornersSecondo.addLast(secondoHL);
        CornersSecondo.addLast(secondoLR);
        CornersSecondo.addLast(secondoLL);

        ResourceCard due = new ResourceCard(5, secondoRes, CardColor.BLUE, 3, CornersSecondo);
        Position z = new Position(FB.FRONT, 0 , 0);
        player1.placeCard(due , z);

        assertEquals( 0 , player1.getObjectCounter(Objects.MANUSCRIPT));
        assertEquals( 0 , player1.getResourceCounter(Resources.FUNGI_KINGDOM));

        boolean terzoRes[] = new boolean[4];
        terzoRes[0] = false;
        terzoRes[1] = false;
        terzoRes[2] = false;
        terzoRes[3] = true;
        Corner terzoHR = new Corner(ABSENT, Orientation.HR);
        Corner terzoHL = new Corner(EMPTY, Orientation.HL);
        Corner terzoLR = new Corner(Resources.ANIMAL_KINGDOM, Orientation.LR);
        Corner terzoLL = new Corner(EMPTY, Orientation.LL);
        LinkedList<Corner> CornersTerzo = new LinkedList<>();
        CornersTerzo.addLast(terzoHR);
        CornersTerzo.addLast(terzoHL);
        CornersTerzo.addLast(terzoLR);
        CornersTerzo.addLast(terzoLL);

        ResourceCard terzo = new ResourceCard(5, terzoRes, CardColor.PURPLE, 3, CornersTerzo);
        Position y = new Position (FB.BACK, 10, 10);
        player1.placeCard(terzo, y);

        assertEquals(1, player1.getResourceCounter(Resources.INSECT_KINGDOM));
    }

    /**
     * This test checks if the player's points are updated correctly.
     */
    @Test
    void pointsCalcTest() {
        GoldDeck gd = new GoldDeck();
        ResourceDeck rd = new ResourceDeck();
        Player p = new Player("ninaaaaa", PlayerColor.RED);

        Position x = new Position(0,0);
        Position y = new Position(-1,-1);
        Position z = new Position (1,1);

        Card c1 = rd.getCardById(1);
        Card c2 = gd.getCardById(43);
        Card c3 = rd.getCardById(31);

        p.placeCard((ResourceCard) c1, x);
        p.placeCard((ResourceCard) c3, z);
        p.placeCard((ResourceCard) c2, y);

        int j = p.getPointsCounter();
        assertEquals(1, j);
    }
}