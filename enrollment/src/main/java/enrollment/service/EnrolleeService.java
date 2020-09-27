package enrollment.service;

import enrollment.common.Dependent;
import enrollment.common.Enrollee;
import enrollment.exceptions.ResourceAlreadyExistsException;
import enrollment.exceptions.ResourceDoesNotExistException;
import enrollment.repository.EnrollmentDAO;
import enrollment.validation.EnrollmentValidator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services for Adding, Modifying, and Deleting Enrollees
 * @author Michael Kulaga
 */
@Service
public class EnrolleeService {

    public final EnrollmentDAO enrollmentDAO;
    public final EnrollmentValidator validator;

    /**
     * Constructor for EnrollmentService
     *
     * @param enrollmentDAO the EnrollmentDAO to use
     */
    public EnrolleeService(EnrollmentDAO enrollmentDAO,
                           EnrollmentValidator validator) {
        this.enrollmentDAO = enrollmentDAO;
        this.validator = validator;
    }

    /**
     * Retrieves a list of all Enrollees
     *
     * @return the List of Enrollee objects
     */
    public List<Enrollee> getEnrollees() {
        return this.enrollmentDAO.findAll();
    }

    /**
     * Add a new Enrollee
     *
     * @param addEnrollee the new Enrollee to add
     */
    public void addEnrollee(Enrollee addEnrollee) {

        this.validateEnrollee(addEnrollee, true);

        final Enrollee existingEnrollee = this.enrollmentDAO.findById(addEnrollee.getId()).orElse(null);

        if (existingEnrollee == null) {
            this.enrollmentDAO.insert(addEnrollee);
        } else {
            throw new ResourceAlreadyExistsException("Unable To Add Enrollee, Enrollee Already Exists For Id: " + addEnrollee.getId());
        }
    }

    /**
     * Retrieves an Enrollee by Id
     *
     * @param id the Id of the Enrollee to search for
     * @return the Enrollee with the passed in Id
     */
    public Enrollee getEnrolleeById(String id) {
        return this.enrollmentDAO.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Unable To Retrieve Enrollee, Enrollee Does Not Exist For Id: " + id));
    }

    /**
     * Modifies an existing Enrollee, but not any Dependents
     *
     * @param modifiedEnrollee the modified Enrollee object
     * @param enrolleeId the Id of the Enrollee to modify
     */
    public void modifyEnrollee(Enrollee modifiedEnrollee, String enrolleeId) {

        this.validateEnrollee(modifiedEnrollee, false);

        final Enrollee existingEnrollee = this.enrollmentDAO.findById(enrolleeId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Unable To Modify Enrollee, Enrollee Does Not Exist For Id: " + enrolleeId));

        modifiedEnrollee.setId(existingEnrollee.getId());
        modifiedEnrollee.setPhoneNumber(modifiedEnrollee.getPhoneNumber() != null ? modifiedEnrollee.getPhoneNumber() : existingEnrollee.getPhoneNumber());
        modifiedEnrollee.setDependents(existingEnrollee.getDependents());

        this.enrollmentDAO.save(modifiedEnrollee);

    }

    /**
     * Deletes an existing Enrollee
     *
     * @param enrolleeId the Id of the Enrollee to delete
     */
    public void deleteEnrollee(String enrolleeId) {
        this.enrollmentDAO.deleteById(enrolleeId);
    }

    /**
     * Helper method to validate an Enrollee
     *
     * @param enrollee the Enrollee to validate
     * @param newEntity whether it is a new entity in the database or not
     */
    private void validateEnrollee(Enrollee enrollee, boolean newEntity) {

        this.validator.entityValidator(enrollee.getId(), enrollee.getName(), enrollee.getDateOfBirth(), newEntity);

        for (Dependent dependent : enrollee.getDependents()) {
            this.validator.entityValidator(dependent.getId(), dependent.getName(), dependent.getDateOfBirth(), newEntity);
        }

    }

}
