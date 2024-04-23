package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.situaCard.situaGoalCard.GoalCard;
import it.polimi.ingsw.Model.situaCard.situaPlayableCard.PlayableCard;
import it.polimi.ingsw.Model.situaDeck.*;
import it.polimi.ingsw.Model.situaPlayer.FB;
import it.polimi.ingsw.Model.situaPlayer.Player;
import it.polimi.ingsw.Model.situaPlayer.Position;

import java.util.Collections;
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
        //i giocatori vanno creati passando nome e colore da controller
    }

    public void initializeGame() {
        createDecks(); //carte a terra si prendono dalle prime 3 posizioni di Resource e Gold deck
        for (Player turn : Turns){
            FB face = FB.FRONT; //in realtà il controller deve darmi la posizione, ma non esiste ancora
            giveStartCards(face, turn);
        }
        giveHands(); //dà le mano ai giocatori
        commmonGoals();
        for (Player turn : Turns){
            boolean choice = true; //in realtà gliela passa il controller di nuovo
            pickGoalCard(turn, choice);
        }
        //va shufflato l'array dei player
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
    /*public void drawCard(Player player, Deck deck){
        player.drawCard(deck);
    }*/
    public Player getTurn(){  //controller gestisce i turni e passa alla room il turno corrente
        return this.PlayerTurn;
    }
    public void placeCard(Player player, PlayableCard card, Position p){
        player.placeCard(card, p);
    }
    public void drawCard(){
        //TODO, prima fare i decks
    }
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
        p.placeCard(p.getHand().get(0), new Position(face, 0, 0));
    }
    public void commmonGoals(){
        GoalCard Goal_1= (GoalCard)GoalDeck.getCards().get(0);
        GoalCard Goal_2= (GoalCard)GoalDeck.getCards().get(1);
        CommonGoals.add(Goal_1);
        CommonGoals.add(Goal_2);
        GoalDeck.getCards().remove(Goal_1);
        GoalDeck.getCards().remove(Goal_2);
    }
}
