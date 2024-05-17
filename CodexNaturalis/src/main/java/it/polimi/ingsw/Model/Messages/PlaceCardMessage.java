package it.polimi.ingsw.Model.Messages;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.VirtualView;


public class PlaceCardMessage extends Message{
    public CommonClient client;
   public int whichInHand;
   public int x;
   public int y;
   public FB face;

   public PlaceCardMessage(CommonClient client,String name, int whichInHand, int x, int y, FB face) {
       super("PlaceCardMessage");
       this.client=client;
       this.whichInHand=whichInHand;
       this.x=x;
       this.y=y;
       this.face=face;
   }

    public CommonClient getClient() {
        return client;
    }

    public FB getFace() {
        return face;
    }

    public int getWhichInHand() {
        return whichInHand;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
