package it.polimi.ingsw.Controller;

//questo controlla un game specifico

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
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

    public void initializeGame(){
        this.Game.initializeGame();
    }

    public void commonGoals(){
        this.Game.commonGoals();
    }

    public void checkGoals(){
        for(Player p : Players){
            this.Game.checkGoals(p, Game.getCommonGoals());
        }
    }

    public LinkedList<Position> showFreePositions(Player p){ //questa va passata al client per far scegliere posizioni
        return p.getPlayerField().getFreePositions();
        //e si updata la view
    }

    public void placeCard(Player player, ResourceCard c, Position p){
        Game.placeCard(player, c, p);
    }

    public void placeStartCard(Player p, ){
        //capiamo lo sbatti del controller
    }
}
