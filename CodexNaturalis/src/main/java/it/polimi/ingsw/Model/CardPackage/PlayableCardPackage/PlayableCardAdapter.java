package it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;
import com.google.gson.*;
import it.polimi.ingsw.Model.CornerPackage.Corner;
import it.polimi.ingsw.Model.CornerPackage.Orientation;
import it.polimi.ingsw.Model.CornerPackage.Resources;

import java.lang.reflect.Type;

public class PlayableCardAdapter implements JsonDeserializer<PlayableCard>, JsonSerializer<PlayableCard> {

    @Override
    public PlayableCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement idElement = jsonObject.get("id");

        if (idElement == null || idElement.isJsonNull()) {
            throw new JsonParseException("Missing 'id' field in JSON data");
        }

        int id = idElement.getAsInt();

        JsonArray backResArray = jsonObject.getAsJsonArray("backRes");
        boolean[] backRes = new boolean[Resources.values().length];
        for (int i = 0; i < backResArray.size(); i++) {
            backRes[i] = Resources.valueOf(backResArray.get(i).getAsString()) != null;
        }

        if (id >= 1 && id <= 40) {
            ResourceCard resourceCard = context.deserialize(json, ResourceCard.class);
            resourceCard.setId(id);
            resourceCard.setColor(CardColor.valueOf(jsonObject.get("color").getAsString()));
            resourceCard.setPoints(jsonObject.get("points").getAsInt());
            resourceCard.setCheckk(jsonObject.get("check").getAsBoolean());
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
            goldCard.setPointsCondition(PointsCondition.valueOf(jsonObject.get("pointsCondition").getAsString()));

            // Deserialize properties of ResourceCard
            JsonObject resourceCardObject = jsonObject.getAsJsonObject("resourceCard");
            goldCard.setColor(CardColor.valueOf(resourceCardObject.get("color").getAsString()));
            goldCard.setPoints(resourceCardObject.get("points").getAsInt());
            goldCard.setCheckk(resourceCardObject.get("check").getAsBoolean());
            return goldCard;
        } else {
            StartCard startCard = context.deserialize(json, StartCard.class);
            startCard.setId(id);
            JsonArray backCornersArray = jsonObject.getAsJsonArray("backCorners");
            for (JsonElement cornerElement : backCornersArray) {
                JsonObject cornerObject = cornerElement.getAsJsonObject();
                Resources res = Resources.valueOf(cornerObject.get("res").getAsString());
                Orientation orientation = Orientation.valueOf(cornerObject.get("orientation").getAsString());
                Corner corner = new Corner(res, orientation);
                startCard.addBackCorner(corner);
            }
            return startCard;
        }
    }

    @Override
    public JsonElement serialize(PlayableCard src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", src.getId());
        JsonArray backResArray = new JsonArray();
        for (Resources res : src.getBackRes()) {
            backResArray.add(res.toString());
        }
        jsonObject.add("backRes", backResArray);

        //switch da fare sull'id forse
        switch (src) {
            case GoldCard goldCard -> {
                JsonArray requirementsArray = new JsonArray();
                for (int requirement : goldCard.getRequirements()) {
                    requirementsArray.add(requirement);
                }
                jsonObject.add("requirements", requirementsArray);
                jsonObject.addProperty("pointsCondition", goldCard.getPointsCondition().toString());

                JsonObject resourceCardObject = context.serialize(goldCard, ResourceCard.class).getAsJsonObject();
                jsonObject.add("resourceCard", resourceCardObject);
            }
            case ResourceCard resourceCard -> {
                jsonObject.addProperty("color", resourceCard.getColor().toString());
                jsonObject.addProperty("points", resourceCard.getPoints());
                jsonObject.addProperty("check", resourceCard.getCheck());
            }
            case StartCard startCard -> {
                JsonArray backCornersArray = new JsonArray();
                for (Orientation orientation : Orientation.values()) {
                    Corner corner = startCard.getBackCorner(orientation);
                    JsonObject cornerObject = new JsonObject();
                    cornerObject.addProperty("res", corner.getRes().toString());
                    cornerObject.addProperty("orientation", corner.getOrientation().toString());
                    backCornersArray.add(cornerObject);
                }
                jsonObject.add("backCorners", backCornersArray);
            }
            default -> throw new IllegalStateException("Unexpected value: " + src);
        }
        return jsonObject;
    }

}