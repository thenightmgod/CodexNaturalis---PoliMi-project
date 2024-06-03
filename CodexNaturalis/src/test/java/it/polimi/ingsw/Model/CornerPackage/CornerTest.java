package it.polimi.ingsw.Model.CornerPackage;

/*import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * This test is composed of an initialization of two corners and tests if the corner gets covered
 */

/*class CornerTest {
    @Test
    void getCoveredTest(){
        Corner x = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        x.setCovered(true);

        Corner y = new Corner(Resources.ANIMAL_KINGDOM, Orientation.HR);
        y.setCovered(true);

        assert(x.getCovered());
        assert(x.equals(y));
    }
    package it.polimi.ingsw.Model.CornerPackage;
    */

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

    public class CornerTest {

        // A helper method to serialize an object to a byte array
        private byte[] serializeObject(Object obj) throws IOException {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bos)) {
                out.writeObject(obj);
                return bos.toByteArray();
            }
        }

        // A helper method to deserialize an object from a byte array
        private Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 ObjectInputStream in = new ObjectInputStream(bis)) {
                return in.readObject();
            }
        }

        @Test
        public void testSerializationAndDeserializationWithCornerState() throws IOException, ClassNotFoundException {
            Corner original = new Corner(CornerState.EMPTY, Orientation.HR);

            // Serialize the object
            byte[] serializedData = serializeObject(original);

            // Deserialize the object
            Corner deserialized = (Corner) deserializeObject(serializedData);

            // Verify the object was deserialized correctly
            assertNotNull(deserialized.getRes());
            assertEquals(CornerState.EMPTY, deserialized.getRes());
            assertEquals(Orientation.HR, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSerializationAndDeserializationWithResources() throws IOException, ClassNotFoundException {
            Corner original = new Corner(Resources.ANIMAL_KINGDOM, Orientation.LL);

            // Serialize the object
            byte[] serializedData = serializeObject(original);

            // Deserialize the object
            Corner deserialized = (Corner) deserializeObject(serializedData);

            int x = 5;

            // Verify the object was deserialized correctly
            assertNotNull(deserialized.getRes());
            assertEquals(Resources.ANIMAL_KINGDOM, deserialized.getRes());
            assertEquals(Orientation.LL, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSerializationAndDeserializationWithObjects() throws IOException, ClassNotFoundException {
            Corner original = new Corner(it.polimi.ingsw.Model.CornerPackage.Objects.MANUSCRIPT, Orientation.LR);

            // Serialize the object
            byte[] serializedData = serializeObject(original);

            // Deserialize the object
            Corner deserialized = (Corner) deserializeObject(serializedData);

            // Verify the object was deserialized correctly
            assertNotNull(deserialized.getRes());
            assertEquals(it.polimi.ingsw.Model.CornerPackage.Objects.MANUSCRIPT, deserialized.getRes());
            assertEquals(Orientation.LR, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSerializationAndDeserializationWithNullRes() throws IOException, ClassNotFoundException {
            Corner original = new Corner(null, Orientation.HR);

            // Serialize the object
            byte[] serializedData = serializeObject(original);

            // Deserialize the object
            Corner deserialized = (Corner) deserializeObject(serializedData);

            // Verify the object was deserialized correctly
            assertNull(deserialized.getRes());
            assertEquals(Orientation.HR, deserialized.getOrientation());
            assertFalse(deserialized.getCovered());
        }

        @Test
        public void testSetCovered() throws IOException, ClassNotFoundException {
            Corner original = new Corner(CornerState.ABSENT, Orientation.LL);
            original.setCovered(true);

            // Serialize the object
            byte[] serializedData = serializeObject(original);

            // Deserialize the object
            Corner deserialized = (Corner) deserializeObject(serializedData);

            // Verify the object was deserialized correctly
            assertTrue(deserialized.getCovered());
        }
    }
