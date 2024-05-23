package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class CreateAction extends Actions{

    int i;

    String name;

    public CreateAction(int i, VirtualView view, MainController manager, String name){
        super(view, manager);
        this.i = i;
        this.name = name;
    }

    @Override
    public void executor() throws RemoteException {
        int k = -1;
        for(Map.Entry<Integer, GameController> entry : getManager().getControllersPerGame().entrySet()){
            if(entry.getValue().getPlayers().stream().map(Player::getName).toList().contains(getView().getName())){
                k = entry.getKey();
            }
        }
        if(k != -1){
            GameController controller = this.getManager().createGame(name, i, getView());
            getManager().getControllersPerGame().put(controller.getRoomId(), controller);
        }
    }
}

