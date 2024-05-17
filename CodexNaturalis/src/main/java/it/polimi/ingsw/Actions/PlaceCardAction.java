package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Exceptions.WrongIndexException;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.util.Map;

public class PlaceCardAction extends Actions{
    int whichInHand;
    int x;
    int y;
    FB face;

    public PlaceCardAction(int whichInHand, int x, int y, FB face){
        super();
        this.whichInHand = whichInHand;
        this.x = x;
        this.y = y;
    }

    @Override
    public void executor() throws WrongIndexException {
        int k = getManager().getControllersPerGame().entrySet().stream().filter(entry -> getManager().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = getManager().getControllersPerGame().get(k);
        //cambiare dinamica di place card con l'int della mano
        controller.placeCard(whichInHand, x, y, face);
    }

}
