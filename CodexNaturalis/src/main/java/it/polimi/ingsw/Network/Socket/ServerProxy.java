package it.polimi.ingsw.Network.Socket;

import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Network.RMI.VirtualView;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ServerProxy implements VirtualServerSocket {

    final PrintWriter output;

    public ServerProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }
    @Override
    public void joinGame(String stringMessage) throws RoomFullException, RoomNotExistsException {
        output.write(stringMessage);
        output.flush();
    }

    @Override
    public void createGame(String stringMessage) throws WrongPlayersNumberException {
        output.write(stringMessage);
        output.flush();
    }

    @Override
    public void leaveGame(String name, VirtualView client) {

    }

    @Override
    public void placeCard(it.polimi.ingsw.Network.Socket.VirtualView client, int whichInHand, int x, int y, FB face) {

    }

    @Override
    public void setStartCardFace(FB face) {

    }
    //per ogni metodo che chiamo dal client mando sul output del canale output.write(gson)

}
