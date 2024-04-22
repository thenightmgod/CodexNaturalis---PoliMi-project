package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaPlayer.Player;
import it.polimi.ingsw.Model.situaCorner.Objects;

public class ObjectsGoalCard extends GoalCard {
    private final int[] obj;
    //la prima posizione è la QUILL, la seconda è la INKWELL, la terza MANUSCRIPT,
    // quando le istanzio metto un '2' al primo posto se la carta obiettivo
    //richiede 2 QUILL ecc...

    public ObjectsGoalCard (int id, int points, int[] arrayInput) {
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

    @Override
    public int getId() {
        return super.getId();
    }
    @Override
    public int getPoints() {
        return super.getPoints();
    }

    public int[] getObj() {
        return obj;
    }

    public int PointsCalc(Player player) {
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
        } else if (obj[1] == 2) {
            int InkwellCount = player.getObjectCounter(Objects.INKWELL);
            totalPoints = InkwellCount / 2;
        } else if (obj[2] == 2) {
            int ManuscriptCount = player.getObjectCounter(Objects.MANUSCRIPT);
            totalPoints = ManuscriptCount / 2;
        }
        return totalPoints;
    }


}

