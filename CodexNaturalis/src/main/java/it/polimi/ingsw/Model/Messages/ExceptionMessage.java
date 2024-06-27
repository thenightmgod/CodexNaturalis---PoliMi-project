package it.polimi.ingsw.Model.Messages;
/**
 * The ExceptionMessage class extends the Message class and represents a message that is sent when an exception occurs.
 * Each ExceptionMessage has the type of the exception and it's details.
 */
public class ExceptionMessage extends Message {
    /**
     * The details of the exception.
     */
    public String details;
    /**
     * The type of the exception.
     */
    public String exception;
    /**
     * Constructs a new ExceptionMessage with the specified type of the exception and it's details.
     *
     * @param exception The type of the exception.
     * @param details The details of the exception.
     */
    public ExceptionMessage(String exception, String details) {
        super("ExceptionMessage");
        this.details=details;
        this.exception = exception;
    }
    /**
     * Returns the details of the exception.
     *
     * @return The details of the exception.
     */
    public String getDetails() {
        return details;
    }
    /**
     * Returns the type of the exception.
     *
     * @return The type of the exception.
     */
    public String getException() {
        return exception;
    }
}
