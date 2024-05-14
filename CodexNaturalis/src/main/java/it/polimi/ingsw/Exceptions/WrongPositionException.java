package it.polimi.ingsw.Exceptions;

public class WrongPositionException extends Exception{
    public WrongPositionException(){
        super("the selected position is not free");
    }
}
