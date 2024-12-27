package exceptions;

public class InvalidServiceIDException extends RuntimeException {
    public InvalidServiceIDException(String message) {
        super(message);
    }
}
