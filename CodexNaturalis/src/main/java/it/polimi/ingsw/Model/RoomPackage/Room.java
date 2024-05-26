package it.polimi.ingsw.Model.RoomPackage;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ResourceGoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.DeckPackage.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents a game room where players participate.
 */
public class Room {

    private ObserverManager observerManager = new ObserverManager();
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
    private Player Winner;
    private boolean LL;

    //la initialize game è stata divisa in più funzioni per evitare sbatti su input da client
    // (non so se ha senso, ma c'est la vie)

    /**
     * Constructs a game room with the specified room identifier and list of players.
     * @param RoomId The unique identifier of the room.
     * @param Players The list of players in the room.
     */
    public Room(int RoomId, LinkedList<Player> Players, LinkedList<VirtualView> clients){
        this.RoomId = RoomId;
        this.LastRound = false;
        this.Twenty = false;
        this.FirstRound = true;
        this.Players = Players;
        this.Turn = Players.getFirst();
        this.CommonGoals = new LinkedList<>();
        this.LL = false;
        for(int i=0; i<this.Players.size(); i++){
            observerManager.addObserver(clients.get(i), Players.get(i).getName());
        }
        //i giocatori vanno creati passando nome e colore da controller arrivano già in ordine
    }

    public LinkedList<Player> getPlayers(){
        return Players;
    }

    public void setTurn(Player player){
        this.Turn = player;
    }

    public Player getTurn(){
        return Turn;
    }

    public ResourceDeck getResourceDeck(){
        return this.ResourceDeck;
    }

    public GoldDeck getGoldDeck(){
        return this.GoldDeck;
    }

