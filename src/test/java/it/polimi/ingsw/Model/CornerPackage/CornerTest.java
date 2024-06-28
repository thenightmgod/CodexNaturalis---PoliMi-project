package it.polimi.ingsw.Model.CornerPackage;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

    public class CornerTest {

        @Test
        void getCoveredTest(){
            Corner x = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
            x.setCovered(true);

            Corner y = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
            y.setCovered(true);

            assert(x.getCovered());
            assert(x.equals(y));
        }

        private byte[] serializeObject(Object obj) throws IOException {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bos)) {
                out.writeObject(obj);
                return bos.toByteArray();
            }
        }

        private Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 ObjectInputStream in = new ObjectInputStream(bis)) {
                return in.readObject();
            }
        }

        @Test
        public void testSerializationAndDeserializationWithCornerState() throws IOException, ClassNotFoundException {
            Corner original = new Corner(CornerState.EMPTY, Orientation.HR);

            byte[] serializedData = serializeObject(original);

            Corner deserialized = (Corner) deserializeObject(serializedData);

            assertNotNull(deserialized.getRes());
            assertEquals(CornerState.EMPTY, deserialized.getRes());
            assertEquals(Orientation.HR, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSerializationAndDeserializationWithResources() throws IOException, ClassNotFoundException {
            Corner original = new Corner(Resources.ANIMAL_KINGDOM, Orientation.LL);

            byte[] serializedData = serializeObject(original);

            Corner deserialized = (Corner) deserializeObject(serializedData);

            int x = 5;

            assertNotNull(deserialized.getRes());
            assertEquals(Resources.ANIMAL_KINGDOM, deserialized.getRes());
            assertEquals(Orientation.LL, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSerializationAndDeserializationWithResources2() throws IOException, ClassNotFoundException {
            ResourceDeck deck = new ResourceDeck();
            ResourceCard card = (ResourceCard) deck.getCardById(1);
            Corner original = card.getCorner(Orientation.HL);

            byte[] serializedData = serializeObject(original);

            Corner deserialized = (Corner) deserializeObject(serializedData);

            int x = 5;

            assertEquals(Resources.FUNGI_KINGDOM, deserialized.getRes());
        }

        @Test
        public void testSerializationAndDeserializationWithObjects() throws IOException, ClassNotFoundException {
            Corner original = new Corner(it.polimi.ingsw.Model.CornerPackage.Objects.MANUSCRIPT, Orientation.LR);

            byte[] serializedData = serializeObject(original);

            Corner deserialized = (Corner) deserializeObject(serializedData);

            assertNotNull(deserialized.getRes());
            assertEquals(it.polimi.ingsw.Model.CornerPackage.Objects.MANUSCRIPT, deserialized.getRes());
            assertEquals(Orientation.LR, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSerializationAndDeserializationWithNullRes() throws IOException, ClassNotFoundException {
            Corner original = new Corner(null, Orientation.HR);

            byte[] serializedData = serializeObject(original);

            Corner deserialized = (Corner) deserializeObject(serializedData);

            assertNull(deserialized.getRes());
            assertEquals(Orientation.HR, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSetCovered() throws IOException, ClassNotFoundException {
            Corner original = new Corner(CornerState.ABSENT, Orientation.LL);
            original.setCovered(true);

            byte[] serializedData = serializeObject(original);

            Corner deserialized = (Corner) deserializeObject(serializedData);

            assertTrue(deserialized.getCovered());
        }
    }
