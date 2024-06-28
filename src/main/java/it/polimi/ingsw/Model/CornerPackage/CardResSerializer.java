package it.polimi.ingsw.Model.CornerPackage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import com.google.gson.JsonSerializer;
/**
 * This class is a custom serializer for the CardRes interface.
 * It implements the JsonSerializer interface provided by the Gson library.
 * The class is used to convert CardRes objects to JSON format.
 */
public class CardResSerializer implements JsonSerializer<CardRes>  {
    /**
     * This method is used to serialize a CardRes object into a JsonElement.
     * @param src The CardRes object that needs to be serialized.
     * @param typeOfSrc The specific genericized type of src.
     * @param context Context for serialization that can be used to serialize other objects.
     * @return A JsonElement corresponding to the specified CardRes.
     */
    @Override
    public JsonElement serialize(CardRes src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
