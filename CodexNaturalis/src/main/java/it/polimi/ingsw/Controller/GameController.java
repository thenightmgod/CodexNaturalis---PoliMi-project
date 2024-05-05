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

    Room Game;
    GameView View;
    int RoomId;
    LinkedList<Player> Players;
    public GameController(int id, GameView Sunglasses){
        this.RoomId = id;
        this.View = Sunglasses;
        this.Players = new LinkedList<>();
    }

    public void addPlayer(String name, PlayerColor color){  //non possono esserci più di 4 giocatori
        if(this.Players.size() < 4) {
            Player player = new Player(name, color);
            this.Players.add(player);
        }
    }

    public void initializeRoom(){ //pre inizializzazione è una specie di waiting room
        this.Game = new Room(RoomId, Players);
    }

    public void initalizeGame(){
        createDecks();
        for(Player p: Players){
            //arriva roba dal client
            FB face = FB.FRONT;
            giveStartCard(p, face);
        }
        giveHands();
        commonGoals();
        for(Player p: Players){
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

    public LinkedList<GoalCard> show2GoalCards(Player p){ //mostra le carte obiettivo da poter scegliere al lazzaro
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

    //la place card effettiva si compone di questi due passaggi

    public LinkedList<Position> showFreePositions(Player p){ //questa va passata al client per far scegliere posizioni
        return p.getPlayerField().getFreePositions();
        //e si updata il client
    }

    public void placeCard(ResourceCard c, Position p){ //p passata dal client
        this.Game.placeCard(c, p);
    }

    // ci serve una funzione che chiede al client un intero da 0 a 2? boh
    // da client si sceglie deck che è già lì(?) e intero, non bisogna passare molto
    public void pickResCard(int i){ //l'intero deve arrivare dal client
        this.Game.getResourceDeck().giveCard(this.Game.getTurn(), i);
        changeTurns();
    }

    public void pickGoldCard(int i){
        this.Game.getGoldDeck().giveCard(this.Game.getTurn(), i);
        changeTurns();
    }

    public void changeTurns(){
        Player now = this.Game.getTurn();
        int size = Players.size();
        switch(size){
            case 2 -> {
                if(now.equals(Players.getFirst()))
                    this.Game.setTurn(Players.get(1));
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

}


