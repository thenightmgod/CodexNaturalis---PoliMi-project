package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Network.RMI.RMIServer;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class SetStartCardFaceAction extends Actions {
    boolean face;

    public SetStartCardFaceAction(boolean face, VirtualView view, MainController manager, RMIServer server){
        super(view, manager, server);
        this.face = face;
    }

    @Override
    public void executor() throws RemoteException {
        System.out.println("franco1");
        int k = -1;
        for(Map.Entry<Integer, GameController> entry : getManager().getControllersPerGame().entrySet()){
            if(entry.getValue().getPlayers().stream().map(Player::getName).toList().contains(getView().getName())){
                k = entry.getKey();
                System.out.println("franco2");
            }
        }
        if(k != -1){
            GameController controller = getManager().getControllersPerGame().get(k);
            FB f = FB.FRONT;
            if(!face) f = FB.BACK;
            controller.placeStartCard(getView().getName(), f);
            System.out.println("franco3");
        }
    }
}