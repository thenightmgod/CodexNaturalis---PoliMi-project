package it.polimi.ingsw.Model.RoomPackage;

import it.polimi.ingsw.View.ChatMessage;
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
import it.polimi.ingsw.Network.VirtualView;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;


/**
 * Represents a game room where players participate.
 */
public class Room implements Serializable {

    private ObserverManager observerManager = new ObserverManager();
    private LinkedList<ChatMessage> chat = new LinkedList<>();
    private final int roomId;
    private boolean lastRound;
    private boolean twenty;
    private LinkedList<Player> players;
    private Player turn;
    private ResourceDeck resourceDeck;
    private GoldDeck goldDeck;
    private Deck goalDeck;
    private StartDeck startDeck;
    private LinkedList<GoalCard> commonGoals;
    private boolean ll;


    /**
     * Constructs a game room with the specified room identifier and list of players.
     * @param roomId The unique identifier of the room.
     * @param players The list of players in the room.
     * @param clients The list of clients connected to the room.
     */
    public Room(int roomId, LinkedList<Player> players, LinkedList<VirtualView> clients){
        this.roomId = roomId;
        this.lastRound = false;
        this.twenty = false;
        this.players = players;
        this.turn = players.getFirst();
        this.commonGoals = new LinkedList<>();
        this.ll = false;
        for(int i=0; i<this.players.size(); i++){
            observerManager.addObserver(clients.get(i), players.get(i).getName());
        }
    }

    /**
     * Returns the current player whose turn it is.
     * @return The current player.
     */
    public Player getTurn(){
        return turn;
    }


    /**
     * Returns the ResourceDeck of the game.
     * @return The ResourceDeck of the game.
     */
    public ResourceDeck getResourceDeck(){
        return this.resourceDeck;
    }

    /**
     * Returns the GoldDeck of the game.
     * @return The GoldDeck of the game.
     */
    public GoldDeck getGoldDeck(){
        return this.goldDeck;
    }

    /**
     * Sets the flag indicating whether the game has reached the twenty points threshold.
     */
    public void setTwentyFlag() {
        if(turn.getPointsCounter()>=2)
            this.twenty = true;
        if(twenty && !lastRound)
            if(turn.getPointsCounter()>=2)
                observerManager.twenty(turn.getName());
    }
    /**
     * Sets the last round flag if the twenty points threshold is reached and it's the first player's turn.
     */
    public void setLastRound() {
        ResourceDeck rd = getResourceDeck();
        GoldDeck gd = getGoldDeck();
        if(twenty || (gd.getSize()==0 && rd.getSize()==0))
            if(turn.equals(players.getLast()))
                this.lastRound = true;
    }

    /**
     * Sets the LL flag if it's the last round and it's the last player's turn.
     */
    public void setLL(){
        if(lastRound)
            if(turn.equals(players.getLast()))
                this.ll = true;
    }

    /**
     * Creates common goals for the game.
     */
    public void createCommonGoals() {
        commonGoals.add((GoalCard) goalDeck.getGoalCard());
        commonGoals.add((GoalCard) goalDeck.getGoalCard());
        for(Player p: players){
            p.addGoalCard(commonGoals.get(0), "common");
            p.addGoalCard(commonGoals.get(1), "common");
        }
    }

    /**
     * Retrieves the room identifier.
     * @return The unique identifier of the room.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Returns the ObserverManager of the game.
     * @return The ObserverManager of the game.
     */
    public ObserverManager getObserverManager(){
        return observerManager;
    }


    /**
     * Places a card on the playing field for the specified player at the given position.
     * @param c The card to place.
     * @param face The face of the card.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @throws RemoteException If a remote communication error occurs.
     * @throws NotBoundException If a binding error occurs.
     */
    public void placeCard(ResourceCard c, FB face, int x, int y) throws RemoteException, NotBoundException {
        Position p = new Position(face, x, y);
        if(turn.getPlayerField().containsInField(p) || !(turn.getPlayerField().containsFreePosition(p))) {
            observerManager.showException("WrongPositionException", "Nothing", turn.getName());
        }
        else {
            if(c.getId() >= 41 && c.getId() < 81 && face == FB.FRONT && !((GoldCard) c).RequirementsOk(turn)){
                observerManager.showException("RequirementsNotSatisfied", "Nothing", turn.getName());
            }
            else {
                turn.placeCard(c, p);
                HashMap<String, Integer> points = new HashMap<>();
                for(Player lazzaro: players){
                    points.put(lazzaro.getName(), lazzaro.getPointsCounter());
                }
                observerManager.showNewHand(turn.getName(), turn.getHand());
                observerManager.updateField(turn.getName(), turn.getPlayerField());
                observerManager.updatePoints(points, turn.getName());
                observerManager.showFreePositions(turn.getName(), turn.getPlayerField().getFreePositions());
                observerManager.showException("Nothing", "PlaceCardWell", turn.getName());
            }
        }
    }

