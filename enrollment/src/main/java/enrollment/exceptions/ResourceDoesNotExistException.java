package enrollment.exceptions;

/**
 * Exception for when the REST resource does not exist
 * @author Michael Kulaga
 */
public class ResourceDoesNotExistException extends RuntimeException {

    public ResourceDoesNotExistException(String message) {
        super(message);
    };

}
