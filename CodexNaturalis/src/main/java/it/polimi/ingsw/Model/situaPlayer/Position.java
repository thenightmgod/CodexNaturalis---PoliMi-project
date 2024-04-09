package it.polimi.ingsw.Model.situaPlayer;

public class Position {
    private final int x;
    private final int y;
    private final FB face;

    public Position(FB situa, int x, int y){
        face = situa;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FB getFace() {
        return face;
    }
}
