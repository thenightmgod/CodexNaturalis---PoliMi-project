package it.polimi.ingsw.View.GUI.GUIController.ScoreBoard;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.View.GUI.GUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

public class ScoreBoard {
    private GUI gui;
    private LinkedHashMap<String, Integer> points;
    private HashMap<PlayerColor, ImageView> placeholders;
    private Set<ScoreBoardPosition> occupiedPositions;

    public ScoreBoard(LinkedHashMap<String, Integer> points) {
        this.points = points;
        this.placeholders = createPlaceholders();
        this.occupiedPositions = new HashSet<>();
        updatePlaceholders();
    }

    private HashMap<PlayerColor, ImageView> createPlaceholders() {
        HashMap<PlayerColor, ImageView> placeholders = new HashMap<>();
        int numPlayers = points.size();
        PlayerColor[] playerColors = PlayerColor.values();

        for (int i = 0; i < numPlayers; i++) {
            PlayerColor color = playerColors[i];
            ImageView placeholder = new ImageView(loadImage("/view/MyCodexNaturalisPhotos/CODEX_pion_" + color.name().toLowerCase() + ".png"));
            placeholder.setFitWidth(50);
            placeholder.setFitHeight(50);
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
        positions.add(new ScoreBoardPosition(145, 612));  // position 0
        positions.add(new ScoreBoardPosition(225, 621));  // position 1
        positions.add(new ScoreBoardPosition(307, 612));
        positions.add(new ScoreBoardPosition(340,543));
        positions.add(new ScoreBoardPosition(263,549)); //4

        positions.add(new ScoreBoardPosition(174,551));
        positions.add(new ScoreBoardPosition(101, 552));
        positions.add(new ScoreBoardPosition(105, 481)); //7
        positions.add(new ScoreBoardPosition(178,481));
        positions.add(new ScoreBoardPosition(261,481));
        positions.add(new ScoreBoardPosition(347,480)); //10
        positions.add(new ScoreBoardPosition(347,411)); //11
        positions.add(new ScoreBoardPosition(267,399));
        positions.add(new ScoreBoardPosition(188,400));
        positions.add(new ScoreBoardPosition(99,398));

        positions.add(new ScoreBoardPosition(105,325)); //15
        positions.add(new ScoreBoardPosition(186,321));
        positions.add(new ScoreBoardPosition(259,324));
        positions.add(new ScoreBoardPosition(341,321));

        positions.add(new ScoreBoardPosition(338,253)); //19
        positions.add(new ScoreBoardPosition(225,228));
        positions.add(new ScoreBoardPosition(106,256)); //21
        positions.add(new ScoreBoardPosition(105,184));

        positions.add(new ScoreBoardPosition(103,112));
        positions.add(new ScoreBoardPosition(153,55));
        positions.add(new ScoreBoardPosition(222,35)); //25
        positions.add(new ScoreBoardPosition(298,55));
        positions.add(new ScoreBoardPosition(343,119));
        positions.add(new ScoreBoardPosition(340,184)); //28
        positions.add(new ScoreBoardPosition(225,125));
        return positions;
    }

    private ScoreBoardPosition calculatePosition(int points) {
        List<ScoreBoardPosition> positions = generatePositions();
        if (points < positions.size()) {
            return positions.get(points);
        } else {
            System.out.println("Points exceed predefined positions. Defaulting to last position.");
            return positions.get(positions.size() - 1);
        }
    }

    private void movePlaceholderToPosition(ImageView placeholder, ScoreBoardPosition position) {
        double x = position.getX();
        double y = position.getY();

        while (isPositionOccupied(x, y)) {
            x += 10;
            y += 10;
        }

        occupiedPositions.add(new ScoreBoardPosition(x, y));
        placeholder.setLayoutX(x);
        placeholder.setLayoutY(y);
    }

    private boolean isPositionOccupied(double x, double y) {
        for (ScoreBoardPosition pos : occupiedPositions) {
            if (pos.getX() == x && pos.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public HashMap<PlayerColor, ImageView> getPlaceholders() {
        return placeholders;
    }

    public void updatePlaceholders() {
        PlayerColor[] colors = {PlayerColor.RED, PlayerColor.YELLOW, PlayerColor.BLUE, PlayerColor.GREEN};
        int index = 0;
        occupiedPositions.clear();

        for (String player : points.keySet()) {
            if (index >= colors.length) break;
            int playerPoints = points.get(player);
            if(playerPoints > 29) {
                playerPoints=29;
            }
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