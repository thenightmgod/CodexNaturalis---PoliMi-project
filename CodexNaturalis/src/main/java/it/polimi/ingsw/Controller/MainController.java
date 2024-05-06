package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;

import java.util.LinkedList;

//questo controlla game multipli
public class MainController {
    LinkedList<GameController> controllers;

    public MainController(){
        this.controllers = new LinkedList<>();
    }

    public void joinGame(String Name, PlayerColor color){
        if(controllers.isEmpty()) {
            GameController now = new GameController(1);
            controllers.add(now);
            now.addPlayer(Name, color);
        }
        else{
            GameController now = controllers.getLast();
            if(now.getHowManyPlayers()==4 || now.getState()==GameState.RUNNING){
                this.controllers.add(new GameController(controllers.getLast().getRoomId() + 1));
                controllers.getLast().addPlayer(Name, color);
            }
            else{
                now.addPlayer(Name, color);
            }
        }
    }


}
