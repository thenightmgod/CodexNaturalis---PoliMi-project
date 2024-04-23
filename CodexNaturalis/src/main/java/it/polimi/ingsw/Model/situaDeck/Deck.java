package it.polimi.ingsw.Model.situaDeck;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Model.situaCard.Card;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.CompositionGoalCard;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.ObjectsGoalCard;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.ResourceGoalCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.GoldCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.StartCard;
import it.polimi.ingsw.Model.situaCorner.CardRes;
import it.polimi.ingsw.Model.situaCorner.CardResDeserializer;
import it.polimi.ingsw.Model.situaCorner.Resources;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;


public class Deck {
    private List<Card> cards;

    public Deck(String type) {
        String json = "src/main/Resources/"+type+"Card.json";
        cards = new LinkedList<>();
        Gson gson = new GsonBuilder().registerTypeAdapter(CardRes.class, new CardResDeserializer()).create();
        try {
            LinkedList<Objects> list = gson.fromJson(new FileReader(json), LinkedList.class);
            for (Object card : list){
                switch(type){
                    case "Resource":
                        this.cards.add(gson.fromJson(new FileReader(json), ResourceCard.class));
                        break;
                    case "Start":
                        this.cards.add(gson.fromJson(new FileReader(json), StartCard.class));
                        break;
                    case "Gold":
                        this.cards.add(gson.fromJson(new FileReader(json), GoldCard.class));
                        break;
                    case "CompositionGoal":
                        this.cards.add(gson.fromJson(new FileReader(json), CompositionGoalCard.class));
                        break;
                    case "ObjectsGoal":
                        this.cards.add(gson.fromJson(new FileReader(json), ObjectsGoalCard.class));
                        break;
                    case "ResourceGoal":
                        this.cards.add(gson.fromJson(new FileReader(json), ResourceGoalCard.class));
                        break;

                }
            }

        }
        catch (Exception ignored) {}

    }

    public List<Card> getCards() {
        return cards;
    }

    public int getSize(){
        return cards.size();
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public void add(GoalCard card){
        cards.add(card);
    }
    //si pesca attraverso il controller così da non dover gestire
}

