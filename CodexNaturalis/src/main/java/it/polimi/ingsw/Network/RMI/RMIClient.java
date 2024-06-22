package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.View.TUI.ClientModel;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.VirtualView;
import it.polimi.ingsw.View.GameView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents a client in the RMI (Remote Method Invocation) network.
 * This class is part of the RMI package and implements the VirtualView and CommonClient interfaces.
 * The RMIClient is responsible for interacting with the RMI server and performing actions based on the user's input.
 */
public class RMIClient extends UnicastRemoteObject implements VirtualView, CommonClient {

    private String name;
    private GameView view;
    private VirtualServer server;
    private Registry registry;
    private ClientModel model;

    /**
     * Constructs a new RMIClient with the specified name and server IP.
     * This constructor also initializes the client.
     *
     * @param name The name of the client.
     * @param serverIp The IP address of the server.
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    public RMIClient(String name, String serverIp) throws RemoteException, NotBoundException {
        this.model = new ClientModel(name);
        this.name = name;
        initializeClient(serverIp);
    }

    /**
     * Sets the name of this client.
     *
     * @param name The new name of this client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Initializes this client with the specified server IP.
     *
     * @param serverIp The IP address of the server.
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    public void initializeClient(String serverIp) throws RemoteException, NotBoundException {
        final String serverName = "CodexServer";
        registry = LocateRegistry.getRegistry(serverIp, 49666);
        this.server = (VirtualServer) registry.lookup(serverName);
    }

    /**
     * Returns the name of this client.
     *
     * @return The name of this client.
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the ClientModel associated with this client.
     *
     * @return The ClientModel associated with this client.
     */
    public ClientModel getClient(){
        return model;
    }

    /**
     * Returns the VirtualServer where this client is connected.
     *
     * @return The VirtualServer where this client is connected.
     */
    public VirtualServer getServer() {
        return server;
    }


    /**
     * Sets the face of the start card for this client.
     *
     * @param face The face of the start card.
     * @param client The client who is setting the face of the start card.
     */
    @Override
    public void setStartCardFace(boolean face, CommonClient client) {
        try {
            server.setStartCardFace(face, this);
        } catch(RemoteException ignored) {}
    }

    /**
     * Allows this client to leave the game.
     */
    @Override
    public void leaveGame() {
        try {
            server.leaveGame(this);
        } catch(RemoteException ignored) {}
    }

