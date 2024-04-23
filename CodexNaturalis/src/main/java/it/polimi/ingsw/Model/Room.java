package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaDeck.*;
import it.polimi.ingsw.Model.situaPlayer.Player;
import it.polimi.ingsw.Model.situaPlayer.PlayerColor;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Room {
    private int RoomId;
    private boolean LastRound;
    private Player PlayerTurn;
    private boolean FirstRound;
    private ResourceDeck ResourceDeck;
    private GoldDeck GoldDeck;
    private Deck GoalDeck;
    private StartDeck StartDeck;
    private Set<GoalCard> CommonGoals;
    private Player[] Turns;

    public Room(int RoomId, Player FirstTurn, int numPlayers){
        //todo inizializzatore
        this.RoomId = RoomId;
        this.LastRound = false;
        this.FirstRound = true;
        this.PlayerTurn = FirstTurn;
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
        this.StartDeck = new StartDeck();
        this.ResourceDeck = new ResourceDeck();
        this.GoldDeck = new GoldDeck();
        CompositionGoalDeck temp1 = new CompositionGoalDeck();
        ObjectsGoalDeck temp2 = new ObjectsGoalDeck();
        ResourceGoalDeck temp3 = new ResourceGoalDeck();
        for(int i=0; i<8; i++){
            this.GoalDeck.add((GoalCard)temp1.getCards().get(i));
        }
        for(int i=0; i<4; i++){
            this.GoalDeck.add((GoalCard)temp2.getCards().get(i));
        }
        for(int i=0; i<4; i++){
            this.GoalDeck.add((GoalCard)temp3.getCards().get(i));
        }
    }
}
