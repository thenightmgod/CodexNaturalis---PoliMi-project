package it.polimi.ingsw.Model.situaPlayer;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.GoldCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaCorner.Objects;
import it.polimi.ingsw.Model.Deck;
import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import java.util.Scanner;

public class Player {
    private final String Name;
    private final PlayerColor Color;
    private int PointsCounter;
    private int[] ResourceCounter;
    private int[] ObjectCounter;
    private PlayableCard[] Hand;
    private GoalCard PlayerGoal;
    private PlayingField Field; //todo il come inizializzarlo

    public Player(String name, PlayerColor color) {
        Name = name;
        Color = color;
        PointsCounter = 0;
        ResourceCounter = new int[4];
        ObjectCounter = new int[3];
    }

    public String getName() {
        return Name;
    }

    public PlayerColor getColor() {
        return Color;
    }

    public int getPointsCounter() {
        return PointsCounter;
    }

    public int getResourceCounter(Resources R) {
        if (R == Resources.PLANT_KINGDOM)
            return ResourceCounter[0];
        else if (R == Resources.ANIMAL_KINGDOM)
            return ResourceCounter[1];
        else if (R == Resources.FUNGI_KINGDOM)
            return ResourceCounter[2];
        else
            return ResourceCounter[3];
    }

    public int getObjectCounter(Objects O) {
        if (O == Objects.QUILL)
            return ObjectCounter[0];
        else if (O == Objects.INKWELL)
            return ObjectCounter[1];
        else
            return ObjectCounter[2];
    }

    public ResourceCard draw(Deck d) {
        //todo dopo il deck
        return null;
    }

    public void placeCard(PlayableCard c, FB face) {
        Field.showFreePositions();
    }

    public GoalCard pickGoalCard(GoalCard A, GoalCard B) {
        //TODO stampa a video
        System.out.println("Premi 1 per selezionare la prima GoalCard, 2 per la seconda");
        Scanner scanner = new Scanner(System.in);
        int scelta = scanner.nextInt();
        scanner.close();

        if (scelta == 1) {
            this.PlayerGoal = A;
            return B;
        } else {
            this.PlayerGoal = B;
            return A;
        }
    }
}
