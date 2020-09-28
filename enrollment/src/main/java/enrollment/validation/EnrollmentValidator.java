package enrollment.validation;

import enrollment.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Validator for Enrollment Application
 * @author Michael Kulaga
 */
@Service
public class EnrollmentValidator {

    /**
     * Validates Enrollees and Dependents
     * @param id the Id to validate
     * @param name the Name to validate
     * @param dateOfBirth the Date of Birth to validate
     */
    public void entityValidator(String id, String name, String dateOfBirth) {

        this.validateId(id);
        this.validateName(name);
        this.validateDateOfBirth(dateOfBirth);
    }

    /**
     * Validates Id
     * @param id the Id to validate
     */
    private void validateId(String id) {

        if (StringUtils.isBlank(id)) {
            throw new ValidationException("Validation Error: Id Must Not Be Blank");
        }

        if (!StringUtils.isNumeric(id)) {
            throw new ValidationException("Validation Error: Ids Must Be Numeric");
        }

    }

    /**
     * Validates Name
     * @param name the Name to validate
     */
    private void validateName(String name) {

        if (StringUtils.isBlank(name)) {
            throw new ValidationException("Validation Error: Name Must Not Be Blank");
        }

    }

    /**
     * Validates Date of Birth
     * @param dateOfBirth the Date of Birth to validate
     */
    private void validateDateOfBirth(String dateOfBirth) {

        if (StringUtils.isBlank(dateOfBirth)) {
            throw new ValidationException("Validation Error: Date Of Birth Must Not Be Blank");
        }

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateOfBirth);
        } catch (ParseException ex) {
            throw new ValidationException("Validation Error: Date Of Birth Is Not In A Valid Format (yyyy-MM-dd)");
        }

    }

}
