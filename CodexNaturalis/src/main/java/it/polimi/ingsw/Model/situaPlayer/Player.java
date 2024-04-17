package it.polimi.ingsw.Model.situaPlayer;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.ResourceCard;
import it.polimi.ingsw.Model.situaCorner.Resources;
import it.polimi.ingsw.Model.situaCorner.Objects;

public class Player {
    private String Name;
    private PlayerColor Color;
    private int PointsCounter;
    private int[] ResourceCounter;
    private int[] ObjectCounter;

    private PlayingField Field; //todo il come inizializzarlo diocan

    public Player(String name, PlayerColor color) {
        Name = name;
        Color = color;
        PointsCounter = 0;
        ResourceCounter = new int[4]; //raga scriviamo nei comments cosa è cosa in ogni posizione
        ObjectCounter = new int[3]; //anche qua
    }

    public String getName() {
        return Name;
    }
    public Player(PlayerColor color) {
        Color = color;
    } //raga che minchia è sta roba?
    public Player(int pointsCounter) {
        PointsCounter = pointsCounter;
    } //stessa cosa
    public int getResourceCounter(Resources R){
        if(R==Resources.PLANT_KINGDOM)
            return ResourceCounter[0];
        else if(R==Resources.ANIMAL_KINGDOM)
            return ResourceCounter[1];
        else if(R==Resources.FUNGI_KINGDOM)
            return ResourceCounter[2];
        else
            return ResourceCounter[3];
    }
    public int getObjectCounter(Objects O){
        if(O==Objects.QUILL)
            return ObjectCounter[0];
        else if(O==Objects.INKWELL)
            return ObjectCounter[1];
        else
            return ObjectCounter[2];
    }

    public ResourceCard draw(Deck d){
        //todo dopo il deck
        return null;
    }

    public void placeCard(PlayableCard c, FB face){
        Field.showFreePositions();

    }
}
