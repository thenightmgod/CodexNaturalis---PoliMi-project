package it.polimi.ingsw.Network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    void connect(VirtualView client) throws RemoteException;

    //le funzioni del server che vuole chiamare il client
}
