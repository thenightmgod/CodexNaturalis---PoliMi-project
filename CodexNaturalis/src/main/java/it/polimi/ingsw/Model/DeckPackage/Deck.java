package it.polimi.ingsw.Model.DeckPackage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Model.CardPackage.Card;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ResourceGoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.CornerPackage.CardRes;
import it.polimi.ingsw.Model.CornerPackage.CardResDeserializer;
import it.polimi.ingsw.Model.PlayerPackage.Player;

import java.io.FileReader;
import java.io.Serializable;
import java.util.*;


/**
 * Represents a deck of cards in the game. Each deck can contain various types of cards, such as Resource, Start, Gold, CompositionGoal, ObjectsGoal, or ResourceGoal cards.
 * The deck is initialized based on the type specified, loading card data from a corresponding JSON file.
 */
public class Deck implements Serializable {
    private LinkedList<Card> cards;

    /**
     * Constructs a new deck based on the specified type.
     * The deck is initialized by loading card data from a JSON file corresponding to the specified type.
     *
     * @param type The type of cards in the deck (e.g., "Resource", "Start", "Gold", "CompositionGoal", "ObjectsGoal", or "ResourceGoal").
     */

    public Deck(String type) {
        String json = "src/main/Resources/"+type+"Card.json";
        cards = new LinkedList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardRes.class, new CardResDeserializer())
                .create();
        try {
            LinkedList<Objects> list = gson.fromJson(new FileReader(json), LinkedList.class);

            for (Object card : list){
                switch(type){
                    case "Resource":
                        cards.add((gson.fromJson(card.toString(), ResourceCard.class)));
                        break;
                    case "Start":
                        cards.add((gson.fromJson(card.toString(), StartCard.class)));
                        break;
                    case "Gold":
                        cards.add((gson.fromJson(card.toString(), GoldCard.class)));
                        break;
                    case "CompositionGoal":
                        cards.add((gson.fromJson(card.toString(), CompositionGoalCard.class)));
                        break;
                    case "ObjectsGoal":
                        cards.add((gson.fromJson(card.toString(), ObjectsGoalCard.class)));
                        break;
                    case "ResourceGoal":
                        cards.add((gson.fromJson(card.toString(), ResourceGoalCard.class)));
                        break;

                }
            }

        }
        catch (Exception ignored) {}
    }
    /**
     * Retrieves the list of cards in the deck.
     *
     * @return The list of cards in the deck.
     */
    public LinkedList<Card> getCards() {
        return cards;
    }

    /**
     * Retrieves the size of the deck.
     *
     * @return The number of cards in the deck.
     */

    public Card getCardById(int id){
        for(int i=0; i<getSize(); i++){
            if(getCards().get(i).getId()==id){
                return getCards().get(i);
            }
        }
        return null;
    }

    public int getSize(){
        return cards.size();
    }

    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }


    /**
     * Adds a GoalCard to the deck.
     *
     * @param card The GoalCard to add to the deck.
     */
    public void add(Card card){
        cards.add(card);
    }

    /**
     * Gives a card from the deck to a player.
     * The card is removed from the deck and added to the player's hand.
     *
     * @param p The player to whom the card is given.
     * @param i The index of the card to give.
     */
    public void giveCard(Player p, int i){ //dovrebbe passare anche un int da 0 a 2
        Card card = getCards().get(i);
        getCards().remove(i);
        p.getHand().add((PlayableCard)card);
    }

    /**
     * Retrieves and removes the top card (goal card) from the deck.
     *
     * @return The top card (goal card) from the deck.
     */
    public Card getGoalCard(){
        Card c = cards.getFirst();
        cards.remove(c);
        return c;
    }
    //si pesca attraverso il controller così da non dover gestire differenze tra Resource e Gold deck
}

