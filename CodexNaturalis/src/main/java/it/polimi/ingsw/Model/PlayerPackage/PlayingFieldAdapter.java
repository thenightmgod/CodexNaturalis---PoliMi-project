package it.polimi.ingsw.Model.PlayerPackage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * This class is a custom serializer and deserializer for the PlayingField class.
 * It implements JsonSerializer and JsonDeserializer interfaces provided by the Gson library.
 * The class is used to convert PlayingField objects to JSON format and vice versa.
 */
public class PlayingFieldAdapter implements JsonSerializer<PlayingField>, JsonDeserializer<PlayingField> {
    /**
     * This method is used to serialize a PlayingField object into a JsonElement.
     * @param src The PlayingField object that needs to be serialized.
     * @param typeOfSrc The specific genericized type of src.
     * @param context Context for serialization that can be used to serialize other objects.
     * @return A JsonElement corresponding to the specified PlayingField.
     */
    @Override
    public JsonElement serialize(PlayingField src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Field", context.serialize(src.getField()));
        jsonObject.add("FreePositions", context.serialize(src.getFreePositions()));
        return jsonObject;
    }
    /**
     * This method is used to deserialize a JsonElement into a PlayingField object.
     * @param json The JsonElement that needs to be deserialized.
     * @param typeOfT The specific genericized type of the object to be deserialized.
     * @param context Context for deserialization that can be used to deserialize other objects.
     * @return A PlayingField object corresponding to the specified JsonElement.
     * @throws JsonParseException If json is not in the expected format of JsonObject.
     */
    @Override
    public PlayingField deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        PlayingField playingField = new PlayingField();

        Type fieldType = new TypeToken<LinkedHashMap<String, PlayableCard>>(){}.getType();
        LinkedHashMap<String, PlayableCard> field = context.deserialize(jsonObject.get("Field"), fieldType);

        LinkedHashMap<Position, PlayableCard> convertedField = new LinkedHashMap<>();
        for (Map.Entry<String, PlayableCard> entry : field.entrySet()) {
            String key = entry.getKey().replaceAll("[{}\"]", "");
            String[] parts = key.split(",");
            int x = Integer.parseInt(parts[0].split(":")[1].trim());
            int y = Integer.parseInt(parts[1].split(":")[1].trim());
            String faceString = parts[2].split(":")[1].trim();
            if (faceString.startsWith("\"") && faceString.endsWith("\"")) {
                faceString = faceString.substring(1, faceString.length() - 1);
            }
            FB face = FB.valueOf(faceString);
            convertedField.put(new Position(face, x, y), entry.getValue());
        }

        playingField.setField(convertedField);
        playingField.setFreePositions(context.deserialize(jsonObject.get("FreePositions"), new TypeToken<LinkedList<Position>>(){}.getType()));

        return playingField;
    }
}