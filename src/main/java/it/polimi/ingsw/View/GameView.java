package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * The GameView interface provides a set of methods for updating the game state and interacting with the user.
 */
public interface GameView {
    /**
     * Updates the colors of the players.
     *
     * @param turn The current player's turn.
     * @param colors The list of the colors.
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     */
    void updateColors(Player turn, LinkedList<PlayerColor> colors) throws RemoteException;
    /**
     * Updates the chat messages.
     *
     * @param name The name of the player.
     * @param chat The list of chat messages.
     */
    void updateChat(String name, LinkedList<ChatMessage> chat);
    /**
     * Updates the points of the players.
     *
     * @param points The map of player names to their points.
     * @param name The name of the player.
     */
    void updatePoints(HashMap<String, Integer> points, String name);

    /**
     * Updates the goal cards of the player.
     *
     * @param goals The list of goal cards.
     * @param name The name of the player.
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     */
    void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;
    /**
     * Updates the common goal cards.
     *
     * @param goals The list of common goal cards.
     * @param name The name of the player.
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     */
    void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException;
    /**
     * Updates the hand of the player.
     *
     * @param hand The list of playable cards in the player's hand.
     * @param name The name of the player.
     */
    void updateHands(LinkedList<PlayableCard> hand, String name);
    /**
     * Updates the playing field of the player.
     *
     * @param field The playing field of the player.
     * @param name The name of the player.
     */
    void updateField(PlayingField field, String name);
    /**
     * Updates the free positions of the player.
     *
     * @param name The name of the player.
     * @param freePositions The list of free positions.
     */
    void updateFreePosition(String name, LinkedList<Position> freePositions);
    /**
     * Shows an exception message.
     *
     * @param name The name of the player.
     * @param exception The exception message.
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     * @throws NotBoundException If the name is not currently bound in the registry.
     */
    void showException(String name, String exception) throws RemoteException, NotBoundException;
    /**
     * Shows the start card.
     *
     * @param card The start card.
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     */
    void showStartCard(StartCard card) throws RemoteException;
    /**
     * Updates the turn of the player.
     *
     * @param player The player whose turn is to be updated.
     * @param mex The message related to the end of the turn.
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     */
    void updateTurn(Player player, String mex) throws RemoteException;
    /**
     * Updates the gold deck.
     *
     * @param deck The list of gold cards in the deck.
     * @param start True if the game has just started.
     * @param name The name of the player.
     */
    void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name);
    /**
     * Updates the resource deck.
     *
     * @param deck The list of resource cards in the deck.
     * @param start True if the game has just started.
     * @param name The name of the player.
     */
    void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name);
    /**
     * Starts the game.
     *
     * @throws RemoteException If there is a communication-related exception occurred during the execution of a remote method call.
     */
    void startingGame() throws RemoteException;
    /**
     * Declares the winner of the game.
     *
     * @param standings The list of player standings.
     */
    void declareWinner(LinkedList<String> standings);
    /**
     * Notifies that a player has reached 20 points.
     *
     * @param name The name of the player.
     */
    void twenty(String name);
    /**
     * Notifies that the last round has started.
     */
    void lastRound();
    /**
     * Prints the not your turn message.
     *
     * @param turn The current player's turn.
     * @param mex The message related to the turn.
     */
    void printNotYourTurn(Player turn, String mex);
    /**
     * Notifies that someone has left the game.
     */
    void leaveGameMessage();

}
