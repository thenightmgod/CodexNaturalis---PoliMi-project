package it.polimi.ingsw.Model.CornerPackage;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import java.io.*;
import java.util.stream.Stream;

/**
 * Represents a corner of a card on the game board, which can be covered or uncovered and can possess objects or resources.
 */


public class Corner implements Serializable {
    private final Orientation Orient;
    private boolean Covered;
    private transient CardRes Res;

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        if (Res instanceof it.polimi.ingsw.Model.CornerPackage.Objects){
            out.writeObject("Objects");
            if(Res.equals(Objects.MANUSCRIPT)){
                out.writeObject("MANUSCRIPT");
            } else if (Res.equals(Objects.QUILL)) {
                out.writeObject("QUILL");
            } else {
                out.writeObject("INKWELL");
            }
        }
        else if(Res instanceof Resources){
            if(Res.equals(Resources.FUNGI_KINGDOM)){
                out.writeObject("FUNGI_KINGDOM");
            }
            else if(Res.equals(Resources.ANIMAL_KINGDOM)){
                out.writeObject("ANIMAL_KINGDOM");
            }
            else if(Res.equals(Resources.INSECT_KINGDOM)){
                out.writeObject("INSECT_KINGDOM");
            }
            else{
                out.writeObject("PLANT_KINGDOM");
            }
        }
        else if(Res instanceof CornerState){
            if(Res.equals(CornerState.EMPTY)){
                out.writeObject("EMPTY");
            }
            else{
                out.writeObject("CORNER");
            }
        }
        else{
            out.writeObject(null);
            out.writeObject(null);
        }
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        String name = (String) in.readObject();
        String className = (String) in.readObject();

        Res = Stream.of(CornerState.values(), it.polimi.ingsw.Model.CornerPackage.Objects.values(), Resources.values())
                .flatMap(Stream::of)
                .filter(x -> x.name().equals(name) && x.getClass().getName().equals(className))
                .findAny()
                .orElse(null);
    }


    /**
     * Constructs a new corner with the specified CardRes and orientation.
     *
     * @param r  The card resource implemented by the corner, which can be a resource, an object, or a corner state.
     * @param or The orientation of the corner.
     */
    public Corner(CardRes r, Orientation or){
        Res = r;
        Covered = false;
        Orient = or;
    }
    /**
     * Sets whether the corner is covered or not.
     *
     * @param k True if the corner is covered, false otherwise.
     */
    public void setCovered(boolean k){
        Covered = k;
        return;
    }


    /**
     * Returns the concrete implementation of the abstract interface CardRes that this corner has implemented.
     *
     * @return The concrete implementation of the abstract interface CardRes.
     */
    public CardRes getRes(){
        return Res;
    }

    /**
     * Sets the concrete implementation of the abstract interface CardRes for this corner.
     *
     * @param R The concrete implementation of the abstract interface CardRes to be set.
     */

    public void setRes(CardRes R){
        this.Res = R;
        return;
    }

    /**
     * Checks if the corner is covered or not.
     *
     * @return True if the corner is covered, false otherwise.
     */
    public boolean getCovered(){
        return Covered;
    }

    /**
     * Gets the orientation of the corner.
     *
     * @return The orientation of the corner.
     */
    public Orientation getOrientation(){
        return Orient;
    }


    /**
     * Returns a string representation of the corner, including its orientation and the card resource it implements.
     *
     * @return A string representation of the corner.
     */

    public String toString() {
        return "Orientation: " + Orient + ", CardRes is: " + Res;
    }

    /**
     * Checks if this corner is equal to another object.
     *
     * @param obj The object to compare with this corner.
     * @return True if the corners are equal, false otherwise.
     */

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


    /**
     * Generates a hash code for the corner.
     *
     * @return The hash code of the corner.
     */
    @Override
    public int hashCode() {
        return Objects.hash(Orient, Covered, Res);
    }
}