    /**
     * Places a start card on the playing field for the specified player at the given face.
     * @param p The player to place the start card for.
     * @param face The face of the start card.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void placeStartCard(Player p, FB face) throws RemoteException {
        p.placeStartCard((StartCard) p.getHand().getFirst(), face);
        observerManager.updateField(p.getName(), p.getPlayerField());
        observerManager.showFreePositions(turn.getName(), turn.getPlayerField().getFreePositions());
        //observerManager.showNewHand(Turn.getName(), Turn.getHand());
    }

    /**
     * Starts the game for the specified player.
     * @param p The player to start the game for.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void startingGame(Player p) throws RemoteException {
        observerManager.getObserver(p.getName()).startingGame(p);
    }

    /**
     * Picks a goal card for the specified player based on their choice.
     * @param player The player picking the goal card.
     */

    public void pickGoalCard(Player player, int i) {
        player.setPlayerGoal(i);
        observerManager.showCommonGoals(player.getName(), player.getCommonGoals());
    }

    /**
     * Shows two goal cards to the specified player.
     * @param player The player to show the goal cards to.
     */
    public void show2GoalCards(Player player) {
        player.addGoalCard((GoalCard) goalDeck.getGoalCard(), "personal"); //in teoria funge
        player.addGoalCard((GoalCard) goalDeck.getGoalCard(), "personal");
        observerManager.showGoals(player.getName(), player.get2Goals());
    }

    /**
     * Creates all decks required for the game and shuffles them.
     */
    public void createDecks() {
        boolean robo = true;
        this.startDeck = new StartDeck();
        this.resourceDeck = new ResourceDeck();
        this.goldDeck = new GoldDeck();
        this.goalDeck = new CompositionGoalDeck();

        ObjectsGoalDeck temp2 = new ObjectsGoalDeck();
        ResourceGoalDeck temp3 = new ResourceGoalDeck();

        for(int i=0; i<4; i++){
            this.goalDeck.add(temp2.getCards().get(i));
        }
        for(int i=0; i<4; i++){
            this.goalDeck.add(temp3.getCards().get(i));
        }
        startDeck.shuffle();
        resourceDeck.shuffle();
        goldDeck.shuffle();
        goalDeck.shuffle();

        LinkedList<ResourceCard> cards = new LinkedList<>();
        ResourceDeck deck = getResourceDeck();
        cards.add((ResourceCard) deck.getCards().get(0));
        cards.add((ResourceCard) deck.getCards().get(1));
        cards.add((ResourceCard) deck.getCards().get(2));
        for(Player p: players) {
            observerManager.updateResourceDeck(p.getName(), robo, cards);
        }

        LinkedList<GoldCard> card5 = new LinkedList<>();
        GoldDeck d3ck = getGoldDeck();
        card5.add((GoldCard) d3ck.getCards().get(0));
        card5.add((GoldCard) d3ck.getCards().get(1));
        card5.add((GoldCard) d3ck.getCards().get(2));
        for(Player p: players) {
            observerManager.updateGoldDeck(p.getName(), robo, card5);
        }
    }


    /**
     * Distributes start cards to players.
     //* @param  face The face of the start card.
     */
    public void giveStartCards() {
        synchronized (startDeck) {
            for(Player p : players) {
                startDeck.giveCard(p, 0);
                observerManager.showStartCard(p.getName(), (StartCard) p.getHand().getFirst());
            }
        }
    }

    /**
     * Gives initial cards to the specified player.
     * @param p The player to give the initial cards to.
     */
    public void giveInitialCards(Player p) {
        getResourceDeck().giveCard(p, 3);
        getResourceDeck().giveCard(p, 3);
        getGoldDeck().giveCard(p, 3);
        observerManager.showNewHand(p.getName(), p.getHand());
    }

