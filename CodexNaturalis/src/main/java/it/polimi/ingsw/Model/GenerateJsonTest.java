package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.CardColor;
import it.polimi.ingsw.Model.situaCorner.*;
import static it.polimi.ingsw.Model.situaCorner.CornerState.ABSENT;
import it.polimi.ingsw.Model.situaCorner.CornerState;

public class GenerateJsonTest {
    public static void main(String[] args) {
        // Creazione di oggetti Corner di prova
        Corner corner1 = new Corner(CornerState.ABSENT, Orientation.HR);
        Corner corner2 = new Corner(CornerState.EMPTY, Orientation.HL);
        Corner corner3 = new Corner(CornerState.ABSENT, Orientation.LR);
        Corner corner4 = new Corner(CornerState.EMPTY, Orientation.LL);

        // Creazione di una lista di angoli
        LinkedList<Corner> corners = new LinkedList<>();
        corners.add(corner1);
        corners.add(corner2);
        corners.add(corner3);
        corners.add(corner4);

        // Creazione di un oggetto ResourceCard di prova
        ResourceCard resourceCard = new ResourceCard(1, Resources.ANIMAL_KINGDOM, CardColor.RED, 5, corners);

        // Inizializzazione di Gson
        Gson gson = new Gson();

        // Convertire l'oggetto in una stringa JSON
        String json = gson.toJson(resourceCard);

        // Scrivere il JSON su un file
        try (FileWriter writer = new FileWriter("resource_card_example.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


