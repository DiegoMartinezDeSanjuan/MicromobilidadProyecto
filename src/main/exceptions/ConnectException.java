package exceptions;

/**
 * Exception thrown when a connection issue occurs.
 */
public class ConnectException extends Exception {
    public ConnectException(String message) {
        super(message);
    }
}
