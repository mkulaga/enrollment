package enrollment.exceptions;

/**
 * Exception for when the REST resource already exists
 * @author Michael Kulaga
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    };

}
