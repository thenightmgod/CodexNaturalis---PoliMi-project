package it.polimi.ingsw.Controller;

//questo controlla un game specifico

import it.polimi.ingsw.Exceptions.RequirementsNotSatisfied;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Exceptions.WrongPositionException;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.Composition;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.CompositionGoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.CardColor;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.DeckPackage.Deck;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Model.Room;
import it.polimi.ingsw.Network.RMI.VirtualView;
import it.polimi.ingsw.View.GameView;

import java.rmi.RemoteException;
import java.util.LinkedList;

public class GameController {

    private final int numPlayers;
    private GameState State;
    private Room Game;
    private final int RoomId;
    private LinkedList<Player> Players;
    private LinkedList<VirtualView> clients;

    public GameController(int id, int numPlayers){
        this.numPlayers = numPlayers;
        this.State = GameState.WAITING;
        this.RoomId = id;
        this.Players = new LinkedList<>();
        this.clients = new LinkedList<>();
    }

    public Room getGame() {
        return Game;
    }

    public int getRoomId(){
        return this.RoomId;
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    public int getHowManyPlayers(){
        return this.Players.size();
    }

    public void removePlayer(String name){
        Players.removeIf(p -> p.getName() == name);
    }

    public void addPlayer(String name, PlayerColor color, VirtualView client){  //non possono esserci più di 4 giocatori
        Player player = new Player(name, color);
        this.Players.add(player);
        this.clients.add(client);
    }

    public void initializeRoom(){ //pre inizializzazione è una specie di waiting room
        this.Game = new Room(RoomId, Players, clients);
    }

    public void startGame(){ //in virtual view
        if(this.Players.size() == this.numPlayers) {
            initializeRoom();
            State = GameState.RUNNING;
            //e poi robe col vecchio initialize game
        }
    }


    public void createDecks(){
        this.Game.createDecks();
    }

    public void giveStartCard(FB face){//ovviamente la face viene passata dal client
        this.Game.giveStartCards(face);
        changeTurns();
    }

    public void giveHands() throws RemoteException {
        this.Game.giveHands();
    }

    public void commonGoals(){
        this.Game.commonGoals();
    }

    /*public void show2GoalCards(Player p, int i) throws RemoteException { //la chiama il server e la mostra al client
        this.Game.show2GoalCards(p);
        //e si updata la view
    }*/
    //non penso serva perchè è il model che la manda al client

    public void chooseGoalCard(Player p, int i) throws WrongIndexException {
        if(i<1 || i>2)
            throw new WrongIndexException("it should be between 1 and 2");
        else{
            boolean choice = i != 1;
            this.Game.pickGoalCard(p, choice);
        }
        changeTurns(); //boh dipende dalla logica di gioco
    }

    public void checkGoals(){
        for(Player p : Players){
            this.Game.checkGoals(p, Game.getCommonGoals());
        }
    }

    public GameState getState(){
        return this.State;
    }

    //la place card effettiva si compone di questi due passaggi

    public void placeCard(int i, int x, int y, FB face) throws WrongIndexException{ //p passata dal client
    if(i < 1 || i > 3)
        throw new WrongIndexException("put an index between 1 and 3");
    else
        try{
            this.Game.placeCard(this.Game.getTurn().getCardFromHand(i), new Position(face, x, y));
        }
        catch(RequirementsNotSatisfied e){
            //chiamare metodo della view che mi fa riscegliere la carta da giocare;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (WrongPositionException e) {
            //chiamare metodo della view che mi fa scegliere posizione sensata
        }
    }

    // ci serve una funzione che chiede al client un intero da 0 a 2? boh
    // da client si sceglie deck che è già lì(?) e intero, non bisogna passare molto
    public void pickResCard(int i) throws RemoteException { //l'intero deve arrivare dal client
        declareWinner();
        this.Game.getResourceDeck().giveCard(this.Game.getTurn(), i);
        this.Game.setTwentyFlag();
        this.Game.setLastRound();
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        changeTurns();
    }

    public void pickGoldCard(int i) throws RemoteException {
        declareWinner();
        this.Game.getGoldDeck().giveCard(this.Game.getTurn(), i);
        this.Game.setTwentyFlag();
        this.Game.setLastRound();
        this.Game.getObserverManager().showNewHand(this.Game.getTurn().getName(), this.Game.getTurn().getHand());
        changeTurns();
    }

    public void changeTurns(){
        Player now = this.Game.getTurn();
        int size = Players.size();
        switch(size){
            case 2 -> {
                if(now.equals(Players.getFirst())) {
                    this.Game.setTurn(Players.get(1));
                    break;
                }
                this.Game.setTurn(Players.getFirst());
            }
            case 3 -> {
                if(now.equals(Players.getFirst()))
                    this.Game.setTurn(Players.get(1));
                else if(now.equals(Players.get(1)))
                    this.Game.setTurn(Players.get(2));
                else this.Game.setTurn(Players.getFirst());
            }
            case 4 -> {
                if(now.equals(Players.getFirst()))
                    this.Game.setTurn(Players.get(1));
                else if(now.equals(Players.get(1)))
                    this.Game.setTurn(Players.get(2));
                else if (now.equals(Players.get(2)))
                    this.Game.setTurn(Players.get(3));
                else this.Game.setTurn(Players.getFirst());
            }
        }
    }

    //come gestire l'ending, se qua o nel client o boh
    public void declareWinner(){
        checkGoals();
        this.Game.declareWinner();
    }

    public LinkedList<Player> getPlayers(){
        return this.Players;
    }

    public void drawCard(int i, int whichone) throws WrongIndexException, RemoteException {
        if(i < 1 || i  > 2)
            throw new WrongIndexException("indice sbagliato, o 1 o 2");
        else{
            if(whichone < 1 || whichone > 3)
                throw new WrongIndexException("indice sbagliato, 1, 2 o 3");
            else{
                if(i==1)
                    pickResCard(whichone);
                else pickGoldCard(whichone);
            }
        }
    }
}


