package it.polimi.ingsw;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;
import it.polimi.ingsw.Model.situaCorner.CardResAdapter;
import it.polimi.ingsw.Model.situaCorner.CardRes;
import it.polimi.ingsw.Model.Deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.Model.situaCorner.Orientation;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Carica le ResourceCard dal file JSON
        List<ResourceCard> resourceCards = loadResourceCardsFromFile("src/main/Resources/ResourceCard.json");

        // Crea un mazzo utilizzando le ResourceCard caricate
        Deck deck = new Deck(resourceCards);

        // Stampa il contenuto del deck
        System.out.println("Contenuto del deck:");
        for (ResourceCard card : deck.getCards()) {
            System.out.println("ResourceCard ID: " + card.getId());
            System.out.println("Color: " + card.getColor());
            System.out.println("Points: " + card.getPoints());
            System.out.println("BackRes: " + card.getBackRes());
            System.out.println("Corners:");
            System.out.println(card.getCorner(Orientation.HR));
            System.out.println(card.getCorner(Orientation.HL));
            System.out.println(card.getCorner(Orientation.LR));
            System.out.println(card.getCorner(Orientation.LL));
            System.out.println();
        }
    }

    private static List<ResourceCard> loadResourceCardsFromFile(String filePath) {
        try {
            // Creazione di un FileReader per leggere il file JSON
            FileReader reader = new FileReader(filePath);

            // Crea un GsonBuilder per configurare Gson con il TypeAdapter personalizzato
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(CardRes.class, new CardResAdapter())
                    .create();

            // Deserializzazione del JSON in una lista di oggetti ResourceCard utilizzando Gson
            Type listType = new TypeToken<List<ResourceCard>>(){}.getType();
            List<ResourceCard> resourceCards = gson.fromJson(reader, listType);

            // Chiudi il FileReader
            reader.close();

            return resourceCards;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}