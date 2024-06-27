package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The VirtualView interface defines the methods that a view in the game must implement.
 */
public interface VirtualView extends Remote {

    /**
     * Sends the players to this client.
     *
     * @param players The players to send.
     * @throws RemoteException If a remote access error occurs.
     */
    void sendPlayers(LinkedList<String> players) throws RemoteException;

    /**
     * Updates the chat for this client.
     *
     * @param name The name of the player.
     * @param chat The new chat for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateChat(String name, LinkedList<ChatMessage> chat) throws RemoteException;

    /**
     * Updates the turn for a player.
     *
     * @param p The player whose turn is to be updated.
     * @param mex The message to be displayed.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateTurn(Player p, String mex) throws RemoteException;

    /**
     * Notifies a player that it's not their turn.
     *
     * @param turn The player to be notified.
     * @param mex The message to be displayed.
     * @throws RemoteException If a remote access error occurs.
     */
    void notYourTurn(Player turn, String mex) throws RemoteException;

    /**
     * Shows an exception message.
     *
     * @param exception The exception message.
     * @param details The details of the exception.
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
    void showException(String exception, String details) throws RemoteException, NotBoundException;

    /**
     * Updates the points for a player.
     *
     * @param points The points to be updated.
     * @param name The name of the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void updatePoints(HashMap<String, Integer> points, String name) throws RemoteException;

    /**
     * Updates the goal cards for this client.
     *
     * @param goals The new goal cards for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateGoals(LinkedList<GoalCard> goals) throws RemoteException;  //mostro due carte goal da scegliere

    /**
     * Updates the common goal cards for this client.
     *
     * @param goals The new common goal cards for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException; //setto i common goals (non li faccio vedere)

    /**
     * Shows the hand of this client.
     *
     * @param hand The hand to show.
     * @throws RemoteException If a remote access error occurs.
     */
    void showHand(LinkedList<PlayableCard> hand) throws RemoteException;

    /**
     * Updates the playing field for this client.
     *
     * @param name The name of the client.
     * @param field The new playing field for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateField(String name, PlayingField field) throws RemoteException;

    /**
     * Shows the free positions to this client.
     *
     * @param name The name of the client.
     * @param freePosition The free positions to show.
     * @throws RemoteException If a remote access error occurs.
     */
    void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException;

    /**
     * Updates the resource deck for this client.
     *
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The new resource deck for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException;

    /**
     * Updates the gold deck for this client.
     *
     * @param name The name of the client.
     * @param start Whether this is the start of the game.
     * @param deck The new gold deck for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException;

    /**
     * Declares the winner of the game.
     *
     * @param standings The final standings of the game.
     * @throws RemoteException If a remote access error occurs.
     */
    void declareWinner(LinkedList<String> standings) throws RemoteException;

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     * @throws RemoteException If a remote access error occurs.
     */


    String getNames() throws RemoteException;

    /**
     * Shows the start card to this client.
     *
     * @param card The start card to show.
     * @throws RemoteException If a remote access error occurs.
     */
    void showStartCard(StartCard card) throws RemoteException;

    /**
     * Notifies this client that the game is starting.
     *
     * @param p The player who is starting the game.
     * @throws RemoteException If a remote access error occurs.
     */
    void startingGame(Player p) throws RemoteException;

    /**
     * Notifies this client that someone has reached 20 points.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void twenty(String name) throws RemoteException;

    /**
     * Notifies this client that the game is in the last round.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void lastRound() throws RemoteException;

    /**
     * Checks if this client is alive.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void isAlivee() throws IOException;

    /**
     * Allows this client to leave the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void leaveGame() throws RemoteException;

    /**
     * Sends a message to this client that someone has left the game.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void leaveGameMessage() throws RemoteException;

    /**
     * Updates the colors for this client.
     *
     * @param turn The player whose turn it is.
     * @param colors The new colors that can be chosen by the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void updateColors(Player turn, LinkedList<PlayerColor> colors) throws RemoteException;

}
