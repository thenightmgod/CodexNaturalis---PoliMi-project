package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Chat.ChatMessage;
import it.polimi.ingsw.Model.Messages.ChatMessageMessage;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.VirtualView;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The VirtualServer interface defines the methods that a server in the game must implement.
 */
public interface VirtualServer extends Remote {

    /**
     * Allows a player to join a game.
     *
     * @param Name The name of the player.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void joinGame(String Name, VirtualView client) throws RemoteException;

    /**
     * Allows a player to create a game.
     *
     * @param Name The name of the game.
     * @param numPlayers The number of players in the game.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void createGame(String Name, int numPlayers, VirtualView client) throws RemoteException;

    /**
     * Allows a player to leave a game.
     *
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void leaveGame(VirtualView client) throws RemoteException;

    /**
     * Allows a player to place a card.
     *
     * @param client The client that represents the player.
     * @param whichInHand The index of the card in hand.
     * @param x The x-coordinate of the placement.
     * @param y The y-coordinate of the placement.
     * @param face The face of the card.
     * @throws RemoteException If a remote access error occurs.
     */
    void placeCard(VirtualView client, int whichInHand, int x, int y, FB face) throws RemoteException;

    /**
     * Allows a player to set the face of the start card.
     *
     * @param face The face of the start card.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void setStartCardFace(boolean face, VirtualView client) throws RemoteException; //il player setta la variabile face della sua start card

    /**
     * Allows a player to choose a goal card.
     *
     * @param i The index of the goal card.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void chooseGoalCard(int i, VirtualView client) throws RemoteException;

    /**
     * Allows a player to draw a card.
     *
     * @param i The index of the deck.
     * @param whichOne The index of the card in the deck.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void drawCard(int i, int whichOne, VirtualView client) throws RemoteException;

    /**
     * Allows a player to end their turn.
     *
     * @param client The client that represents the player.
     * @param mex The message to be displayed when the turn ends.
     * @throws RemoteException If a remote access error occurs.
     */
    void endTurn(VirtualView client, String mex) throws RemoteException;

    void sendChatMessage(ChatMessage message, VirtualView client) throws RemoteException;

}
