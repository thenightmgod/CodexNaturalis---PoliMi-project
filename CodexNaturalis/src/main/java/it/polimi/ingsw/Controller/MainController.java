package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.RoomPackage.ObserverManager;
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;

import java.io.EOFException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

//questo controlla game multipli
public class MainController {

    LinkedList<GameController> controllers;


    final HashMap<Integer, LinkedList<VirtualView>> viewPerGame;

    public MainController() {
        this.controllers = new LinkedList<>();
        this.viewPerGame = new HashMap<>();
        this.isAlive();

    }

    public LinkedList<GameController> getControllers() {
        return this.controllers;
    }

    public void createGame(String Name, int numPlayers, VirtualView client, int roomId) throws RemoteException {

        GameController Garfield = new GameController(roomId, numPlayers);
        controllers.add(Garfield);
        LinkedList<VirtualView> Grian = new LinkedList<>();
        Grian.add(client);
        viewPerGame.put(roomId, Grian);

        controllers.getLast().addPlayer(Name, PlayerColor.RED, client);

    }

    public void joinGame(String Name, VirtualView client, int roomId) throws RemoteException, NotBoundException {

        if (this.controllers.isEmpty()) {
            client.showException("RoomNotExistsException", "Nothing");
        } else {
            String boh = controllers.stream().map(GameController::getPlayers).flatMap(Collection::stream).map(Player::getName).filter(x -> x.equals(Name)).findFirst().orElse("");
            if (!boh.isEmpty()) {
                client.showException("NameAlreadyTakenException", "Nothing");
            } else {
                if (this.controllers.getLast().getHowManyPlayers() == this.controllers.getLast().getNumPlayers()) {
                    client.showException("RoomFullException", "Nothing");
                } else {
                    switch (this.controllers.getLast().getHowManyPlayers()) {
                        case 1 -> {
                            this.controllers.getLast().addPlayer(Name, PlayerColor.YELLOW, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(roomId);
                            Grian.add(client);
                            this.viewPerGame.put(roomId, Grian);
                        }
                        case 2 -> {
                            this.controllers.getLast().addPlayer(Name, PlayerColor.BLUE, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(roomId);
                            Grian.add(client);
                            this.viewPerGame.put(roomId, Grian);
                        }
                        case 3 -> {
                            this.controllers.getLast().addPlayer(Name, PlayerColor.GREEN, client);
                            LinkedList<VirtualView> Grian = this.viewPerGame.get(roomId);
                            Grian.add(client);
                            this.viewPerGame.put(roomId, Grian);
                        }
                    }
                }
            }
        }
    }

    public GameController leaveGame(String Name, int RoomId) throws RemoteException {
        controllers.get(RoomId).removePlayer(Name);
        return controllers.get(RoomId);
    }


    //come togliere sbatti del try catch
    public LinkedList<VirtualView> getOtherPlayers(String name) throws RemoteException {
        int k = -1;
        LinkedList<VirtualView> otherPlayers = new LinkedList<>();
        for (Map.Entry<Integer, LinkedList<VirtualView>> entry : viewPerGame.entrySet()) {
            for (VirtualView v : entry.getValue()) {
                if (v.getName().equals(name)) {
                    k = entry.getKey();
                }
            }
        }
        if (k != -1) {
            for (int i = 0; i < viewPerGame.get(k).size(); i++) {
                VirtualView view = viewPerGame.get(k).get(i);
                if (!name.equals(view.getName()))
                    otherPlayers.add(view);
            }
        }
        return otherPlayers;
    }

    public HashMap<Integer, LinkedList<VirtualView>> getViewPerGame() {
        return viewPerGame;
    }

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
                                    view.isAlive();
                                } catch (RemoteException e) {
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
            long period = 10000L;
            timer.scheduleAtFixedRate(task, delay, period);
        }).start();
    }

    public int getYourRoomId(String name) throws RemoteException {
        for (int roomId : viewPerGame.keySet()) {
            for (VirtualView v : viewPerGame.get(roomId)) {
                if (v.getName().equals(name)) {
                    return roomId;
                }
            }
        }
        return -1;
    }

}
