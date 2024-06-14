package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Actions.Actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class MultipleFlow {

    private ExecutorService executorService;
    private PriorityBlockingQueue<Actions> actionsQueue;

    public MultipleFlow() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.actionsQueue = new PriorityBlockingQueue<>();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public PriorityBlockingQueue<Actions> getActionsQueue() {
        return actionsQueue;
    }

}
