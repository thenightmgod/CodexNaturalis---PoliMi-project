package it.polimi.ingsw.Network;

import it.polimi.ingsw.Messages.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation extends UnicastRemoteObject implements Client {




    public void update(Message m) throws RemoteException{
        return;
    };
}
