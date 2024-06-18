package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Actions.Actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Represents a class that manages multiple flows of actions in the game.
 * This class is part of the RMI (Remote Method Invocation) package.
 */
public class MultipleFlow {

    private ExecutorService executorService;
    private PriorityBlockingQueue<Actions> actionsQueue;

    /**
     * Constructs a new MultipleFlow with a single thread executor and a priority blocking queue for actions.
     */
    public MultipleFlow() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.actionsQueue = new PriorityBlockingQueue<>();
    }

    /**
     * Returns the ExecutorService associated with this MultipleFlow.
     *
     * @return The ExecutorService associated with this MultipleFlow.
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    public PriorityBlockingQueue<Actions> getActionsQueue() {
        return actionsQueue;
    }

}
