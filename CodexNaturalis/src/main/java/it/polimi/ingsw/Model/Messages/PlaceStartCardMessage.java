package it.polimi.ingsw.Model.Messages;


import it.polimi.ingsw.Network.RMI.VirtualView;

public class PlaceStartCardMessage extends Message {
    public boolean Face;

    public PlaceStartCardMessage(boolean face, String name) {
        super("PlaceStartCardMessage", name);
        this.Face=face;
    }

}
