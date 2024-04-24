package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.StartCard;
import it.polimi.ingsw.Model.situaDeck.*;
import it.polimi.ingsw.Model.situaPlayer.FB;
import it.polimi.ingsw.Model.situaPlayer.Player;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Room {
    private final int RoomId;
    private boolean LastRound;
    private boolean Twenty;
    private LinkedList<Player> Players;
    private Player Turn;
    private boolean FirstRound;
    private ResourceDeck ResourceDeck;
    private GoldDeck GoldDeck;
    private Deck GoalDeck;
    private StartDeck StartDeck;
    private Set<GoalCard> CommonGoals;
    private Player[] Turns;

    public Room(int RoomId, LinkedList<Player> Players){
        this.RoomId = RoomId;
        this.LastRound = false;
        this.Twenty = false;
        this.FirstRound = true;
        this.Players = Players;
        this.Turn = Players.getFirst();
        this.CommonGoals = new HashSet<>();
        this.Turns = new Player[Players.size()];
        //i giocatori vanno creati passando nome e colore da controller arrivano già in ordine
    }

    public void setTwentyFlag(){ //vede se il punteggio di qualcuno è >= 20 per mettere lastRound=true
            for(Player p : Players){
                if(p.getPointsCounter()>=20)
                    this.Twenty = true;
            }
    }

    public void setLastRound(){
        if(Twenty)
            if(Turn.equals(Players.getFirst()))
                this.LastRound = true;
    }

    public void initializeGame() {
        createDecks(); //carte a terra si prendono dalle prime 3 posizioni di Resource e Gold deck
        for (Player turn : Turns){
            FB face = FB.FRONT; //in realtà il controller deve darmi la posizione, ma non esiste ancora
            giveStartCards(face, turn);
        }
        giveHands(); //dà le mano ai giocatori
        commonGoals();
        for (Player turn : Turns){
            boolean choice = true; //in realtà gliela passa il controller di nuovo
            pickGoalCard(turn, choice);
        }
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

    public LinkedList<Player> getTurns(){  //controller gestisce i turni e passa alla room il turno corrente
        return this.Players;
    }
    public void placeCard(Player player, PlayableCard card, Position p){
        player.placeCard(card, p);
    } //per il collegamento col controller

    public void pickGoalCard(Player player, boolean choice){
        GoalCard A = (GoalCard) GoalDeck.getGoalCard();
        GoalCard C = (GoalCard) GoalDeck.getGoalCard();
        if(choice) player.setPlayerGoal(A);
        else player.setPlayerGoal(C);
    }
    public void createDecks(){
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
        StartDeck.shuffle();
        ResourceDeck.shuffle();
        GoldDeck.shuffle();
        GoalDeck.shuffle();
    }
    public void giveHands(){ //rimangono così fisse le prime 2
        for (Player turn : Turns) {
            ResourceDeck.giveCard(turn, 2);
            ResourceDeck.giveCard(turn, 2);
            GoldDeck.giveCard(turn, 2);
        }
    }
    public void giveStartCards(FB face, Player p){
        StartDeck.giveCard(p, 0);
        p.placeStartCard((StartCard) p.getHand().getFirst(), face);
    }
    public void commonGoals(){
        GoalCard Goal_1= (GoalCard)GoalDeck.getCards().get(0);
        GoalCard Goal_2= (GoalCard)GoalDeck.getCards().get(1);
        CommonGoals.add(Goal_1);
        CommonGoals.add(Goal_2);
        GoalDeck.getCards().remove(Goal_1);
        GoalDeck.getCards().remove(Goal_2);
    }
}
