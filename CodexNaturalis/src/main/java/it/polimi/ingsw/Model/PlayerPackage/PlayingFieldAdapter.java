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

public class PlayingFieldAdapter implements JsonSerializer<PlayingField>, JsonDeserializer<PlayingField> {

    @Override
    public JsonElement serialize(PlayingField src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Field", context.serialize(src.getField()));
        jsonObject.add("FreePositions", context.serialize(src.getFreePositions()));
        return jsonObject;
    }

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