package exceptions;

/**
 * Exception thrown when a procedural error occurs.
 */
public class ProceduralException extends Exception {
    public ProceduralException(String message) {
        super(message);
    }

    // Nuevo constructor que acepta una causa
    public ProceduralException(String message, Throwable cause) {
        super(message, cause);
    }
}
