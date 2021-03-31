package airbnb.exceptions.property;

public class PropertyNotAvailableException extends RuntimeException {
    public PropertyNotAvailableException(String message) {
        super(message);
    }
}
