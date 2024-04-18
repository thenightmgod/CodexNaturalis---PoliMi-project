package it.polimi.ingsw.Model.situaCorner;

public class Corner {
    private final Orientation Orient;
    private boolean Covered;
    private final CardRes Res;
    public Corner(CardRes r, Orientation or){
        Res = r;
        Covered = false;
        Orient = or;
    }
    public void setCovered(boolean k){
        Covered = k;
        return;
    }
    public CardRes getRes(){
        return Res;
    }
    public boolean getCovered(){
        return Covered;
    }
    public Orientation getOrientation(){
        return Orient;
    }
    public CardRes getCardRes(){
        return Res;
    }


}
