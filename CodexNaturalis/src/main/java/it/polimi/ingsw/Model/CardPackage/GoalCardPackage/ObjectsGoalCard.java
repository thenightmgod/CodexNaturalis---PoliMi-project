package it.polimi.ingsw.Model.CardPackage.GoalCardPackage;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.CornerPackage.Objects;

/**
 * Represents the Goal Card which specific Goal is the achievement of a
 * certain number of one of the three objects on the player's playing field.
 */
public class ObjectsGoalCard extends GoalCard {
    private final int[] obj;
    //la prima posizione è la QUILL, la seconda è la INKWELL, la terza MANUSCRIPT,
    // quando le istanzio metto un '2' al primo posto se la carta obiettivo
    //richiede 2 QUILL ecc...

    /**
     * Constructs a new ObjectsGoalCard with the specified identifier, points, and object requirements.
     *
     * @param id The unique identifier of the ObjectsGoalCard.
     * @param points The number of points associated with the ObjectsGoalCard.
     * @param arrayInput An array representing the required number of objects for each type (Quill, Inkwell, Manuscript).
     *                   The array must be of size 3, where:
     *                   - The first position represents the number of Quill objects required.
     *                   - The second position represents the number of Inkwell objects required.
     *                   - The third position represents the number of Manuscript objects required.
     * @throws IllegalArgumentException if the size of the arrayInput is not equal to 3.
     */
    public ObjectsGoalCard(int id, int points, int[] arrayInput) {
        super(id, points);
        if (arrayInput.length != 3) {
            throw new IllegalArgumentException("L'array di input deve essere di dimensione 3");
        }
        obj = new int[3];
        // Copia gli elementi dall'array di input a obj
        for (int i = 0; i < 3; i++) {
            obj[i] = arrayInput[i];
        }
    }

    /**
     * Get the array with the info about how many and which objects the GoalCard requires to give points.
     *
     * @return the array with the info about how many and which objects the GoalCard requires to give points.
     */
    public int[] getObj() {
        return obj;
    }

    /**
     * Calculates the number of points earned by the specified player based on the achievement of Objects' Goals.
     *
     * @param player The player whose score due to the achievement of Objects' Goals we want to calculate.
     * @return The total number of points earned by the player.
     */
    public int pointsCalc(Player player) {
        int totalPoints = 0;

        if (this.getPoints() == 3) {
            int QuillCount = player.getObjectCounter(Objects.QUILL);
            int InkwellCount = player.getObjectCounter(Objects.INKWELL);
            int ManuscriptCount = player.getObjectCounter(Objects.MANUSCRIPT);
            int totalGroups = Math.min(QuillCount, Math.min(InkwellCount, ManuscriptCount));
            totalPoints = totalGroups * 3;
        } else if (obj[0] == 2) {
            int QuillCount = player.getObjectCounter(Objects.QUILL);
            totalPoints = QuillCount / 2;
            totalPoints = totalPoints*2;
        } else if (obj[1] == 2) {
            int InkwellCount = player.getObjectCounter(Objects.INKWELL);
            totalPoints = InkwellCount / 2;
            totalPoints = totalPoints*2;
        } else if (obj[2] == 2) {
            int ManuscriptCount = player.getObjectCounter(Objects.MANUSCRIPT);
            totalPoints = ManuscriptCount / 2;
            totalPoints = totalPoints*2;
        }
        return totalPoints;
    }
}