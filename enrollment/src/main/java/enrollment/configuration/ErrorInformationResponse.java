package enrollment.configuration;

/**
 * Error information response object for handling exceptions
 * @author Michael Kulaga
 */
class ErrorInformationResponse {

    String exception;
    String message;

    /**
     * Default Constructor for ErrorInformationResponse
     * @param exception the exception that was thrown
     * @param message the message inside the exception
     */
    public ErrorInformationResponse(String exception, String message) {
        this.exception = exception;
        this.message = message;
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
