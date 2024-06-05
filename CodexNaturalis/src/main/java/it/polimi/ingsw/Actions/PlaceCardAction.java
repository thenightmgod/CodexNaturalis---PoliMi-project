package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class PlaceCardAction extends Actions{

    int whichInHand;
    int x;
    int y;
    FB face;

    public PlaceCardAction(MainController manager, VirtualView view, int whichInHand, int x, int y, FB face, RMIServer server){
        super(view, manager, server);
        this.whichInHand = whichInHand;
        this.x = x;
        this.y = y;
    }

    @Override
    public void executor() throws RemoteException, NotBoundException {
        int k = -1;
        for(Map.Entry<Integer, GameController> entry : getManager().getControllersPerGame().entrySet()){
            if(entry.getValue().getPlayers().stream().map(Player::getName).toList().contains(getView().getName())){
                k = entry.getKey();
            }
        }
        if(k != -1){
            GameController controller = getManager().getControllersPerGame().get(k);
            //cambiare dinamica di place card con l'int della mano
            controller.placeCard(whichInHand, x, y, face);
            System.out.println("PLACED CARD");
        }
    }
}