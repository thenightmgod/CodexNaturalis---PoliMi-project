package it.polimi.ingsw.Controller;

//questo controlla un game specifico

import it.polimi.ingsw.Actions.Actions;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private final int numPlayers;
    private GameState State;
    private Room Game;
    private final int RoomId;
    private LinkedList<Player> Players;
    private LinkedList<VirtualView> clients;

    public GameController(int id, int numPlayers) {
        this.numPlayers = numPlayers;
        this.State = GameState.WAITING;
        this.RoomId = id;
        this.Players = new LinkedList<>();
        this.clients = new LinkedList<>();
        run();
    }

    public Room getGame() {
        return Game;
    }

    public int getRoomId() {
        return this.RoomId;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getHowManyPlayers() {
        return this.Players.size();
    }

    public void removePlayer(String name) {
        Players.removeIf(p -> p.getName() == name);
    }

    public void addPlayer(String name, PlayerColor color, VirtualView client) throws RemoteException {  //non possono esserci più di 4 giocatori
        Player player = new Player(name, color);
        this.Players.add(player);
        this.clients.add(client);

    }

    public void initializeRoom() { //pre inizializzazione è una specie di waiting room
        this.Game = new Room(RoomId, Players, clients);
    }

    public void run(){
        new Thread(() -> {
            TimerTask task = new TimerTask() {
                public void run() {
                    if(Players.size() == numPlayers) {
                        try {
                            startGame();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };

            Timer timer = new Timer("Timer");
            long delay = 10000L;

            timer.schedule(task, delay);

        }).start();
    }

    public void startGame() throws RemoteException { //in virtual view
        initializeRoom();
        for (Player p : Players)
            this.Game.startingGame(p);
        State = GameState.RUNNING;
        createDecks();
        createCommonGoals();
        for(Player p: Players)
            giveStartCard(p);
        for (Player p : Players)
            giveInitialCards(p);
        for(Player p: Players)
            show2goalCards(p);
        this.Game.start();
    }

    public void createCommonGoals() throws RemoteException {
        this.Game.createCommonGoals();
    }

    public void createDecks() throws RemoteException {
        this.Game.createDecks();
    }


    public void commonGoals() {
        this.Game.commonGoals();
    }

    /*public void show2GoalCards(Player p, int i) throws RemoteException { //la chiama il server e la mostra al client
        this.Game.show2GoalCards(p);
        //e si updata la view
    }*/
    //non penso serva perchè è il model che la manda al client

    public void chooseGoalCard(Player p, int i) throws RemoteException {
        boolean choice = i != 1;
        this.Game.pickGoalCard(p, choice);
    }

    public void show2goalCards(Player p) throws RemoteException {
        this.Game.show2GoalCards(p);
    }

    public void giveStartCard(Player p) throws RemoteException {
        this.Game.giveStartCards(p);
    }

    public void giveInitialCards(Player p) throws RemoteException {
        this.Game.giveInitialCards(p);
    }

    public GameState getState() {
        return this.State;
    }

    //la place card effettiva si compone di questi due passaggi

    public void placeCard(int i, int x, int y, FB face) throws RemoteException, NotBoundException { //p passata dal client
        if (i < 1 || i > 3)
            getGame().getObserverManager().showException("WrongIndexException", "PlaceCard", getGame().getTurn().getName());
        else
            {
            this.Game.placeCard(this.Game.getTurn().getCardFromHand(i), face, x, y);
            }
    }

    public void placeStartCard(String name, FB face) throws RemoteException {
        this.Game.placeStartCard(getPlayerByName(name), face);
    }


    public void pickResCard(int i) throws RemoteException { //l'intero deve arrivare dal client
        this.Game.getResourceDeck().giveCard(this.Game.getTurn(), i);
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        LinkedList<ResourceCard> cards = new LinkedList<>();
        ResourceDeck deck = this.Game.getResourceDeck();
        cards.add((ResourceCard) deck.getCards().get(0));
        cards.add((ResourceCard) deck.getCards().get(1));
        cards.add((ResourceCard) deck.getCards().get(2));
        for(Player p: Players) {
            this.Game.getObserverManager().updateResourceDeck(p.getName(), false, cards);
        }
    }

    public void pickGoldCard(int i) throws RemoteException {
        this.Game.getGoldDeck().giveCard(this.Game.getTurn(), i);
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        LinkedList<GoldCard> cards = new LinkedList<>();
        GoldDeck deck = this.Game.getGoldDeck();
        cards.add((GoldCard) deck.getCards().get(0));
        cards.add((GoldCard) deck.getCards().get(1));
        cards.add((GoldCard) deck.getCards().get(2));
        for(Player p: Players) {
            this.Game.getObserverManager().updateGoldDeck(p.getName(), false, cards);
        }
    }

    public void changeTurns() throws RemoteException {
        this.Game.changeTurns();

    }

    public void checkGoals(String name) throws RemoteException {
        this.Game.checkGoals(getPlayerByName(name));
    }

    public void endTurn() throws RemoteException {
        this.Game.changeTurns();
    }

    public LinkedList<Player> getPlayers() {
        return this.Players;
    }

    public void drawCard(int i, int whichOne) throws RemoteException, NotBoundException {
        if (i < 1 || i > 2)
            getGame().getObserverManager().showException("WrongIndexException", "DrawDeck", getGame().getTurn().getName());
        else {
            if (whichOne < 1 || whichOne > 3)
                getGame().getObserverManager().showException("WrongIndexException", "DrawIndex", getGame().getTurn().getName());
            else {
                if (i == 1)
                    pickResCard(whichOne);
                else pickGoldCard(whichOne);
            }
        }
    }

    public Player getPlayerByName(String name) {
        return Players.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        //se è null sbatti
    }

}
