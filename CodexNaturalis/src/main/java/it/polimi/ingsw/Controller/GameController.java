package it.polimi.ingsw.Controller;

//questo controlla un game specifico

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
import it.polimi.ingsw.View.GameView;

import java.util.LinkedList;

public class GameController {

    private final int numPlayers;
    private GameState State;
    private Room Game;
    private final int RoomId;
    LinkedList<Player> Players;
    public GameController(int id, int numPlayers){
        this.numPlayers = numPlayers;
        this.State = GameState.WAITING;
        this.RoomId = id;
        this.Players = new LinkedList<>();
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


    public void addPlayer(String name, PlayerColor color){  //non possono esserci più di 4 giocatori
        Player player = new Player(name, color);
        this.Players.add(player);
    }

    public void initializeRoom(){ //pre inizializzazione è una specie di waiting room
        this.Game = new Room(RoomId, Players);
    }

    public void startGame(){ //in virtual view
        if(this.Players.size() == this.numPlayers) {
            initializeRoom();
            State = GameState.RUNNING;
            initializeGame();
        }
    }

    public void initializeGame(){
        createDecks();
        for(Player p: Players){
            FB face = FB.FRONT;
            //setStartCardFace dal virtualServer
            giveStartCard(p, face);
        }
        giveHands();
        commonGoals();
        for(Player p: Players){
            LinkedList<GoalCard> toChoose = show2GoalCards(p);
            //si manda roba al client con la show2GoalCards(p);
            //arriva roba dal client
            GoalCard card = new CompositionGoalCard(3, 3, Composition.T, CardColor.GREEN);
            //ovviamente sta carta non ha alcun senso, deve arrivare dal client
            chooseGoalCard(p, card);
        }
    }

    public void createDecks(){
        this.Game.createDecks();
    }

    public void giveStartCard(Player p, FB face){//ovviamente la face viene passata dal client
        this.Game.giveStartCards(p, face);
    }

    public void giveHands(){
        this.Game.giveHands();
    }

    public void commonGoals(){
        this.Game.commonGoals();
    }

    public LinkedList<GoalCard> show2GoalCards(Player p){ //la chiama il server e la mostra al client
        return this.Game.show2GoalCards(p);
        //e si updata la view
    }

    public void chooseGoalCard(Player p, GoalCard card){
        this.Game.pickGoalCard(p, card);
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

    public LinkedList<Position> showFreePositions(Player p){ //questa va passata al client per far scegliere posizioni
        return p.getPlayerField().getFreePositions();
        //viene chiamata dal server e passata al client
    }

    public void placeCard(ResourceCard c, int x, int y, FB face){ //p passata dal client
        this.Game.placeCard(c, new Position(face, x, y));
    }

    // ci serve una funzione che chiede al client un intero da 0 a 2? boh
    // da client si sceglie deck che è già lì(?) e intero, non bisogna passare molto
    public void pickResCard(int i){ //l'intero deve arrivare dal client
        declareWinner();
        this.Game.getResourceDeck().giveCard(this.Game.getTurn(), i);
        this.Game.setTwentyFlag();
        this.Game.setLastRound();
        changeTurns();
    }

    public void pickGoldCard(int i){
        declareWinner();
        this.Game.getGoldDeck().giveCard(this.Game.getTurn(), i);
        this.Game.setTwentyFlag();
        this.Game.setLastRound();
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

}


