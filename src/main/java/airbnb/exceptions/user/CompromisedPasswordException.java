package airbnb.exceptions.user;

public class CompromisedPasswordException extends RuntimeException {
    public CompromisedPasswordException(String message) {
        super(message);
    }
}
