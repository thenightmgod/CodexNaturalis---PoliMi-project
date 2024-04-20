package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import it.polimi.ingsw.Model.situaDeck.Deck;
import it.polimi.ingsw.Model.situaPlayer.Player;
import it.polimi.ingsw.Model.situaPlayer.PlayerColor;
import java.util.Set;

public class Room {
    private final int RoomId;
    private boolean LastRound;
    private PlayerColor PlayerTurn;
    private boolean FirstRound;
    private Deck ResourceDeck;
    private Deck GoalDeck;
    private Deck StartDeck;
    private Set<GoalCard> CommonGoals;

    public Room(int RoomId, ){
        //todo inizializzatore
        this.RoomId = RoomId;
        this.LastRound = false;
        this.FirstRound = true;
    }

    public int getRoomId() {
        return RoomId;
    }
    public boolean isLastRound() {
        return LastRound;
    }
    public boolean isFirstRound() {
        return FirstRound;
    }
    public void drawCard(Player player, Deck deck){
        player.drawCard(deck);
    }

}
