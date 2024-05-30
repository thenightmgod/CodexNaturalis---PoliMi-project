package it.polimi.ingsw.Model.DeckPackage;

import static it.polimi.ingsw.Model.CornerPackage.CornerState.EMPTY;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Objects;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
/**
 * This test first creates all the decks that will be used in a game, and then checks if some of the main methods
 * that use the Deck Class work.
 */
class GoldDeckTest {
    @BeforeEach
    public void setup(){
        Corner HR = new Corner(EMPTY, Orientation.HR);
    }
    @Test
    void golddeckTest(){
        ResourceDeck rd = new ResourceDeck();
        LinkedList<Card> cards = rd.getCards();

        StartDeck sd = new StartDeck();

        GoldDeck gd = new GoldDeck();

        CompositionGoalDeck cgd = new CompositionGoalDeck();

        ObjectsGoalDeck ogd = new ObjectsGoalDeck();

        ResourceGoalDeck rgd = new ResourceGoalDeck();

        Player p1 = new Player("Lazz", PlayerColor.RED);
        gd.giveCard(p1, 0);
        LinkedList<PlayableCard> h1 = p1.getHand();
        assert(h1.get(0).getCorner(Orientation.LR).getRes().equals(Objects.QUILL));
    }
}