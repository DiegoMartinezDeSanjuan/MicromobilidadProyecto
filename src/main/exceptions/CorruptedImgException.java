package exceptions;

/**
 * Exception thrown when an image is corrupted or unreadable.
 */
public class CorruptedImgException extends Exception {
    public CorruptedImgException(String message) {
        super(message);
    }
}

