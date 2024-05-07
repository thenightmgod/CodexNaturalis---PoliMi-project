package it.polimi.ingsw.Exceptions;

public class RoomFullException extends Exception{
    public RoomFullException(String errMessage){
        super(errMessage);
    }
}
