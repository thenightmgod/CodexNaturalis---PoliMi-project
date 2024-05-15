package it.polimi.ingsw.Model.CornerPackage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * This test is composed of an initialization of two corners and tests if the corner gets covered
 */

class CornerTest {
    @Test
    void getCoveredTest(){
        Corner x = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        x.setCovered(true);

        Corner y = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        y.setCovered(true);

        assert(x.getCovered());
        assert(x.equals(y));
    }
}