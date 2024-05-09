package it.polimi.ingsw.Network.Socket;

import java.rmi.RemoteException;

public interface VirtualView {
    void reportError(String details) ;

    void showUpdate() ;
}
