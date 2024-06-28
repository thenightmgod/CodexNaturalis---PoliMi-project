package it.polimi.ingsw.Model.DeckPackage;

import com.google.gson.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ResourceGoalCard;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/**
 * The GoalCardLoader class is responsible for loading goal cards from JSON files.
 * It uses the Gson library to parse JSON files and create GoalCard objects.
 * The class provides a static method to load all goal cards into a map where the key is the card's ID.
 */
public class GoalCardLoader {
    /**
     * Loads goal cards from JSON files and returns a map of them.
     * The map's keys are the IDs of the goal cards, and the values are the GoalCard objects.
     * The method reads JSON files for ResourceGoalCard, ObjectsGoalCard, and CompositionGoalCard types.
     * @return A map of goal cards with their IDs as keys.
     * @throws IOException If there is an error reading the JSON files.
     */
    public static Map<Integer, GoalCard> loadGoalCards() throws IOException {
        Map<Integer, GoalCard> goalCards = new HashMap<>();
        Gson gson = new Gson();

        JsonArray resourceGoalCards = JsonParser.parseReader(new InputStreamReader(GoalCardLoader.class.getResourceAsStream("/ResourceGoalCard.json"))).getAsJsonArray();
        for (JsonElement element : resourceGoalCards) {
            GoalCard card = gson.fromJson(element, ResourceGoalCard.class);
            goalCards.put(card.getId(), card);
        }

        JsonArray objectsGoalCards = JsonParser.parseReader(new InputStreamReader(GoalCardLoader.class.getResourceAsStream("/ObjectsGoalCard.json"))).getAsJsonArray();
        for (JsonElement element : objectsGoalCards) {
            GoalCard card = gson.fromJson(element, ObjectsGoalCard.class);
            goalCards.put(card.getId(), card);
        }

        JsonArray compositionGoalCards = JsonParser.parseReader(new InputStreamReader(GoalCardLoader.class.getResourceAsStream("/CompositionGoalCard.json"))).getAsJsonArray();
        for (JsonElement element : compositionGoalCards) {
            GoalCard card = gson.fromJson(element, CompositionGoalCard.class);
            goalCards.put(card.getId(), card);
        }

        return goalCards;
    }
}