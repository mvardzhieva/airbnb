package airbnb.exceptions;

public class NotMatchingPasswordsException extends RuntimeException {
    public NotMatchingPasswordsException(String message) {
        super(message);
    }
}
