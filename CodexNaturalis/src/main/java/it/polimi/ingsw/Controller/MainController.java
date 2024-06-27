package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.Socket.SocketClientHandler;
import it.polimi.ingsw.Network.VirtualView;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * The MainController class manages multiple game controllers and their associated views.
 */
public class MainController {
    /**
     * The list of game controllers.
     */
    LinkedList<GameController> controllers;
    /**
     * The map of the clients per game.
     */
    final HashMap<Integer, LinkedList<VirtualView>> viewPerGame;

    /**
     * Constructs a MainController and initializes the controllers and view-per-game mappings.
     */
    public MainController() {
        this.controllers = new LinkedList<>();
        this.viewPerGame = new HashMap<>();
        this.isAlive();
    }

    /**
     * Returns the list of game controllers.
     *
     * @return a LinkedList of GameController objects
     */
    public LinkedList<GameController> getControllers() {
        return this.controllers;
    }

    /**
     * Creates a new game with the specified parameters and adds the first player to it.
     *
     * @param name       the name of the first player
     * @param numPlayers the number of players for the game
     * @param client     the virtual view of the client
     * @param roomId     the ID of the room
     */
    public void createGame(String name, int numPlayers, VirtualView client, int roomId){
        GameController Garfield = new GameController(roomId, numPlayers);
        controllers.add(Garfield);
        LinkedList<VirtualView> Grian = new LinkedList<>();
        Grian.add(client);
        viewPerGame.put(roomId, Grian);
        controllers.getLast().addPlayer(name, PlayerColor.RED, client);
    }

    /**
     * When a new player joins, calls the game controller function that adds it to the game.
     *
     * @param name   the name of the player
     * @param client the virtual view of the client
     * @param roomId the ID of the room
     * @throws RemoteException if a remote communication error occurs
     * @throws NotBoundException if a binding error occurs
     */
    public void joinGame(String name, VirtualView client, int roomId) throws RemoteException, NotBoundException {
        if (this.controllers.isEmpty()) {
            client.showException("RoomNotExistsException", "Nothing");
        } else {
            String boh = controllers.stream().map(GameController::getPlayers).flatMap(Collection::stream).map(Player::getName).filter(x -> x.equals(name)).findFirst().orElse("");
            if (!boh.isEmpty()) {
                client.showException("NameAlreadyTakenException", "Nothing");
            } else {
                if (this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers()) {
                    client.showException("RoomFullException", "Nothing");
                } else {
                    switch (this.controllers.getLast().getHowManyPlayers()) {
                        case 1 -> {
                            this.controllers.getLast().addPlayer(name, PlayerColor.YELLOW, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(roomId);
                            Grian.add(client);
                            this.viewPerGame.put(roomId, Grian);
                        }
                        case 2 -> {
                            this.controllers.getLast().addPlayer(name, PlayerColor.BLUE, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(roomId);
                            Grian.add(client);
                            this.viewPerGame.put(roomId, Grian);
                        }
                        case 3 -> {
                            this.controllers.getLast().addPlayer(name, PlayerColor.GREEN, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(roomId);
                            Grian.add(client);
                            this.viewPerGame.put(roomId, Grian);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the map of clients per game.
     *
     * @return a HashMap with room IDs as keys and LinkedLists of VirtualView objects as values
     */
    public HashMap<Integer, LinkedList<VirtualView>> getViewPerGame() {
        return viewPerGame;
    }

    /**
     * Periodically checks if all the clients in a room are still in, then ends the game for all the players in that room.
     */
    public void isAlive() {
        new Thread(() -> {
            Timer timer = new Timer("Timer");
            TimerTask task = new TimerTask() {
                public void run() {
                    synchronized (viewPerGame) {
                        for (Map.Entry<Integer, LinkedList<VirtualView>> entry : viewPerGame.entrySet()) {
                            LinkedList<VirtualView> views = entry.getValue();
                            Iterator<VirtualView> iterator = views.iterator();
                            while (iterator.hasNext()) {
                                VirtualView view = iterator.next();
                                try {
                                    if (view instanceof SocketClientHandler) {
                                        if (!((SocketClientHandler) view).isClientConnected()) {
                                            iterator.remove();
                                            for (VirtualView otherView : views) {
                                                try {
                                                    otherView.leaveGame();
                                                } catch (RemoteException leaveGameException) {
                                                    System.out.println("Error while trying to remove client: ");
                                                }
                                            }
                                        }
                                    } else {
                                        view.isAlivee();
                                    }
                                } catch (IOException e) {
                                    iterator.remove();
                                    for (VirtualView otherView : views) {
                                        try {
                                            otherView.leaveGame();
                                        } catch (RemoteException leaveGameException) {
                                            System.out.println("Error while trying to remove client: ");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            };

            long delay = 5000L;
            long period = 5000L;
            timer.scheduleAtFixedRate(task, delay, period);
        }).start();
    }

    /**
     * Returns the room ID for a player given their name.
     *
     * @param name the name of the player
     * @return the room ID of the player, or -1 if the player is not found
     * @throws RemoteException if a remote communication error occurs
     */
    public int getYourRoomId(String name) throws RemoteException {
        for (int roomId : viewPerGame.keySet()) {
            for (VirtualView v : viewPerGame.get(roomId)) {
                if (v.getNames().equals(name)) {
                    return roomId;
                }
            }
        }
        return -1;
    }
}