    /**
     * Checks the goals of the specified player.
     * @param p The player whose goals to check.
     */
    public void checkGoals(Player p) {
        LinkedList<GoalCard> toCheck = p.getCommonGoals();

        for(int i=0; i<3; i++){
            if(toCheck.get(i).getId() >= 87 && toCheck.get(i).getId() <= 94) {
                CompositionGoalCard nostra = (CompositionGoalCard) toCheck.get(i);
                p.addGoalPoints(nostra.pointsCalc(p, nostra.getColor()));
            }
            if(toCheck.get(i).getId() >= 95 && toCheck.get(i).getId() <= 98) {
                ResourceGoalCard nostra = (ResourceGoalCard) toCheck.get(i);
                p.addGoalPoints(nostra.pointsCalc(p));
            }
            if(toCheck.get(i).getId() >= 99 && toCheck.get(i).getId() <= 102) {
                ObjectsGoalCard nostra = (ObjectsGoalCard) toCheck.get(i);
                p.addGoalPoints(nostra.pointsCalc(p));
            }
        }
        p.addTotalPoints(p.getPointsCounter());
        p.addTotalPoints(p.getGoalPointsCounter());
    }

    /**
     * Starts the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void start() throws RemoteException {
        this.observerManager.updateTurn(turn, "StartCard");
    }

    /**
     * Declares the winner of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void declareWinner() throws RemoteException {
        TreeMap<String, Integer> goalPoints = new TreeMap<>();
        TreeMap<String, Integer> totalPoints = new TreeMap<>();

        for (Player p : players) {
            goalPoints.put(p.getName(), p.getGoalPointsCounter());
        }
        for (Player p : players) {
            totalPoints.put(p.getName(), p.getTotalPointsCounter());
        }


        LinkedList<String> NamesInOrder = new LinkedList<>(totalPoints.keySet());

        NamesInOrder.sort((player1, player2) -> {
            int totalComparison = totalPoints.get(player2).compareTo(totalPoints.get(player1));
            if (totalComparison != 0) {
                return totalComparison;
            } else {
                return goalPoints.get(player2).compareTo(goalPoints.get(player1));
            }
        });

        for(Player p: players) {
            observerManager.declareWinner(p.getName(), NamesInOrder);
        }
    }

    /**
     * Changes the turn of the game.
     * @param mex The message to send to the players.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void changeTurns(String mex) throws RemoteException {
        setLL();
        if(ll){
            for(Player p: players)
                checkGoals(p);
            declareWinner();
        }
        else {
            setTwentyFlag();
            setLastRound();

            int index = players.indexOf(turn);
            turn = players.getLast().equals(turn) ? players.getFirst() : players.get(index + 1);

            switch(mex) {
                case "StartCard" -> {
                    if(turn.equals(players.getFirst())) {
                        for(Player p: players) {
                            giveInitialCards(p);
                        }
                        for(Player p: players) {
                            show2GoalCards(p);
                        }
                        observerManager.updateTurn(turn, "GoalCard");
                    }
                    else observerManager.updateTurn(turn, "StartCard");
                }

                case "GoalCard" -> {
                    if(turn.equals(players.getFirst()))
                        observerManager.updateTurn(turn, "NormalTurn");
                    else observerManager.updateTurn(turn, "GoalCard");
                }
                case "NormalTurn" -> {
                    if(lastRound)
                        observerManager.lastRound(turn.getName());
                    observerManager.updateTurn(turn, "NormalTurn");
                }
            }
        }
    }

    public void sendPlayers(){
        LinkedList<String> names = new LinkedList<>();
        for(Player p: players){
            names.add(p.getName());
        }
        observerManager.sendPlayers(names);
    }

    public void sendChatMessage(ChatMessage message) throws RemoteException {
        chat.add(message);
        HashMap<String, LinkedList<ChatMessage>> toSend = new HashMap<>();

        for (Player player : players) {
            LinkedList<ChatMessage> playerMessages = new LinkedList<>();
            for (int i=0; i<chat.size(); i++) {
                if (chat.get(i).getSender().equals(player.getName()) ||
                        chat.get(i).getRecipient().equals(player.getName()) ||
                        chat.get(i).getRecipient().equals("all")) {
                    playerMessages.add(chat.get(i));
                }
            }
            toSend.put(player.getName(), playerMessages);
        }

        for (Map.Entry<String, LinkedList<ChatMessage>> entry : toSend.entrySet()) {
            String playerName = entry.getKey();
            LinkedList<ChatMessage> messages = entry.getValue();
            observerManager.updateChat(playerName, messages);
        }
    }
}
