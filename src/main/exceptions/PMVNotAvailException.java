package exceptions;

/**
 * Exception thrown when a Personal Mobility Vehicle (PMV) is not available.
 */
public class PMVNotAvailException extends Exception {
    public PMVNotAvailException(String message) {
        super(message);
    }
}

