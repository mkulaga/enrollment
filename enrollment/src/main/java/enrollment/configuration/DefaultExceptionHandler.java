package enrollment.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler to respond to exceptions gracefully to the consumer
 * @author Michael Kulaga
 */
@RestControllerAdvice
public class DefaultExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * Default exception handler for RuntimeExceptions
     * @param e the RuntimeException that was thrown
     * @return the ErrorInformationResponse object that was thrown
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInformationResponse exceptionHandler(RuntimeException e) {

        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(e.getLocalizedMessage());
        }

        return new ErrorInformationResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());

    }

}
