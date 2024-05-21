package it.polimi.ingsw.Actions;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Network.VirtualView;

import java.rmi.RemoteException;
import java.util.Map;

public class ChooseGoalCardAction extends Actions{
    int i;

    public ChooseGoalCardAction(int i, VirtualView view, MainController manager){
        super(view, manager);
        this.i = i;
    }

    @Override
    public void executor(){
        int k = getManager().getControllersPerGame().entrySet().stream().filter(entry -> getManager().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
        GameController controller = getManager().getControllersPerGame().get(k);
        controller.chooseGoalCard(controller.getGame().getTurn(), i);
    }
}