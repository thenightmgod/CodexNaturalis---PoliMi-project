package it.polimi.ingsw.Network;

import it.polimi.ingsw.Messages.*;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.*;
public class ServerImplementation extends UnicastRemoteObject implements Server {

    private static ServerImplementation istance;

    protected ServerImplementation() throws RemoteException {
    }

    private static void startRMI() throws RemoteException {
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();
        Server stub = (Server) UnicastRemoteObject.exportObject(istance, 0);
        registry.rebind("Codex-Server", stub);
    }

    @Override
    public void handleMessage(Message m, Client client) throws RemoteException {
        return;
    }
}
