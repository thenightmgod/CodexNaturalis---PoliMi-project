package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.ClientModel;
import java.rmi.RemoteException;
import it.polimi.ingsw.Network.RMI.VirtualServer;

/**
 * The CommonClient interface defines the common methods that a client in the game must implement.
 */
public interface CommonClient {

    /**
     * Allows a player to join a game.
     *
     * @param Name The name of the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void joinGame(String Name) throws RemoteException;

    /**
     * Allows a player to create a game.
     *
     * @param Name The name of the game.
     * @param numPlayers The number of players in the game.
     * @throws RemoteException If a remote access error occurs.
     */
    void createGame(String Name, int numPlayers) throws RemoteException;

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
    void placeCard(CommonClient client, int whichInHand, int x, int y, FB face) throws RemoteException;

    /**
     * Allows a player to set the face of the start card.
     *
     * @param face The face of the start card.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void setStartCardFace(boolean face, CommonClient client) throws RemoteException; //il player setta la variabile face della sua start card

    /**
     * Allows a player to choose a goal card.
     *
     * @param i The index of the goal card.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void chooseGoalCard(int i, CommonClient client) throws RemoteException;

    /**
     * Allows a player to draw a card.
     *
     * @param i The index of the deck.
     * @param whichOne The index of the card in the deck.
     * @param client The client that represents the player.
     * @throws RemoteException If a remote access error occurs.
     */
    void drawCard(int i, int whichOne, CommonClient client) throws RemoteException;

    /**
     * Sets the view for the game.
     *
     * @param view The view to be set.
     */
    void setView(GameView view);

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    String getNames();

    /**
     * Gets the client model.
     *
     * @return The client model.
     */
    ClientModel getClient();

    /**
     * Allows a player to end their turn.
     *
     * @param name The name of the player.
     * @param mex The message to be displayed when the turn ends.
     * @throws RemoteException If a remote access error occurs.
     */
    void endTurn(String name, String mex) throws RemoteException;

    /**
     * Gets the server.
     *
     * @return The server.
     */
    VirtualServer getServer();

    void sendChatMessage(ChatMessage message) throws RemoteException;

    void endColor(PlayerColor color) throws RemoteException;

}
