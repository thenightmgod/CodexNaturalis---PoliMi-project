package it.polimi.ingsw.Controller;

//questo controlla un game specifico

import it.polimi.ingsw.Exceptions.*;
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

    public synchronized void addPlayer(String name, PlayerColor color, VirtualView client) {  //non possono esserci più di 4 giocatori
        Player player = new Player(name, color);
        this.Players.add(player);
        this.clients.add(client);
    }

    public synchronized void initializeRoom() { //pre inizializzazione è una specie di waiting room
        this.Game = new Room(RoomId, Players, clients);
    }

    public synchronized void startGame() { //in virtual view
        if (this.Players.size() == this.numPlayers) {
            initializeRoom();
            State = GameState.RUNNING;
            //e poi robe col vecchio initialize game
        }
    }


    public synchronized void createDecks() {
        this.Game.createDecks();
    }

    public synchronized void giveStartCard(FB face) {//ovviamente la face viene passata dal client
        this.Game.giveStartCards(face);
        changeTurns();
    }

    public synchronized void giveHands() throws RemoteException {
        this.Game.giveHands();
    }

    public synchronized void commonGoals() {
        this.Game.commonGoals();
    }

    /*public void show2GoalCards(Player p, int i) throws RemoteException { //la chiama il server e la mostra al client
        this.Game.show2GoalCards(p);
        //e si updata la view
    }*/
    //non penso serva perchè è il model che la manda al client

    public synchronized void chooseGoalCard(Player p, int i){
        if (i < 1 || i > 2)
            getGame().getObserverManager().showException("WrongIndexException", p.getName());
        else {
            boolean choice = i != 1;
            this.Game.pickGoalCard(p, choice);
            changeTurns();
        }


       /* if (i < 1 || i > 2)
            getGame().getObserverManager().showException("WrongIndexException", p.getName());
        else {
            boolean choice = i != 1;
            this.Game.pickGoalCard(p, choice);
        }
        changeTurns(); //boh dipende dalla logica di gioco */
    }

    public synchronized void checkGoals() {
        for (Player p : Players) {
            this.Game.checkGoals(p, Game.getCommonGoals());
        }
    }

    public synchronized GameState getState() {
        return this.State;
    }

    //la place card effettiva si compone di questi due passaggi

    public synchronized void placeCard(int i, int x, int y, FB face) { //p passata dal client
        if (i < 1 || i > 3)
            getGame().getObserverManager().showException("WrongIndexException", getGame().getTurn().getName());
        else
            {
            this.Game.placeCard(this.Game.getTurn().getCardFromHand(i), face, x, y);
            }
    }


    public synchronized void pickResCard(int i) { //l'intero deve arrivare dal client
        this.Game.getResourceDeck().giveCard(this.Game.getTurn(), i);
        this.Game.setTwentyFlag();
        this.Game.setLastRound();
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        changeTurns();
    }

    public synchronized void pickGoldCard(int i) {
        this.Game.getGoldDeck().giveCard(this.Game.getTurn(), i);
        this.Game.setTwentyFlag();
        this.Game.setLastRound();
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        changeTurns();
    }

    public synchronized void changeTurns() {
        Player now = this.Game.getTurn();
        int size = Players.size();
        switch (size) {
            case 2 -> {
                if (now.equals(Players.getFirst())) {
                    this.Game.setTurn(Players.get(1));
                    break;
                }
                this.Game.setTurn(Players.getFirst());
            }
            case 3 -> {
                if (now.equals(Players.getFirst()))
                    this.Game.setTurn(Players.get(1));
                else if (now.equals(Players.get(1)))
                    this.Game.setTurn(Players.get(2));
                else this.Game.setTurn(Players.getFirst());
            }
            case 4 -> {
                if (now.equals(Players.getFirst()))
                    this.Game.setTurn(Players.get(1));
                else if (now.equals(Players.get(1)))
                    this.Game.setTurn(Players.get(2));
                else if (now.equals(Players.get(2)))
                    this.Game.setTurn(Players.get(3));
                else this.Game.setTurn(Players.getFirst());
            }
        }
    }

    //come gestire l'ending, se qua o nel client o boh
    public synchronized void declareWinner() {
        checkGoals();
        this.Game.declareWinner();
    }

    public synchronized LinkedList<Player> getPlayers() {
        return this.Players;
    }

    public synchronized void drawCard(int i, int whichone) {
        if (i < 1 || i > 2)
            getGame().getObserverManager().showException("WrongIndexException", getGame().getTurn().getName());
        else {
            if (whichone < 1 || whichone > 3)
                getGame().getObserverManager().showException("WrongIndexException", getGame().getTurn().getName());
            else {
                if (i == 1)
                    pickResCard(whichone);
                else pickGoldCard(whichone);
            }
        }
    }

    public synchronized Player getPlayerByName(String name) {
        Player p = Players.stream().filter(x -> Objects.equals(x.getName(), name)).findFirst().orElse(null);
        // se p è null ci deve essere un'eccezione
        return p;
    }

}