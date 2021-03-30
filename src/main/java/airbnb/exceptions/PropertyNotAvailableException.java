package airbnb.exceptions;

public class PropertyNotAvailableException extends RuntimeException {
    public PropertyNotAvailableException(String message) {
        super(message);
    }
}
