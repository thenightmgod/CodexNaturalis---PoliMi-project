package it.polimi.ingsw.View.GUI.GUIController.ScoreBoard;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ScoreBoard {
    private LinkedHashMap<String, Integer> points;
    private HashMap<PlayerColor, ImageView> placeholders;

    public ScoreBoard(LinkedHashMap<String, Integer> points) {
        this.points = points;
        this.placeholders = createPlaceholders();
        updatePlaceholders();
    }

    private HashMap<PlayerColor, ImageView> createPlaceholders() {
        HashMap<PlayerColor, ImageView> placeholders = new HashMap<>();


        int numPlayers = points.size();
        PlayerColor[] playerColors = PlayerColor.values();

        // Create placeholders for the number of players
        for (int i = 0; i < numPlayers; i++) {
            PlayerColor color = playerColors[i];
            ImageView placeholder = new ImageView(loadImage("/view/MyCodexNaturalisPhotos/CODEX_pion_" + color.name().toLowerCase() + ".png"));
            placeholder.setFitWidth(30);  // Set appropriate width
            placeholder.setFitHeight(30); // Set appropriate height
            placeholder.setPreserveRatio(true);
            placeholders.put(color, placeholder);
        }

        return placeholders;
    }

    private Image loadImage(String imagePath) {
        URL resourceUrl = getClass().getResource(imagePath);
        if (resourceUrl == null) {
            System.out.println("Resource not found: " + imagePath);
            return null;
        }
        return new Image(resourceUrl.toString());
    }
    private List<ScoreBoardPosition> generatePositions() {
        List<ScoreBoardPosition> positions = new ArrayList<>();
        // Esempio di posizioni, devono essere precise e basate sul tuo layout
        positions.add(new ScoreBoardPosition(85, 531));  // Posizione 0
        positions.add(new ScoreBoardPosition(155, 529)); // Posizione 1
        // ... aggiungi tutte le posizioni fino a 29
        return positions;
    }

    private ScoreBoardPosition calculatePosition(int points) {
        List<ScoreBoardPosition> positions = generatePositions();
        return positions.get(points);
    }

    private void movePlaceholderToPosition(ImageView placeholder, ScoreBoardPosition position) {
        placeholder.setLayoutX(position.getX());
        placeholder.setLayoutY(position.getY());
    }

    public HashMap<PlayerColor, ImageView> getPlaceholders() {
        return placeholders;
    }

    public void updatePlaceholders() {
        PlayerColor[] colors = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW};
        int index = 0;
        for (String player : points.keySet()) {
            if (index >= colors.length) break;
            int playerPoints = points.get(player);
            ScoreBoardPosition position = calculatePosition(playerPoints);
            ImageView placeholder = placeholders.get(colors[index]);
            if (placeholder != null) {
                movePlaceholderToPosition(placeholder, position);
            } else {
                System.out.println("Placeholder for color " + colors[index] + " is null.");
            }
            index++;
        }
    }
}
