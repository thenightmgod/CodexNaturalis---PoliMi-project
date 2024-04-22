package it.polimi.ingsw.Model.situaCorner;

public class   Corner {
    private final Orientation Orient;
    private boolean Covered;
    private CardRes Res;
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

    public void setRes(CardRes R){
        this.Res = R;
        return;
    }
    public boolean getCovered(){
        return Covered;
    }
    public Orientation getOrientation(){
        return Orient;
    }

    public String toString() {
        return "Orientation: " + Orient + ", CardRes is: " + Res;
    }
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Corner corner = (Corner) obj;
        return Covered == corner.Covered &&
                Orient == corner.Orient &&
                Objects.equals(Res, corner.Res);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Orient, Covered, Res);
    }
}
