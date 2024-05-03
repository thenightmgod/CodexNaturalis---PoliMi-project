package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ResourceGoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.DeckPackage.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Represents a game room where players participate.
 */
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
    private LinkedList<GoalCard> CommonGoals;
    private Player[] Turns;


    /**
     * Constructs a game room with the specified room identifier and list of players.
     * @param RoomId The unique identifier of the room.
     * @param Players The list of players in the room.
     */
    public Room(int RoomId, LinkedList<Player> Players){
        this.RoomId = RoomId;
        this.LastRound = false;
        this.Twenty = false;
        this.FirstRound = true;
        this.Players = Players;
        this.Turn = Players.getFirst();
        this.CommonGoals = new LinkedList<>();
        this.Turns = new Player[Players.size()];
        //i giocatori vanno creati passando nome e colore da controller arrivano già in ordine
    }


    /**
     * Sets the flag indicating whether the game has reached the twenty points threshold.
     */
    public void setTwentyFlag(){ //vede se il punteggio di qualcuno è >= 20 per mettere lastRound=true
            for(Player p : Players){
                if(p.getPointsCounter()>=20)
                    this.Twenty = true;
            }
    }

    public boolean getTwentyFlag() {
        return this.Twenty;
    }

    /**
     * Sets the last round flag if the twenty points threshold is reached and it's the first player's turn.
     */
    public void setLastRound(){
        if(Twenty)
            if(Turn.equals(Players.getFirst()))
                this.LastRound = true;
    }


    /**
     * Initializes the game by creating decks, distributing start cards and goals, and setting common goals.
     */
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

    /**
     * Retrieves the room identifier.
     * @return The unique identifier of the room.
     */
    public int getRoomId() {
        return RoomId;
    }

    /**
     * Retrieves the last round flag.
     * @return {@code true} if it's the last round, {@code false} otherwise.
     */
    public boolean getLastRound() {
        return LastRound;
    }

    /**
     * Retrieves the first round flag.
     * @return {@code true} if it's the first round, {@code false} otherwise.
     */
    public boolean getFirstRound() {
        return FirstRound;
    }


    /**
     * Retrieves the list of players.
     * @return The list of players in the room.
     */
    public LinkedList<Player> getTurns(){  //controller gestisce i turni e passa alla room il turno corrente
        return this.Players;
    }

    /**
     * Places a card on the playing field for the specified player at the given position.
     * @param player The player placing the card.
     * @param card The card to place.
     * @param p The position to place the card.
     */
    public void placeCard(Player player, ResourceCard card, Position p){
        player.placeCard(card, p);
    } //per il collegamento col controller

    /**
     * Picks a goal card for the specified player based on their choice.
     * @param player The player picking the goal card.
     * @param choice The choice made by the player.
     */

    public void pickGoalCard(Player player, boolean choice){
        GoalCard A = (GoalCard) GoalDeck.getGoalCard();
        GoalCard C = (GoalCard) GoalDeck.getGoalCard();
        if(choice) player.setPlayerGoal(A);
        else player.setPlayerGoal(C);
    }

    /**
     * Creates all decks required for the game and shuffles them.
     */
    public void createDecks(){
        this.StartDeck = new StartDeck();
        this.ResourceDeck = new ResourceDeck();
        this.GoldDeck = new GoldDeck();
        CompositionGoalDeck temp1 = new CompositionGoalDeck();
        ObjectsGoalDeck temp2 = new ObjectsGoalDeck();
        ResourceGoalDeck temp3 = new ResourceGoalDeck();
        /*for(int i=0; i<8; i++){
            this.GoalDeck.add((GoalCard)temp1.getCards().get(i));
        }
        for(int i=0; i<4; i++){
            this.GoalDeck.add((GoalCard)temp2.getCards().get(i));
        }
        for(int i=0; i<4; i++){
            this.GoalDeck.add((GoalCard)temp3.getCards().get(i));
        } */
        StartDeck.shuffle();
        ResourceDeck.shuffle();
        GoldDeck.shuffle();
        //GoalDeck.shuffle();
    }



    /**
     * Distributes hands to each player, giving them a fixed number of cards from the resource and gold decks.
     * The first two cards remain fixed.
     */
    public void giveHands(){ //rimangono così fisse le prime 2
        for (Player turn : Turns) {
            ResourceDeck.giveCard(turn, 2);
            ResourceDeck.giveCard(turn, 2);
            GoldDeck.giveCard(turn, 2);
        }
    }

    /**
     * Distributes start cards to players.
     * @param face The face of the start card.
     * @param p The player receiving the start card.
     */
    public void giveStartCards(FB face, Player p){
        StartDeck.giveCard(p, 0);
        p.placeStartCard((StartCard) p.getHand().getFirst(), face);
    }

    /**
     * Sets common goals for the game.
     */

    public void commonGoals(){
        GoalCard Goal_1= (GoalCard)GoalDeck.getCards().get(0);
        GoalCard Goal_2= (GoalCard)GoalDeck.getCards().get(1);
        CommonGoals.add(Goal_1);
        CommonGoals.add(Goal_2);
        GoalDeck.getCards().remove(Goal_1);
        GoalDeck.getCards().remove(Goal_2);
    }

    public void checkGoals(Player p, LinkedList<GoalCard> commonGoals){
        LinkedList<GoalCard> toCheck = new LinkedList<>(commonGoals);
        toCheck.add(p.getPlayerGoal());
        for(int i=0; i<3; i++){
            if(toCheck.get(i).getId() >= 87 && toCheck.get(i).getId() <= 94) {
                CompositionGoalCard nostra = (CompositionGoalCard) toCheck.get(i);
                p.addPoints(nostra.pointsCalc(p, nostra.getColor()));
            }
            if(toCheck.get(i).getId() >= 95 && toCheck.get(i).getId() <= 98) {
                ResourceGoalCard nostra = (ResourceGoalCard) toCheck.get(i);
                p.addPoints(nostra.pointsCalc(p));
            }
            if(toCheck.get(i).getId() >= 99 && toCheck.get(i).getId() <= 102) {
                ObjectsGoalCard nostra = (ObjectsGoalCard) toCheck.get(i);
                p.addPoints(nostra.pointsCalc(p));
            }
        }
    }
}
