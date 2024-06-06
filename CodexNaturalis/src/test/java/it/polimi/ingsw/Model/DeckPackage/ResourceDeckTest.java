package it.polimi.ingsw.Model.DeckPackage;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
/**
 * This test first creates the resource deck, and then checks if some of the main methods that use this Class work.
 */
class ResourceDeckTest {
    @Test
    void ResourceDeck(){
        ResourceDeck rd = new ResourceDeck();
        Player p1 = new Player("Lazz", PlayerColor.RED);
        rd.giveCard(p1, 0);
        LinkedList<PlayableCard> h1 = p1.getHand();
        assert(h1.get(0).getCorner(Orientation.LL).getRes().equals(Resources.FUNGI_KINGDOM));
    }
}