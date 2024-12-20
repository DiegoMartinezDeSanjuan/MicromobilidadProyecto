package exceptions;

public class ConnectException extends Exception {

    public ConnectException(String message) {
        super(message);
    }

    // Nuevo constructor
    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
