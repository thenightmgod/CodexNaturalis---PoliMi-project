package it.polimi.ingsw.Model.Messages;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.CommonClient;


public class PlaceCardMessage extends Message{
   private int whichInHand;
   private int x;
   private int y;
   private FB face;
   private String name;

   public PlaceCardMessage(String name, int whichInHand, int x, int y, FB face) {
       super("PlaceCardMessage");
       this.whichInHand=whichInHand;
       this.x=x;
       this.y=y;
       this.face=face;
       this.name = name;
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

    public String getName() {
        return name;
    }
}
