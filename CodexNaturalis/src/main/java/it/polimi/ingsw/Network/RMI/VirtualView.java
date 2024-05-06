package it.polimi.ingsw.Network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    //le funzioni del client che vuole chiamare il server

    void reportError(String details) throws RemoteException;

    void showUpdate(Integer number) throws RemoteException;
}
