package airbnb.exceptions.user;

public class NotMatchingPasswordsException extends RuntimeException {
    public NotMatchingPasswordsException(String message) {
        super(message);
    }
}
