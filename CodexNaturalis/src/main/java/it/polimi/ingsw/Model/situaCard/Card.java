package it.polimi.ingsw.Model.situaCard;

public abstract class Card {
    private final int Id;

    public Card(int id) {
        this.Id=id;
    }
    public int getId() {
        return Id;
    }
}
