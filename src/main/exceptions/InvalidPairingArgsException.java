package exceptions;

/**
 * Exception thrown when invalid arguments are provided for a pairing operation.
 */
public class InvalidPairingArgsException extends Exception {

    /**
     * Constructor with a custom error message.
     *
     * @param message Detailed error message.
     */
    public InvalidPairingArgsException(String message) {
        super(message);
    }
}
