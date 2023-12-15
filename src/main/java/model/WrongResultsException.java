package model;

/**
 * Custom exception class for handling errors related to incorrect results in the dice game model.
 *
 * @author grzelu
 * @version 2.0
 */
public class WrongResultsException extends Exception {
    
    /**
     * Constructs a new WrongResultsException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public WrongResultsException(String message) {
        super(message);
    }
}