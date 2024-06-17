package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**The GameController class manages the game state, players, and game logic.
 * */
public class GameController {
    private final int numPlayers;
    private GameState state;
    private Room game;
    private final int roomId;
    private final LinkedList<Player> players;
    private final LinkedList<VirtualView> clients;

    /**
     * This function is the constructor of the GameController with the specified room ID and number of players as inputs.
     *
     * @param id         the ID of the room
     * @param numPlayers the number of players
     */
    public GameController(int id, int numPlayers) {
        this.numPlayers = numPlayers;
        this.state = GameState.WAITING;
        this.roomId = id;
        this.players = new LinkedList<>();
        this.clients = new LinkedList<>();
        run();
    }

    /**
     * Returns the Room.
     */
    public Room getGame() {
        return game;
    }

    /**
     * Returns the room ID.
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Returns the number of players requested by the room to start the game.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Returns the number of players currently in the game.
     */
    public int getHowManyPlayers() {
        return this.players.size();
    }

    /**
     * Removes a player by their name.
     *
     * @param name the name of the player to remove
     */
    public void removePlayer(String name) {
        players.removeIf(p -> Objects.equals(p.getName(), name));
    }

    /**
     * Adds a new player to the game.
     *
     * @param name   the name of the player
     * @param color  the color of the player
     * @param client the virtual view of the client
     */
    public void addPlayer(String name, PlayerColor color, VirtualView client) {
        Player player = new Player(name, color);
        this.players.add(player);
        this.clients.add(client);
    }

    /**
     * Initializes the game room with the current players and clients.
     */
    public void initializeRoom() {
        this.game = new Room(roomId, players, clients);
    }

    /**
     * Starts a thread that waits for the required number of players before starting the game.
     */
    public void run(){
        new Thread(() -> {

            Timer timer = new Timer("Timer");
            TimerTask task = new TimerTask() {

                public void run() {
                    if(state==GameState.WAITING) {
                        if (players.size() == numPlayers) {
                            state = GameState.RUNNING;
                            try {
                                startGame();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            } finally {
                                timer.cancel();
                            }
                        }
                    }
                    else {
                        timer.cancel();
                    }
                }
            };

            long delay = 3000L;
            long period = 1500L;

            timer.scheduleAtFixedRate(task, delay, period);

        }).start();
    }

    /**
     * Starts the game by initializing the room, setting up decks, common goals, and notifying players.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public void startGame() throws RemoteException {
        initializeRoom();
        for (Player p : players)
            this.game.startingGame(p);
        createDecks();
        createCommonGoals();
        this.game.giveStartCards();
        this.game.start();
    }

    /**
     * Calls the room method that creates common goals for the game.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public void createCommonGoals() throws RemoteException {
        this.game.createCommonGoals();
    }

    /**
     * Calls the room method that creates decks for the game.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public void createDecks() throws RemoteException {
        this.game.createDecks();
    }

    /**
     * Calls the room method that allows the player to choose a GoalCard.
     *
     * @param name the name of the player
     * @param i    the index of the goal card
     * @throws RemoteException if a remote communication error occurs
     */
    public void chooseGoalCard(String name, int i) throws RemoteException {
        this.game.pickGoalCard(getPlayerByName(name), i);
    }

    /**
     * Calls the room method that places a card on the game board.
     *
     * @param i    the index of the card in the player's hand
     * @param x    the x-coordinate on the board
     * @param y    the y-coordinate on the board
     * @param face the face of the card
     * @throws RemoteException if a remote communication error occurs
     * @throws NotBoundException if a binding error occurs
     */
    public void placeCard(int i, int x, int y, FB face) throws RemoteException, NotBoundException {
        if (i < 1 || i > 3)
            getGame().getObserverManager().showException("WrongIndexException", "PlaceCard", getGame().getTurn().getName());
        else
            {
            this.game.placeCard(this.game.getTurn().getCardFromHand(i), face, x, y);
            }
    }

    /**
     * Calls the room method that places the starting card for a player.
     *
     * @param name the name of the player
     * @param face the face of the card
     * @throws RemoteException if a remote communication error occurs
     */
    public void placeStartCard(String name, FB face) throws RemoteException {
        this.game.placeStartCard(getPlayerByName(name), face);
    }

    /**
     * Calls the room method that picks a resource card for the current player. And then notifies the player of his new hand.
     *
     * @param i the index of the card to pick
     * @throws RemoteException if a remote communication error occurs
     */
    public void pickResCard(int i) throws RemoteException {
        this.game.getResourceDeck().giveCard(this.game.getTurn(), i);
        this.game.getObserverManager().showNewHand(this.game.getTurn().getName(), this.game.getTurn().getHand());
        LinkedList<ResourceCard> cards = new LinkedList<>();
        ResourceDeck deck = this.game.getResourceDeck();
        cards.add((ResourceCard) deck.getCards().get(0));
        cards.add((ResourceCard) deck.getCards().get(1));
        cards.add((ResourceCard) deck.getCards().get(2));
        for(Player p: players) {
            this.game.getObserverManager().updateResourceDeck(p.getName(), false, cards);
        }
        changeTurns("NormalTurn");
    }

    /**
     * Calls the room method that picks a gold card for the current player. And then notifies the player of his new hand.
     *
     * @param i the index of the card to pick
     * @throws RemoteException if a remote communication error occurs
     */
    public void pickGoldCard(int i) throws RemoteException {
        this.game.getGoldDeck().giveCard(this.game.getTurn(), i);
        this.game.getObserverManager().showNewHand(this.game.getTurn().getName(), this.game.getTurn().getHand());
        LinkedList<GoldCard> cards = new LinkedList<>();
        GoldDeck deck = this.game.getGoldDeck();
        cards.add((GoldCard) deck.getCards().get(0));
        cards.add((GoldCard) deck.getCards().get(1));
        cards.add((GoldCard) deck.getCards().get(2));
        for(Player p: players) {
            this.game.getObserverManager().updateGoldDeck(p.getName(), false, cards);
        }
        changeTurns("NormalTurn");
    }

    /**
     * Changes the turn to the next player.
     *
     * @param mex the message indicating the type of turn change
     * @throws RemoteException if a remote communication error occurs
     */
    public void changeTurns(String mex) throws RemoteException {
        this.game.changeTurns(mex);

    }

    /**
     * Returns the list of players in the game.
     *
     * @return the list of players
     */
    public LinkedList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Calls the functions that actually pick the card from the deck you want.
     *
     * @param i         the deck index (1 for resource, 2 for gold)
     * @param whichOne  the index of the card in the deck
     * @throws RemoteException  if a remote communication error occurs
     * @throws NotBoundException if a binding error occurs
     */
    public void drawCard(int i, int whichOne) throws RemoteException, NotBoundException {
        if (i < 1 || i > 2)
            getGame().getObserverManager().showException("WrongIndexException", "DrawDeck", getGame().getTurn().getName());
        else {
            if (whichOne < 1 || whichOne > 3)
                getGame().getObserverManager().showException("WrongIndexException", "DrawIndex", getGame().getTurn().getName());
            else {
                if (i == 1)
                    pickResCard(whichOne-1);
                else pickGoldCard(whichOne-1);
            }
        }
    }

    /**
     * Returns a player by his name.
     *
     * @param name the name of the player
     * @return the player with the specified name, or null if not found
     */
    public Player getPlayerByName(String name) {
        return players.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }
}