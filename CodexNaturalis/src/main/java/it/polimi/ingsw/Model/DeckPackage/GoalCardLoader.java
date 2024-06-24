package it.polimi.ingsw.Model.DeckPackage;

import com.google.gson.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ResourceGoalCard;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GoalCardLoader {
    private static final String RESOURCE_GOAL_CARD_JSON = "src/main/resources/ResourceGoalCard.json";
    private static final String OBJECTS_GOAL_CARD_JSON = "src/main/resources/ObjectsGoalCard.json";
    private static final String COMPOSITION_GOAL_CARD_JSON = "src/main/resources/CompositionGoalCard.json";

    public static Map<Integer, GoalCard> loadGoalCards() throws IOException {
        Map<Integer, GoalCard> goalCards = new HashMap<>();
        Gson gson = new Gson();

        // Load ResourceGoalCard
        JsonArray resourceGoalCards = JsonParser.parseReader(new FileReader(RESOURCE_GOAL_CARD_JSON)).getAsJsonArray();
        for (JsonElement element : resourceGoalCards) {
            GoalCard card = gson.fromJson(element, ResourceGoalCard.class);
            goalCards.put(card.getId(), card);
        }

        // Load ObjectsGoalCard
        JsonArray objectsGoalCards = JsonParser.parseReader(new FileReader(OBJECTS_GOAL_CARD_JSON)).getAsJsonArray();
        for (JsonElement element : objectsGoalCards) {
            GoalCard card = gson.fromJson(element, ObjectsGoalCard.class);
            goalCards.put(card.getId(), card);
        }

        // Load CompositionGoalCard
        JsonArray compositionGoalCards = JsonParser.parseReader(new FileReader(COMPOSITION_GOAL_CARD_JSON)).getAsJsonArray();
        for (JsonElement element : compositionGoalCards) {
            GoalCard card = gson.fromJson(element, CompositionGoalCard.class);
            goalCards.put(card.getId(), card);
        }

        return goalCards;
    }
}