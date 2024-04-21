package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaDeck.Deck;
import it.polimi.ingsw.Model.situaPlayer.Player;
import it.polimi.ingsw.Model.situaPlayer.PlayerColor;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private final int RoomId;
    private boolean LastRound;
    private Player PlayerTurn;
    private boolean FirstRound;
    private Deck ResourceDeck;
    private Deck GoalDeck;
    private Deck StartDeck;
    private Set<GoalCard> CommonGoals;
    private Player[] Turns;

    public Room(int RoomId, Player FirstTurn, int numPlayers){
        //todo inizializzatore
        this.RoomId = RoomId;
        this.LastRound = false;
        this.FirstRound = true;
        this.PlayerTurn = FirstTurn;
        //this.ResourceDeck = new ResourceDeck ;  //finire costruttore dei decks
        //this.GoalDeck = new GoalDeck;
        //this.StartDeck = new StartDeck;
        this.CommonGoals = new HashSet<>();
        this.Turns = new Player[numPlayers];
    }

    public int getRoomId() {
        return RoomId;
    }
    public boolean getLastRound() {
        return LastRound;
    }
    public boolean getFirstRound() {
        return FirstRound;
    }
    public void drawCard(Player player, Deck deck){
        player.drawCard(deck);
    }
    public Player getTurn(){  //controller gestisce i turni e passa alla room il turno corrente
        return this.PlayerTurn;
    }
    public void placeCard(Player player, PlayableCard card, Position p){
        player.placeCard(card, p);
    }
    public void drawCard(){
        //TODO, prima fare i decks
    }
    public void pickGoalCard(Player player, GoalCard A, GoalCard B, boolean choice){
        player.pickGoalCard(A, B, choice);
    }
    public void initializeGame() {
        //vanno creati i deck
        for (int i = 0; i < this.Turns.length; i++){

        }

    }
}
