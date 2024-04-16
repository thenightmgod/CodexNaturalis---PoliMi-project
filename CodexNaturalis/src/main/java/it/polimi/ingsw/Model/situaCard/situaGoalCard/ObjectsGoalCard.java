package it.polimi.ingsw.Model.situaCard.situaGoalCard;

import it.polimi.ingsw.Model.situaPlayer.Player;

public class ObjectsGoalCard extends GoalCard {
    private final int[] obj;
    //la prima posizione è la QUILL, la seconda è la INKWELL, la terza MANUSCRIPT,
    // quando le istanzio metto un '2' al primo posto se la carta obiettivo
    //richiede 2 QUILL ecc...

    public ObjectsGoalCard (int points, int [] arrayInput) {
        super(points);
        if (arrayInput.length != 3) {
            throw new IllegalArgumentException("L'array di input deve essere di dimensione 3");
        }
        obj = new int[3];
        // Copia gli elementi dall'array di input a obj
        for (int i = 0; i < 3; i++) {
            obj[i] = arrayInput[i];
        }
    }
    public int[] getObj() {
        return obj;
    }
    public int PointsCalc(Player player) {
        int totalPoints = 0;

        if (this.getPoints() == 3) {
            int QuillCount = player.getObjectCounter(Objects.Quill);
            int InkwellCount = player.getObjectCounter(Objects.Inkwell);
            int ManuscriptCount = player.getObjectCounter(Obects.Manuscript);
            int totalGroups = Math.min(QuillCount, Math.min(InkwellCount, ManuscriptCount));
            totalPoints = totalGroups * 3;
        } else if (objectsCount[0] == 2) {
            int QuillCount = player.getObjectCounter(Objects.Quill);
            totalPoints = QuillCount / 2;
        } else if (ObjectsCount[1] == 2) {
            int InkwellCount = player.getObjectCounter(Objects.Inkwell);
            totalPoints = InkwellCount / 2;
        } else if (ObjectsCount[2] == 2) {
            int ManuscriptCount = player.getObjectCounter(Objects.Manuscript);
            totalPoints = ManuscriptCount / 2;
        }
        return totalPoints;
    }







    }



}
