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

public class CardResSerializer implements JsonSerializer<CardRes>  {

    @Override
    public JsonElement serialize(CardRes src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