    /**
     * Places a card on the playing field for this client.
     *
     * @param client The client who is placing the card.
     * @param whichInHand The index of the card in the client's hand.
     * @param x The x-coordinate on the playing field where the card is to be placed.
     * @param y The y-coordinate on the playing field where the card is to be placed.
     * @param face The face of the card.
     */
    @Override
    public void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) {
        try {
            server.placeCard(this, whichInHand, x, y, face);
        } catch(RemoteException ignored) {}
    }

    /**
     * Allows this client to choose a goal card.
     *
     * @param i The index of the goal card to choose.
     * @param client The client who is choosing the goal card.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void chooseGoalCard(int i, CommonClient client) throws RemoteException {
        try {
            server.chooseGoalCard(i, this);
        } catch(RemoteException ignored) {}
    }

    /**
     * Allows this client to draw a card.
     *
     * @param whichDeck The index of the deck from which to draw the card.
     * @param whichone The index of the card to draw.
     * @param client The client who is drawing the card.
     */
    @Override
    public void drawCard(int whichDeck, int whichone, CommonClient client) {
        try {
            server.drawCard(whichDeck, whichone, this);
        } catch(RemoteException ignored) {}
    }

    /**
     * Allows this client to create a game.
     *
     * @param Name The name of the client that creates it.
     * @param numPlayers The number of players in the game.
     */
    @Override
    public void createGame(String Name, int numPlayers) {
        try {
            server.createGame(Name, numPlayers, this);
        } catch(RemoteException ignored) {}
    }

    /**
     * Sets the GameView for this client.
     *
     * @param view The GameView to set.
     */
    @Override
    public void setView(GameView view) {
        this.view = view;
    }

    /**
     * Ends the turn for this client.
     *
     * @param name The name of the client.
     * @param mex The message to be displayed when the turn ends.
     */
    @Override
    public void endTurn(String name, String mex){
        try {
            server.endTurn(this, mex);
        } catch (RemoteException ignored) {}
    }

    /**
     * Allows this client to join a game.
     *
     * @param name The name of the player who joins.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void joinGame(String name) throws RemoteException {
        try {
            server.joinGame(name, this);
        } catch (RemoteException ignored) {}
    }


    //    FUNZIONI DELLA VIRTUAL VIEW

    /**
     * Notifies the client when the 20 points are achieved by someone
     *
     * @param name the name of the player that does it
     */
    @Override
    public void twenty(String name) {
        this.view.twenty(name);

    }

    /**
     * Notifies the clients when the last round begins
     */
    @Override
    public void lastRound() {
        this.view.lastRound();
    }

    /**
     * Updates the turn for this client.
     *
     * @param p The player whose turn it is.
     * @param mex The message to be displayed when the turn updates.
     */
    @Override
    public void updateTurn(Player p, String mex) {
        try {
            this.view.updateTurn(p, mex);
        } catch (RemoteException ignored) {}
    }

    /**
     * Notifies this client that it is not their turn.
     *
     * @param turn The player whose turn it is.
     */
    public void notYourTurn(Player turn, String mex) {
        this.view.printNotYourTurn(turn, mex);
    }

    /**
     * Shows an exception to this client.
     *
     * @param exception The exception to show.
     * @param details The details of the exception.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    @Override
    public void showException(String exception, String details) throws NotBoundException {
        try {
            this.view.showException(exception, details);
        } catch (RemoteException ignored) {}
    }

    /**
     * Updates the points for this client.
     *
     * @param points The new points for this client.
     * @param name The name of the client.
     */
    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) {
       // model.setPointsCounter(points);
        this.view.updatePoints(points, name);
    }

    /**
     * Shows the start card to this client.
     *
     * @param card The start card to show.
     */
    @Override
    public void showStartCard(StartCard card) {
        try {
            this.view.showStartCard(card);
        } catch (RemoteException ignored) {}
    }

    /**
     * Notifies this client that the game is starting.
     *
     * @param p The player who is starting the game.
     */
    @Override
    public void startingGame(Player p) {
        try {
            this.view.startingGame();
        } catch (RemoteException ignored) {}
    }

    /**
     * Updates the goal cards for this client.
     *
     * @param goals The new goal cards for this client.
     */
    @Override
    public void updateGoals(LinkedList<GoalCard> goals) {
        try {
            this.view.updateGoals(goals, name);
        } catch (RemoteException ignored) {}

    }

    /**
     * Updates the common goal cards for this client.
     *
     * @param goals The new common goal cards for this client.
     */
    public void updateCommonGoals(LinkedList<GoalCard> goals) {
        try {
            this.view.updateCommonGoals(goals, name);
        } catch (RemoteException ignored) {}
    }

    /**
     * Shows the hand of this client.
     *
     * @param hand The hand to show.
     */
    @Override
    public void showHand(LinkedList<PlayableCard> hand) {
        this.view.updateHands(hand, name);
    }

    /**
     * Updates the playing field for this client.
     *
     * @param name The name of the client.
     * @param field The new playing field for this client.
     */
    @Override
    public void updateField(String name, PlayingField field) {
        this.view.updateField(field, name);
    }

    /**
     * Shows the free positions to this client.
     *
     * @param name The name of the client.
     * @param freePosition The free positions to show.
     */
    @Override
    public void showFreePositions(String name, LinkedList<Position> freePosition) {
        this.view.updateFreePosition(name, freePosition);
    }

    /**
     * Updates the resource deck for this client.
     *
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The new resource deck for this client.
     */
    @Override
    public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) {
        this.view.updateResourceDeck(deck, start, name);
    }

    /**
     * Updates the gold deck for this client.
     *
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The new gold deck for this client.
     */
    @Override
    public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) {
        this.view.updateGoldDeck(deck, start, name);
    }

    /**
     * Declares the winner of the game.
     *
     * @param standings The final standings of the game.
     */
    @Override
    public void declareWinner(LinkedList<String> standings) {
        this.view.declareWinner(standings);
    }

    /**
     * Updates the client's view.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void update() throws RemoteException {

    }

    /**
     * Shows the other player's field to this client.
     *
     * @param player The name of the other player.
     */
    @Override
    public void showOtherField(String player) {

    }

    /**
     * Checks if this client is alive.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void isAlive() throws RemoteException{

    }

    /**
     * Sends a message to this client that they have left the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void leaveGameMessage() throws RemoteException {
        this.view.leaveGameMessage();
    }

    /**
     * Checks if this client is equal to another object.
     *
     * @param obj The object to compare with this client.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VirtualView that = (VirtualView) obj;
        try {
            return Objects.equals(getName(), that.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }


}
