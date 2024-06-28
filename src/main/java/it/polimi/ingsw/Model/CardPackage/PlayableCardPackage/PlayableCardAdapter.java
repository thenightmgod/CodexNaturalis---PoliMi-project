package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;
import com.google.gson.*;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;

import java.lang.reflect.Type;
/**
 * This class is a custom adapter for the Gson library to handle the serialization and deserialization of PlayableCard objects.
 * It implements the JsonDeserializer and JsonSerializer interfaces provided by Gson.
 * The class overrides the deserialize and serialize methods to provide custom logic for converting between PlayableCard objects and JSON.
 * This is necessary because PlayableCard is a superclass with multiple subclasses (ResourceCard, GoldCard, StartCard), and Gson needs to know how to handle each one.
 */
public class PlayableCardAdapter implements JsonDeserializer<PlayableCard>, JsonSerializer<PlayableCard> {
    /**
     * Custom deserialization logic for PlayableCard objects.
     * This method determines the specific type of PlayableCard (ResourceCard, GoldCard, StartCard) based on the "Id" field in the JSON,
     * and then deserializes the JSON into an object of that type.
     *
     * @param json The JsonElement to be deserialized.
     * @param typeOfT The type of the Object to deserialize to.
     * @param context Context for deserialization.
     * @return The deserialized PlayableCard.
     * @throws JsonParseException If json is not in the expected format of JsonObject.
     */
    @Override
    public PlayableCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement idElement = jsonObject.get("Id");

        if(idElement != null) {

            int id = idElement.getAsInt();
            JsonArray backResArray = jsonObject.getAsJsonArray("BackRes");
            boolean[] backRes = new boolean[Resources.values().length];
            for (int i = 0; i < backResArray.size(); i++) {
                backRes[i] = backResArray.get(i).getAsBoolean();            }

            if (id >= 1 && id <= 40) {
                ResourceCard resourceCard = context.deserialize(json, ResourceCard.class);
                resourceCard.setId(id);
                resourceCard.setColor(CardColor.valueOf(jsonObject.get("Color").getAsString()));
                resourceCard.setPoints(jsonObject.get("Points").getAsInt());
                resourceCard.setCheckk(jsonObject.get("Check").getAsBoolean());
                return resourceCard;
            } else if (id > 40 && id <= 80) {
                GoldCard goldCard = context.deserialize(json, GoldCard.class);
                goldCard.setId(id);
                JsonArray requirementsArray = jsonObject.getAsJsonArray("requirements");
                int[] requirements = new int[requirementsArray.size()];
                for (int i = 0; i < requirementsArray.size(); i++) {
                    requirements[i] = requirementsArray.get(i).getAsInt();
                }
                goldCard.setRequirements(requirements);
                goldCard.setPointsCondition(PointsCondition.valueOf(jsonObject.get("PointsC").getAsString()));

                // Deserialize properties of ResourceCard
                goldCard.setColor(CardColor.valueOf(jsonObject.get("Color").getAsString()));
                goldCard.setPoints(jsonObject.get("Points").getAsInt());
                goldCard.setCheckk(jsonObject.get("Check").getAsBoolean());
                return goldCard;
            } else {
                StartCard startCard = context.deserialize(json, StartCard.class);
                startCard.setId(id);
                JsonArray backCornersArray = jsonObject.getAsJsonArray("BackCorners");
                for (JsonElement cornerElement : backCornersArray) {
                    JsonObject cornerObject = cornerElement.getAsJsonObject();
                    try {
                        Resources res = Resources.valueOf(cornerObject.get("Res").getAsString());
                        Orientation orientation = Orientation.valueOf(cornerObject.get("Orient").getAsString());
                        Corner corner = new Corner(res, orientation);
                        startCard.addBackCorner(corner);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid resource value: " + cornerObject.get("Res"));
                    }
                }
                return startCard;
            }
        }
        return null;
    }
    /**
     * Custom serialization logic for PlayableCard objects.
     * This method determines the specific type of the PlayableCard (ResourceCard, GoldCard, StartCard),
     * and then serializes the object into JSON using the appropriate logic for that type.
     *
     * @param src The PlayableCard to be serialized.
     * @param typeOfSrc The actual type of the source object.
     * @param context Context for serialization.
     * @return A JsonElement corresponding to the specified PlayableCard.
     */
    @Override
    public JsonElement serialize(PlayableCard src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        System.out.println(src.getId());

        JsonArray backResArray = new JsonArray();
        for (Resources res : src.getBackRes()) {
            backResArray.add(res.toString());
        }
        jsonObject.add("BackRes", backResArray);

        //switch da fare sull'id forse
        switch (src) {
            case GoldCard goldCard -> {
                JsonArray requirementsArray = new JsonArray();
                for (int requirement : goldCard.getRequirements()) {
                    requirementsArray.add(requirement);
                }
                jsonObject.add("requirements", requirementsArray);
                jsonObject.addProperty("PointsC", goldCard.getPointsCondition().toString());

                JsonObject resourceCardObject = context.serialize(goldCard, ResourceCard.class).getAsJsonObject();
                jsonObject.add("ResourceCard", resourceCardObject);
            }
            case ResourceCard resourceCard -> {
                jsonObject.addProperty("Color", resourceCard.getColor().toString());
                jsonObject.addProperty("Points", resourceCard.getPoints());
                jsonObject.addProperty("Check", resourceCard.getCheck());
            }
            case StartCard startCard -> {
                JsonArray backCornersArray = new JsonArray();
                for (Orientation orientation : Orientation.values()) {
                    Corner corner = startCard.getBackCorner(orientation);
                    JsonObject cornerObject = new JsonObject();
                    cornerObject.addProperty("Res", corner.getRes().toString());
                    cornerObject.addProperty("Orient", corner.getOrientation().toString());
                    backCornersArray.add(cornerObject);
                }
                jsonObject.add("BackCorners", backCornersArray);
            }
            default -> throw new IllegalStateException("Unexpected value: " + src);
        }
        return jsonObject;
    }

}