    /**
     * Sets the flag indicating whether the game has reached the twenty points threshold.
     */
    public void setTwentyFlag(){ //vede se il punteggio di qualcuno è >= 20 per mettere lastRound=true
        if(Turn.getPointsCounter()>=20)
            this.Twenty = true;
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

    public void setLL(){
        if(LastRound)
            if(Turn.equals(Players.getFirst()))
                this.LL = true;
    }

    public void createCommonGoals() throws RemoteException {
        CommonGoals.add((GoalCard) GoalDeck.getGoalCard());
        CommonGoals.add((GoalCard) GoalDeck.getGoalCard());
        for(Player p: Players){
            observerManager.showGoals("ivebeenwaitingforaguidetocometakemebythehand", CommonGoals);
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

    public ObserverManager getObserverManager(){
        return observerManager;
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
     * @param c The card to place.
     */
    public void placeCard(ResourceCard c, FB face, int x, int y) throws RemoteException {
        Position p = new Position(face, x, y);
        if(!Turn.getPlayerField().getField().containsKey(p)) {
            observerManager.showException("WrongPositionException", Turn.getName());
        }
        else if(c.getId() >= 41 && c.getId() < 81 && !((GoldCard) c).RequirementsOk(Turn)){
            observerManager.showException("RequirementsNotSatisfied", Turn.getName());
        }
        else {
            Turn.placeCard(c, p);
            observerManager.showNewHand(Turn.getName(), Turn.getHand());
            for(Player player: Players){
                observerManager.updateField(player.getName(), Turn.getPlayerField());
            }
            for(Player player: Players) {
                observerManager.updatePoints(player.getPointsCounter(), Turn.getName());
            }
            observerManager.showFreePositions(Turn.getName(), Turn.getPlayerField().getFreePositions());
        }
    } //per il collegamento col controller

    public void placeStartCard(Player p, FB face) throws RemoteException {
        p.placeStartCard((StartCard) p.getHand().getFirst(), face);
        observerManager.updateField(p.getName(), p.getPlayerField());
        observerManager.showFreePositions(Turn.getName(), Turn.getPlayerField().getFreePositions());
        observerManager.showNewHand(Turn.getName(), Turn.getHand());
    }


    /**
     * Picks a goal card for the specified player based on their choice.
     * @param player The player picking the goal card.
     */

    public void pickGoalCard(Player player, boolean i) {
        player.setPlayerGoal(i);
    }

    public void show2GoalCards(Player player) throws RemoteException {
        player.addGoalCard((GoalCard) GoalDeck.getGoalCard()); //in teoria funge
        player.addGoalCard((GoalCard) GoalDeck.getGoalCard());
        observerManager.showGoals(player.getName(), player.get2goals());
    }

    /**
     * Creates all decks required for the game and shuffles them.
     */
    public void createDecks() throws RemoteException {
        this.StartDeck = new StartDeck();
        this.ResourceDeck = new ResourceDeck();
        this.GoldDeck = new GoldDeck();
        this.GoalDeck = new CompositionGoalDeck();

        ObjectsGoalDeck temp2 = new ObjectsGoalDeck();
        ResourceGoalDeck temp3 = new ResourceGoalDeck();

        for(int i=0; i<4; i++){
            this.GoalDeck.add(temp2.getCards().get(i));
        }
        for(int i=0; i<4; i++){
            this.GoalDeck.add(temp3.getCards().get(i));
        }
        StartDeck.shuffle();
        ResourceDeck.shuffle();
        GoldDeck.shuffle();
        GoalDeck.shuffle();
        for(int i=0; i<Players.size(); i++){
            Players.get(i).addGoalCard((GoalCard) this.GoalDeck.getGoalCard());
            Players.get(i).addGoalCard((GoalCard) this.GoalDeck.getGoalCard());
        }
        // robe per observers
        LinkedList<ResourceCard> cards = new LinkedList<>();
        ResourceDeck deck = getResourceDeck();
        cards.add((ResourceCard) deck.getCards().get(0));
        cards.add((ResourceCard) deck.getCards().get(1));
        cards.add((ResourceCard) deck.getCards().get(2));
        for(Player p: Players) {
            observerManager.updateResourceDeck("ivebeenwaitingforaguidetocometakemebythehand", cards);
        }

        // robe per observers
        LinkedList<GoldCard> card5 = new LinkedList<>();
        ResourceDeck d3ck = getResourceDeck();
        cards.add((GoldCard) d3ck.getCards().get(0));
        cards.add((GoldCard) d3ck.getCards().get(1));
        cards.add((GoldCard) d3ck.getCards().get(2));
        for(Player p: Players) {
            observerManager.updateGoldDeck("ivebeenwaitingforaguidetocometakemebythehand", card5);
        }
    }


    /**
     * Distributes start cards to players.
     //* @param  face The face of the start card.
     */
    public void giveStartCards(Player player) throws RemoteException {
        synchronized (StartDeck) { //boh
            StartDeck.giveCard(this.Turn, 0);
            observerManager.showStartCard(player.getName(), (StartCard) player.getHand().getFirst());
        }
    }

    public void giveInitialCards(Player p) throws RemoteException {
        getResourceDeck().giveCard(p, 3);
        getResourceDeck().giveCard(p, 3);
        getGoldDeck().giveCard(p, 3);
        observerManager.showNewHand(p.getName(), p.getHand());
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

    public LinkedList<GoalCard> getCommonGoals(){
        return this.CommonGoals;
    }

    public void checkGoals(Player p) throws RemoteException {
        LinkedList<GoalCard> toCheck = CommonGoals;
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

    public Deck getGoalDeck() {
        return GoalDeck;
    }

    public StartDeck getStartDeck() {
        return StartDeck;
    }

    public void start() throws RemoteException {
        observerManager.updateTurn(Turn);
    }

    public void declareWinner() throws RemoteException {
        HashMap<String, Integer> points = new HashMap<>();
        for(Player p: Players) {
            points.put(p.getName(), p.getPointsCounter());
        }
        for(Player p: Players) {
            observerManager.declareWinner(p.getName(), points);
        }
    }

    public void changeTurns() throws RemoteException {
        setLL();
        if(LL){
            for(Player p: Players)
                checkGoals(p);
            declareWinner();
        }
        else {
            int size = Players.size();
            switch (size) {
                case 2 -> {
                    if (Turn.equals(Players.getFirst())) {
                        setTurn(Players.get(1));
                        break;
                    }
                    setTurn(Players.getFirst());
                }
                case 3 -> {
                    if (Turn.equals(Players.getFirst()))
                        setTurn(Players.get(1));
                    else if (Turn.equals(Players.get(1)))
                        setTurn(Players.get(2));
                    else setTurn(Players.getFirst());
                }
                case 4 -> {
                    if (Turn.equals(Players.getFirst()))
                        setTurn(Players.get(1));
                    else if (Turn.equals(Players.get(1)))
                        setTurn(Players.get(2));
                    else if (Turn.equals(Players.get(2)))
                        setTurn(Players.get(3));
                    else setTurn(Players.getFirst());
                }
            }
            setTwentyFlag();
            setLastRound();
            observerManager.updateTurn(Turn);
        }
    }
}
