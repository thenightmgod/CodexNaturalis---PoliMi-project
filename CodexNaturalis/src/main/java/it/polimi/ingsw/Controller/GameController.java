package it.polimi.ingsw.Controller;

//questo controlla un game specifico

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

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Objects;

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
    }

    public synchronized Room getGame() {
        return Game;
    }

    public synchronized int getRoomId() {
        return this.RoomId;
    }

    public synchronized int getNumPlayers() {
        return numPlayers;
    }

    public synchronized int getHowManyPlayers() {
        return this.Players.size();
    }

    public synchronized void removePlayer(String name) {
        Players.removeIf(p -> p.getName() == name);
    }

    public synchronized void addPlayer(String name, PlayerColor color, VirtualView client) throws RemoteException {  //non possono esserci più di 4 giocatori
        Player player = new Player(name, color);
        this.Players.add(player);
        this.clients.add(client);
        if(this.Players.size() == numPlayers) {
            startGame();
        }
    }

    public synchronized void initializeRoom() { //pre inizializzazione è una specie di waiting room
        this.Game = new Room(RoomId, Players, clients);
    }

    public synchronized void startGame() throws RemoteException { //in virtual view
        initializeRoom();
        State = GameState.RUNNING;
        createDecks();
        for(Player p: Players)
            giveStartCard(p);
        for(Player p: Players)
            giveInitialCards(p);
        createCommonGoals();
        for(Player p: Players)
            show2goalCards(p);
        this.Game.start();
    }

    public synchronized void createCommonGoals() throws RemoteException {
        this.Game.createCommonGoals();
    }

    public synchronized void createDecks() throws RemoteException {
        this.Game.createDecks();
    }


    public synchronized void commonGoals() {
        this.Game.commonGoals();
    }

    /*public void show2GoalCards(Player p, int i) throws RemoteException { //la chiama il server e la mostra al client
        this.Game.show2GoalCards(p);
        //e si updata la view
    }*/
    //non penso serva perchè è il model che la manda al client

    public synchronized void chooseGoalCard(Player p, int i) throws RemoteException {
        boolean choice = i != 1;
        this.Game.pickGoalCard(p, choice);
    }

    public synchronized void show2goalCards(Player p) throws RemoteException {
        this.Game.show2GoalCards(p);
    }

    public synchronized void giveStartCard(Player p) throws RemoteException {
        this.Game.giveStartCards(p);
    }

    public synchronized void giveInitialCards(Player p) throws RemoteException {
        this.Game.giveInitialCards(p);
    }

    public synchronized GameState getState() {
        return this.State;
    }

    //la place card effettiva si compone di questi due passaggi

    public synchronized void placeCard(int i, int x, int y, FB face) throws RemoteException { //p passata dal client
        if (i < 1 || i > 3)
            getGame().getObserverManager().showException("WrongIndexException", "PlaceCard", getGame().getTurn().getName());
        else
            {
            this.Game.placeCard(this.Game.getTurn().getCardFromHand(i), face, x, y);
            }
    }

    public synchronized void placeStartCard(String name, FB face) throws RemoteException {
        this.Game.placeStartCard(getPlayerByName(name), face);
    }


    public synchronized void pickResCard(int i) throws RemoteException { //l'intero deve arrivare dal client
        this.Game.getResourceDeck().giveCard(this.Game.getTurn(), i);
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        LinkedList<ResourceCard> cards = new LinkedList<>();
        ResourceDeck deck = this.Game.getResourceDeck();
        cards.add((ResourceCard) deck.getCards().get(0));
        cards.add((ResourceCard) deck.getCards().get(1));
        cards.add((ResourceCard) deck.getCards().get(2));
        for(Player p: Players) {
            this.Game.getObserverManager().updateResourceDeck(p.getName(), cards);
        }
    }

    public synchronized void pickGoldCard(int i) throws RemoteException {
        this.Game.getGoldDeck().giveCard(this.Game.getTurn(), i);
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        LinkedList<GoldCard> cards = new LinkedList<>();
        GoldDeck deck = this.Game.getGoldDeck();
        cards.add((GoldCard) deck.getCards().get(0));
        cards.add((GoldCard) deck.getCards().get(1));
        cards.add((GoldCard) deck.getCards().get(2));
        for(Player p: Players) {
            this.Game.getObserverManager().updateGoldDeck(p.getName(), cards);
        }
    }

    public synchronized void changeTurns() throws RemoteException {
        this.Game.changeTurns();

    }

    public synchronized void checkGoals(String name) throws RemoteException {
        this.Game.checkGoals(getPlayerByName(name));
    }

    public void endTurn() throws RemoteException {
        this.Game.changeTurns();
    }

    public synchronized LinkedList<Player> getPlayers() {
        return this.Players;
    }

    public synchronized void drawCard(int i, int whichOne) throws RemoteException {
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

    public synchronized Player getPlayerByName(String name) {
        return Players.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        //se è null sbatti
    }

}
