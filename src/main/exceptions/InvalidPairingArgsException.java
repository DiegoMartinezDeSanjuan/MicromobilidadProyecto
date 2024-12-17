package exceptions;

/**
 * Exception thrown when invalid arguments are provided for a pairing operation.
 */
public class InvalidPairingArgsException extends Exception {
    public InvalidPairingArgsException(String message) {
        super(message);
    }
}
