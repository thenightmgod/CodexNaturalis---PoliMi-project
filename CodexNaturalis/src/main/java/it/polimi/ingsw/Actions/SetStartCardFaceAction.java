package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.util.Map;

public class SetStartCardFaceAction extends Actions {
    boolean face;

    public SetStartCardFaceAction(boolean face, VirtualView view, MainController manager){
        super(view, manager);
        this.face = face;
    }

    @Override
    public void executor() { //ordine initialize game tutto gestito nella view
        int k = getManager().getControllersPerGame().entrySet().stream().filter(entry -> getManager().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = getManager().getControllersPerGame().get(k);
        FB f = FB.FRONT;
        if(!face) f = FB.BACK;
        controller.giveStartCard(f);
    }
}

