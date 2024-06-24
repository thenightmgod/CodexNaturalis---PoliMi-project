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