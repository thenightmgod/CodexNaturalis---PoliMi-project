package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.util.Map;

public class LeaveAction extends Actions{
    public LeaveAction(){
        super();
    }

    @Override
    public void executor() {
        int k = getManager().getControllersPerGame().entrySet().stream().filter(entry -> getManager().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController c = this.getManager().leaveGame(this.getName(), k);
        getManager().getControllersPerGame().remove(c);
    }
}
