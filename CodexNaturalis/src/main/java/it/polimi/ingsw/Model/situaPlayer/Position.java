package it.polimi.ingsw.Model.situaPlayer;

import it.polimi.ingsw.Model.situaCorner.Corner;

import java.util.Objects;

public class Position {
    private int x;
    private int y;
    private final FB face;

    public Position(FB situa, int x, int y){
        face = situa;
        this.x = x;
        this.y = y;
    }
    public Position(int x, int y){
        face = FB.FRONT;
        this.x = x;
        this.y = y;
    }
    public void setX(int x) { this.x=x; }
    public void setY(int y) { this.y=y; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FB getFace() {
        return face;
    }
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return this.x == position.getX() && this.y == position.getY() && this.face == position.getFace();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, face);
    }

}
