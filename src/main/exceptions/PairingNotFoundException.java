package exceptions;

/**
 * Exception thrown when a pairing operation is not found.
 */
public class PairingNotFoundException extends Exception {
    public PairingNotFoundException(String message) {
        super(message);
    }
}

