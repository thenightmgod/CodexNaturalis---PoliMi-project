package it.polimi.ingsw.Model.Messages;
import it.polimi.ingsw.Model.PlayerPackage.FB;


public class PlaceCardMessage extends Message{
    //virtualview ??
   public int whichInHand;
   public int x;
   public int y;
   public FB face;

   public PlaceCardMessage(String name, int whichInHand, int x, int y, FB face) {
       super("PlaceCardMessage", name);
       this.whichInHand=whichInHand;
       this.x=x;
       this.y=y;
       this.face=face;
   }
    @Override
    public String getName() {
        return super.getName();
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
