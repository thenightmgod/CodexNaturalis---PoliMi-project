package it.polimi.ingsw.Model.PlayerPackage;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.ABSENT;
import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;

class PlayingFieldTest {
    private static Position x;
    private static PlayingField prova;
    private static Position z;
    private static Corner primoHR, primoHL, primoLR, primoLL, secondoHR, secondoHL, secondoLR, secondoLL, terzoHR, terzoHL, terzoLL,terzoLR, quartoHR,quartoHL,quartoLR,quartoLL,quintoHR,quintoHL,quintoLL,quintoLR,sestoHR,sestoLL,sestoHL,sestoLR;
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
    @Test
    void updateFreePositionsTest(){
        LinkedList<Corner> CornersPrimo = new LinkedList<>();
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
        primoRes[0] = true;
        primoRes[1] = false;
        primoRes[2] = false;
        primoRes[3] = false;
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
    }
    @Test
    void getPosFromCornerTest(){
        z= prova.getPosFromCorner(x, Orientation.LR);
        x.setX(2);
        x.setY(0);

        assert(x.equals(z));
    }

}