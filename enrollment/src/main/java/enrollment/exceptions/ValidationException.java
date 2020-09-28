package enrollment.exceptions;

/**
 * Exception for when there's an issue validating an object
 * @author Michael Kulaga
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    };

}
